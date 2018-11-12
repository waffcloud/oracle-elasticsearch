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

package com.justplay1994.github.oracle2es.core.service;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.justplay1994.github.oracle2es.core.service
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/12 14:12
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/12 14:12
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
public class LinkedBlockingTest {

    @Test
    public void test() throws InterruptedException {
        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        queue.offer("123");
        queue.offer("123");
        queue.offer("123");

        System.out.println(queue.poll(3000L, TimeUnit.MILLISECONDS));
        System.out.println(queue.poll(3000L, TimeUnit.MILLISECONDS));
        System.out.println(queue.poll(3000L, TimeUnit.MILLISECONDS));
        System.out.println(queue.poll(3000L, TimeUnit.MILLISECONDS));
    }

}
