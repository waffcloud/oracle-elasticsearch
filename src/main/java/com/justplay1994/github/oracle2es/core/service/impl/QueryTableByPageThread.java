package com.justplay1994.github.oracle2es.core.service.impl;

import com.justplay1994.github.oracle2es.core.service.model.DatabaseModel;
import com.justplay1994.github.oracle2es.core.service.model.PageModel;
import com.justplay1994.github.oracle2es.framework.utils.SpringContextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */
public class QueryTableByPageThread implements Runnable {

    private String tbName;
    private PageModel pageModel;
    private LinkedBlockingQueue<List<HashMap>> queue;

    public QueryTableByPageThread(String tbName, PageModel pageModel, LinkedBlockingQueue<List<HashMap>> queue){
        this.tbName = tbName;
        this.pageModel = pageModel;
        this.queue = queue;
    }

    @Override
    public void run() {
        OracleOperateServiceImpl oracleOperateService = SpringContextUtils.getApplicationContext().getBean(OracleOperateServiceImpl.class);
        List<HashMap> pageData = oracleOperateService.queryTableByPage(tbName, pageModel);
        //等待入队，直到成功为止。
        while (true) {
            try {
                queue.offer(pageData, 3000L, TimeUnit.MILLISECONDS);
                break;
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
