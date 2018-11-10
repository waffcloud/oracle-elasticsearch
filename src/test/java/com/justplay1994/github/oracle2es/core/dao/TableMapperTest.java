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

package com.justplay1994.github.oracle2es.core.dao;

import com.justplay1994.github.oracle2es.Oracle2esApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Package: com.justplay1994.github.oracle2es.core.dao
 * @Project: oracle-elasticsearch
 * @Description: //TODO
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 13:56
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 13:56
 * @Update_Description: huangzezhou 补充
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class TableMapperTest {

    @Autowired
    TableMapper tableMapper;

    @Test
    public void queryTableTest(){
        List<HashMap> tables = tableMapper.queryTable("AUTH_ROLES");
        HashMap map = new HashMap(){{
            put("tbName", "all_tab_columns");
            put("OWNER", "ZHFTYJJCPT");
            put("cols", new ArrayList<String>(){{
                add("OWNER");
                add("TABLE_NAME");
                add("COLUMN_NAME");
                add("DATA_TYPE");
            }});
        }};
        List<HashMap> tables1 = tableMapper.queryTableByColumn(map);
        System.out.println();
    }

}
