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

import com.justplay1994.github.oracle2es.core.config.Oracle2esConfig;
import com.justplay1994.github.oracle2es.core.service.model.current.ProcessBar;
import com.justplay1994.github.oracle2es.framework.utils.SpringContextUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Package: com.justplay1994.github.oracle2es.core.service.model
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 17:25
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 17:25
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@Data
public class TableModel {
    private List<ColumnModel> columnModels;   //表结构
    private LinkedBlockingQueue<List<HashMap>> rows;  //数据队列,每次分页查询的结果入队。
    private ProcessBar processBar;
    private int totalNumber;
    private boolean bulkGeneratorFinished;

    public TableModel(ColumnModel columnModel, int totalNumber){
        columnModels = new ArrayList<ColumnModel>();
        rows = new LinkedBlockingQueue<List<HashMap>>();
        this.totalNumber = totalNumber;
        columnModels.add(columnModel);
        processBar = new ProcessBar(totalNumber);
    }

    public boolean isQueryDataFinished(){
        return processBar.getFinishedNumber() == totalNumber;
    }
}
