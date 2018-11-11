package com.justplay1994.github.oracle2es.core.service;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */

public class ThreadPoolExcutorTest {


    @Test
    public void test() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(16,16,100, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque());
        Runnable runnable = new Runnable() {
            int i=0;
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                add();
            }

            synchronized void add(){
                System.out.println(++i);
            }
        };
        for (int i = 0; i < 10000; ++i) {
            Thread thread = new Thread(runnable);
            executor.execute(thread);
            System.out.println("activeCount="+executor.getActiveCount());
            System.out.println("completedTaskCount="+executor.getCompletedTaskCount());
            System.out.println("corePoolSize="+executor.getCorePoolSize());
            System.out.println("keepAliveTime="+executor.getKeepAliveTime(TimeUnit.MILLISECONDS));
            System.out.println("largesPoolSize="+executor.getLargestPoolSize());
            System.out.println("maximumPoolSize="+executor.getMaximumPoolSize());
            System.out.println("poolSize="+executor.getPoolSize());
            System.out.println("taskCount="+executor.getTaskCount());
//            while (executor.getQueue().size() >= 16){
//                Thread.sleep(100);
//            }
        }
        System.out.println("\n\n\n\n\n\n\n");
        while (executor.getActiveCount() != 0 ){
            System.out.println("activeCount="+executor.getActiveCount());
            System.out.println("completedTaskCount="+executor.getCompletedTaskCount());
            System.out.println("corePoolSize="+executor.getCorePoolSize());
            System.out.println("keepAliveTime="+executor.getKeepAliveTime(TimeUnit.MILLISECONDS));
            System.out.println("largesPoolSize="+executor.getLargestPoolSize());
            System.out.println("maximumPoolSize="+executor.getMaximumPoolSize());
            System.out.println("poolSize="+executor.getPoolSize());
            System.out.println("taskCount="+executor.getTaskCount());
            Thread.sleep(100);
        }
        System.out.println("\n\n\n\n\n\n\n");
        System.out.println("activeCount="+executor.getActiveCount());
        System.out.println("completedTaskCount="+executor.getCompletedTaskCount());
        System.out.println("corePoolSize="+executor.getCorePoolSize());
        System.out.println("keepAliveTime="+executor.getKeepAliveTime(TimeUnit.MILLISECONDS));
        System.out.println("largesPoolSize="+executor.getLargestPoolSize());
        System.out.println("maximumPoolSize="+executor.getMaximumPoolSize());
        System.out.println("poolSize="+executor.getPoolSize());
        System.out.println("taskCount="+executor.getTaskCount());

        executor.shutdown();
    }

}
