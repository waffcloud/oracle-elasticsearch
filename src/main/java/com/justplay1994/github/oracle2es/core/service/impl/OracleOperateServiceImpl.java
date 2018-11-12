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

import com.justplay1994.github.oracle2es.core.config.Oracle2esConfig;
import com.justplay1994.github.oracle2es.core.dao.TableMapper;
import com.justplay1994.github.oracle2es.core.service.model.ColumnModel;
import com.justplay1994.github.oracle2es.core.service.model.PageModel;
import com.justplay1994.github.oracle2es.core.service.model.TableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Package: com.justplay1994.github.oracle2es.core.service
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 18:38
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 18:38
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@Service
public class OracleOperateServiceImpl{

    @Autowired
    TableMapper tableMapper;

    @Autowired
    Oracle2esConfig config;

    //查询表结构，以及表的记录总数
    public HashMap<String, TableModel> queryAllTableStructure() {
        HashMap<String, TableModel> result = new HashMap<String, TableModel>();
        List<HashMap> structures = tableMapper.queryAllTableStructure();
        for (HashMap map: structures){
            String tbName = String.valueOf(map.get("TABLE_NAME"));
            String colName = String.valueOf(map.get("COLUMN_NAME"));
            String colType = String.valueOf(map.get("DATA_TYPE"));
            BigDecimal num_rows = (BigDecimal) map.get("NUM_ROWS");
            if (isSkip(tbName)) continue;
            ColumnModel columnModel = new ColumnModel(colName, colType);
            if (result.get(tbName) == null) {   //一张新表
                TableModel tableModel = new TableModel(columnModel, num_rows.intValue());
                result.put(tbName, tableModel);
            }else {
                result.get(tbName).getColumnModels().add(columnModel);
            }
        }
        return result;
    }

    //
    public List<HashMap> queryTableByPage(String tbName, PageModel pageModel) {
        return tableMapper.queryTableByPage(tbName, pageModel);
    }


    public boolean isSkip(String tbName) {
        String[] skipReadTB = config.getSkipReadTB();
        String[] justReadTB = config.getJustReadTB();

        if (justReadTB != null){//当只读配置不为空时，范围为只读表
            for (int i = 0; i < justReadTB.length; ++i){
                if (justReadTB[i].equalsIgnoreCase(tbName)){    //在只需读的范围，则不跳过。
                    return false;
                }
            }
            return true;    //不在只读范围，跳过
        } else {    //当只读配置为空时，则默认读全部表.
            if (skipReadTB != null){
                for (int i = 0; i < skipReadTB.length; ++i){
                    if (skipReadTB[i].equalsIgnoreCase(tbName)){
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
    }
}
