package com.justplay1994.github.oracle2es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Package: com.justplay1994.github.oracle2es
 * @Project: oracle-elasticsearch
 * @Description: //TODO
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 11:49
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 11:49
 * @Update_Description: huangzezhou ²¹³ä
 **/

@SpringBootApplication
@ComponentScan
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan({
        "com.justplay1994.github.oracle2es.core.dao"
})
public class Oracle2esApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Oracle2esApplication.class, args);
    }
}
