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

import lombok.Data;

/**
 * @Package: com.justplay1994.github.oracle2es.core.thread
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 15:51
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 15:51
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@Data
public class TestModel implements Runnable{
    public String name;
    private int value=100;
    public ObjectTest objectTest = new ObjectTest("run",100);

    TestModel(){

    }

    TestModel(String name){
        this.name = name;
    }

    TestModel(ObjectTest objectTest){
        this.objectTest = objectTest;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; ++i) {
            value--;
            if (objectTest!=null)
                objectTest.value--;
            System.out.println(this);
        }
    }

//    @Override
//    public void run() {
//        for (int i = 0; i < 100; ++i) {
//            objectTest.value++;
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(this.toString()+":"+objectTest.toString());
//        }
//    }

}
