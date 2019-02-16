package com.justplay1994.github.oracle2es.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.justplay1994.github.oracle2es.core.config.Oracle2esConfig;
import com.justplay1994.github.oracle2es.core.service.model.DatabaseModel;
import com.justplay1994.github.oracle2es.core.service.model.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */

@Service
public class ScheduleServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    OracleOperateServiceImpl oracleOperateService;

    @Autowired
    Oracle2esConfig config;

    @Autowired
    EsOperateServiceImpl esOperateService;

    private DatabaseModel databaseModel;

    public void schedule() throws InterruptedException {
        databaseModel = new DatabaseModel();

        //查询所有表结构
        databaseModel.tbs = oracleOperateService.queryAllTableStructure();
        //删除冲突索引
        esOperateService.deleteAllConflict(databaseModel);
        //创建es mapping关系,如果需要给字段起别名，可以在该方法上用around环绕通知
        esOperateService.createMapping(databaseModel);
        //启动生产者，分页查询表数据
        Thread producer = new Thread(new Producer());
        producer.start();
        //启动bulk生产者，去消费数据，将数据转化为bulk批量插入语句。如果已经给字段起了别名，在这里需要注意给执行函数around。
        Thread bulkProducer = new Thread(new BulkProducer());
        bulkProducer.start();
        //启动bulk消费者，向es插入数据
        Thread bulkConsumer = new Thread(new BulkConsumer());
        bulkConsumer.start();
        while (producer.isAlive() || bulkProducer.isAlive() || bulkConsumer.isAlive()) {
            Thread.sleep(100);
        }
        logger.info("Finished!");

    }

    class Producer implements Runnable {

        @Override
        public void run() {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getMaxTable(), config.getMaxTable(), 100L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
            for (String tbName : databaseModel.tbs.keySet()) {
                TableModel tableModel = databaseModel.tbs.get(tbName);
                executor.execute(new OneTableThread(tbName, tableModel, tableModel.getRows()));
            }
            while (executor.getActiveCount() != 0) {
                double process = executor.getCompletedTaskCount() * 1.0 / executor.getTaskCount() * 100;
//                logger.info("query table finished=: " + process + "% " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            double process = executor.getCompletedTaskCount() * 1.0 / executor.getTaskCount() * 100;
            logger.info("query data finished=: " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount() + " " + process + "%");
            executor.shutdown();
            logger.info("query data all finished!");
        }
    }

    class BulkProducer implements Runnable {
        @Override
        public void run() {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getMaxBulkGenerator(), config.getMaxBulkGenerator(), 100, TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<Runnable>(1));
            databaseModel.bulks = new LinkedBlockingQueue<String>();
            int dbTotalNum = 0;
            for (String tbName : databaseModel.tbs.keySet()) {
                TableModel tableModel = databaseModel.tbs.get(tbName);
                dbTotalNum += tableModel.getTotalNumber();
            }
            //遍历所有的表，的所有数据队列
            while (true) {
                if (databaseModel.isBulkGeneratorFinished()) break;
                for (String tbName : databaseModel.tbs.keySet()) {
                    TableModel tableModel = databaseModel.tbs.get(tbName);
                    if (tableModel.isBulkGeneratorFinished()) continue;
                    LinkedBlockingQueue<List<HashMap>> queue = tableModel.getRows();
                    int k = 0;
                    if (tableModel.isQueryDataFinished()) {
                        k = 1;
                    }
                    while (true) {
                        List<HashMap> pageData = null;
                        try {
                            pageData = queue.poll(3000L, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (pageData == null) break;
                        final String f_tbName = tbName;
                        final List<HashMap> f_pageData = pageData;
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                List<String> bulkList = null;
                                try {
                                    bulkList = esOperateService.generatorBulk(f_tbName, f_pageData);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                for (String bulk : bulkList) {
                                    try {
                                        databaseModel.bulks.offer(bulk, 3000L, TimeUnit.MILLISECONDS);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                    if (k == 1) {
                        tableModel.setBulkGeneratorFinished(true);
                    }
                }
            }
        }
    }

    class BulkConsumer implements Runnable {
        @Override
        public void run() {
            while (databaseModel.bulks == null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    logger.error("",e);
                }
            }
            ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getMaxInputData(), config.getMaxInputData(), 100L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(config.getMaxInputData()));
            StringBuilder builder = new StringBuilder();
            boolean allFinished = false;
            while (true) {
                if (databaseModel.isBulkGeneratorFinished() && databaseModel.bulks.size() == 0) {
                    allFinished = true;
                }
                String bulk = databaseModel.bulks.poll();
                if (bulk == null){
                    if (!allFinished)
                        continue;
                }
                else {
                    builder.append(bulk).append("\n");
                }
                if (builder.length() < config.getBulkSize() * 1025 * 1024) {
                    if (!allFinished) continue;
                }
                final String input = builder.toString();
                builder.delete(0, builder.length());
                try {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                esOperateService.inputData(input);
                            } catch (IOException e) {
                                logger.error("input data error!\n", e);
                            }
                        }
                    });
                } catch (RejectedExecutionException e) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

                if (databaseModel.isBulkGeneratorFinished() && databaseModel.bulks.size() == 0 && builder.length() == 0) {
                    break;
                }
            }
            while (executor.getActiveCount() != 0) {
                double process = executor.getCompletedTaskCount() * 1.0 / executor.getTaskCount() * 100;
                logger.info("input data finished=: " + process + "% " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            double process = executor.getCompletedTaskCount() * 1.0 / executor.getTaskCount() * 100;
            logger.info("input data finished=: " + process + "% " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount());
            executor.shutdown();
            logger.info("input data all finished!");
        }
    }
}
