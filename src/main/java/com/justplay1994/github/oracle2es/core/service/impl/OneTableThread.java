package com.justplay1994.github.oracle2es.core.service.impl;

import com.justplay1994.github.oracle2es.core.config.Oracle2esConfig;
import com.justplay1994.github.oracle2es.core.service.model.PageModel;
import com.justplay1994.github.oracle2es.core.service.model.TableModel;
import com.justplay1994.github.oracle2es.framework.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */
public class OneTableThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(OneTableThread.class);

    private String tbName;
    private TableModel tableModel;
    private LinkedBlockingQueue<List<HashMap>> queue;

    public OneTableThread(String tbName, TableModel tableModel, LinkedBlockingQueue<List<HashMap>> queue){
        this.tbName = tbName;
        this.tableModel = tableModel;
        this.queue = queue;
    }


    @Override
    public void run() {
        Oracle2esConfig config = SpringContextUtils.getBean(Oracle2esConfig.class);
        ThreadPoolExecutor producer = new ThreadPoolExecutor(config.getMaxQueryByPagePerTable(),config.getMaxQueryByPagePerTable(),100, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>());
        int pageNum = 1;
        while(true) {
            int end = pageNum * config.getPageSize()-1;
            //启动翻页查询的线程，每一页请求一个查询线程。
            if (end >= tableModel.getTotalNumber()) {//是否是最后一页
                PageModel pageModel = new PageModel(pageNum, config.getPageSize());
                tableModel.getProcessBar().addFinishedNumber(config.getPageSize() - (end - tableModel.getTotalNumber())-1);//进度条增加最后一页
                producer.execute(new QueryTableByPageThread(tbName, pageModel, queue));
                logger.info(tbName+":"+tableModel.getProcessBar().print()); //打印进度条
                break;
            }else {
                PageModel pageModel = new PageModel(pageNum, config.getPageSize());
                tableModel.getProcessBar().addFinishedNumber(config.getPageSize()); //进度条
                producer.execute(new QueryTableByPageThread(tbName, pageModel, queue));
                logger.info(tbName + ":" + tableModel.getProcessBar().print()); //打印进度条
            }
        }
        while (producer.getActiveCount() != 0){
            logger.info("query " + tbName + " finished=" + producer.getCompletedTaskCount() * 1.0 / producer.getTaskCount()*100 +"%");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }
}
