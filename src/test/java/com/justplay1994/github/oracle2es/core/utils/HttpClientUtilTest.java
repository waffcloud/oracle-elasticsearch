package com.justplay1994.github.oracle2es.core.utils;

import com.justplay1994.github.oracle2es.framework.utils.HttpClientUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */

public class HttpClientUtilTest {

    @Test
    public void getTest() throws IOException, URISyntaxException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8,8,100, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>());
        for (int i = 0; i < 1000; ++i) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpClientUtil.get("http://localhost:9002/", null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        while (executor.getActiveCount() != 0){
            System.out.println(executor.getCompletedTaskCount()+"/"+executor.getTaskCount());
            Thread.sleep(1000);
        }
        System.out.println(executor.getCompletedTaskCount() + "/" + executor.getTaskCount());
        executor.shutdown();
    }

}
