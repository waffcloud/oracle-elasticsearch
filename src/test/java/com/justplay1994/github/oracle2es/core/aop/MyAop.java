/*
 * MIT License
 *
 * Copyright (c) [2018] [oracle-elasticsearch]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.justplay1994.github.oracle2es.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Package: com.justplay1994.github.oracle2es.core.aop
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 17:54
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 17:54
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@Component
@Aspect
public class MyAop {

    @Pointcut("execution(* com.justplay1994.github.oracle2es.core.aop.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("before");
        joinPoint.getArgs()[0] = new ArrayList<HashMap>(){{
            add(new HashMap(){{
                put("change","change");
            }});
        }};
        List list = (List)joinPoint.getArgs()[0];
        list.add(new HashMap(){{
            put("add","add");
        }});

    }

    @AfterReturning(returning = "ret", pointcut = "pointCut()")
    public void after(Object ret){
        System.out.println("after");
    }

    @Around("execution(* com.justplay1994.github.oracle2es.core.aop.*.*(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        args[0]= new ArrayList<HashMap>(){{
            add(new HashMap(){{
                put("change","change");
            }});
        }};
        proceedingJoinPoint.proceed(args);
    }

}
