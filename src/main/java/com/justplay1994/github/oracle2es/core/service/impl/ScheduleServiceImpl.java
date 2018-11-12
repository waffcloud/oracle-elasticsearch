package com.justplay1994.github.oracle2es.core.service.impl;

import com.justplay1994.github.oracle2es.core.config.Oracle2esConfig;
import com.justplay1994.github.oracle2es.core.service.model.DatabaseModel;
import com.justplay1994.github.oracle2es.core.service.model.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        //启动bulk消费者，向es插入数据

        while (producer.isAlive()){
            Thread.sleep(100);
        }
        logger.info("Finished!");
    }
    class Producer implements Runnable{

        @Override
        public void run() {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getMaxTable(), config.getMaxTable(), 100L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
            for (String tbName : databaseModel.tbs.keySet()){
                TableModel tableModel = databaseModel.tbs.get(tbName);
                executor.execute(new OneTableThread(tbName, tableModel, tableModel.getRows()));
            }
            while (executor.getActiveCount() != 0){
                double process = executor.getCompletedTaskCount()*1.0/executor.getTaskCount()*100;
                logger.info("query data finished=: " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount()+" "+process+"%");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            double process = executor.getCompletedTaskCount()*1.0/executor.getTaskCount()*100;
            logger.info("query data finished=: " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount()+" "+process+"%");
            executor.shutdown();
            logger.info("query data all finished!");
        }
    }

    class BulkProducer implements Runnable{
        @Override
        public void run() {
            //遍历所有的表，的所有数据队列
            while (true){
                for (String tbName: databaseModel.tbs.keySet()){
                    TableModel tableModel = databaseModel.tbs.get(tbName);
                    LinkedBlockingQueue<List<HashMap>> queue = tableModel.getRows();

                }
            }
        }
    }

    class BulkConsumer implements Runnable{
        @Override
        public void run() {

        }
    }
}
