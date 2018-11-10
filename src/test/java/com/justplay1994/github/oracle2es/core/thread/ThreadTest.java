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

package com.justplay1994.github.oracle2es.core.thread;

import org.junit.Test;

/**
 * @Package: com.justplay1994.github.oracle2es.core.thread
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 15:52
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 15:52
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 *
 * 多线程会共享变量（不安全）：
 * 情况1：new 一个Runnable对象，然后new 多个对象，那么这多个对象会共享runnable里面的值。
 * 情况2：new 多个Runnable的时候，传入同一个对象，再起多个线程，那么会共享这个对象。
 * 其他情况都不会共享(安全)：
 * 3. new多个Runnable，直接每个都是独立的。
 * 4. new一个Runnale，但是里面的对象不传入多个，而是Runbale自己创建的。
 * 未知情况
 *
 *
 * 总结：线程安全与否，与public、private目前看来关系不大，而是看起操作的对象是否是同一个（指针指向的是否是同一个）
 * 如果变量不共享，则不存在线程安全问题，如果变量共享，做需要考虑线程安全的问题。
 * 最好是将共享的变量进行严格封装，然后提供线程安全的操作对外，这样达到线程安全的目的。
 **/
public class ThreadTest {

    @Test
    public void test1() throws InterruptedException {
        Runnable runnable = new TestModel("thread");
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

        while(thread1.isAlive() || thread2.isAlive()){
            Thread.sleep(100);
        }

    }

    @Test
    public void test2() throws InterruptedException {
        ObjectTest objectTest = new ObjectTest("run",100);
        Runnable runnable1 = new TestModel(objectTest);
        Runnable runnable2 = new TestModel(objectTest);
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        while(thread1.isAlive() || thread2.isAlive()){
            Thread.sleep(100);
        }

    }

    @Test
    public void test3() throws InterruptedException {
        Runnable runnable1 = new TestModel();
        Runnable runnable2 = new TestModel();
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        while(thread1.isAlive() || thread2.isAlive()){
            Thread.sleep(100);
        }

    }
}
