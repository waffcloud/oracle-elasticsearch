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

package com.justplay1994.github.oracle2es.core.service.impl;

import com.justplay1994.github.oracle2es.core.service.model.DatabaseModel;
import com.justplay1994.github.oracle2es.core.service.model.TableModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Package: com.justplay1994.github.oracle2es.core.service
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 19:14
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 19:14
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@Service
public class EsOperateByTableServiceImpl extends TableModel{

    /**
     * 创建该表的mapping映射
     */
    public void createMapping(){

    }

    /**
     * 解析数据，产生bulk语句（es批量插入语句）,每一个String对象，就是一条数据插入的bulk语句。
     * 产生了该语句，并不需要立即执行，而是累积到一定数量在执行。
     * @param rows
     * @return
     */
    private List<String> generatorBulk(List<HashMap> rows){
        return null;
    }



}
