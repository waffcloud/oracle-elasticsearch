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

package com.justplay1994.github.oracle2es.core.service.model;

import com.justplay1994.github.oracle2es.core.service.model.current.ProcessBar;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Package: com.justplay1994.github.oracle2es.core.service.model
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 17:08
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 17:08
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
public class DatabaseModel {//数据库

    private static final Logger logger = LoggerFactory.getLogger(DatabaseModel.class);

    public static HashMap<String, TableModel> tbs;
    public static ProcessBar processBar = new ProcessBar(0);
    public static ProcessBar inputDataProcessBar = new ProcessBar(0);
    public static LinkedBlockingQueue<String> bulks;   //es批量插入语句队列,该语句直接执行，内部已包含索引信息。

    public boolean isQueryDataFinished(){
        for (String key: tbs.keySet()){
            TableModel tableModel = tbs.get(key);
            if (!tableModel.isQueryDataFinished()){
                return false;
            }
        }
        return true;
    }

    public boolean isBulkGeneratorFinished(){
        for (String key: tbs.keySet()){
            TableModel tableModel = tbs.get(key);
            if (!tableModel.isBulkGeneratorFinished()){
                return false;
            }
        }
        return true;
    }

}
