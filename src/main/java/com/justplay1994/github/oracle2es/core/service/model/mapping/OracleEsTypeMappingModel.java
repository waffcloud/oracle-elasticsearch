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

package com.justplay1994.github.oracle2es.core.service.model.mapping;

import lombok.Data;

import java.util.HashMap;

/**
 * @Package: com.justplay1994.github.oracle2es.core.service
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/12 10:59
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/12 10:59
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
public class OracleEsTypeMappingModel {
    public static HashMap map = new HashMap();

    static {
        //oracle java es
        //VARCHAR2 String
        //NUMBER BigDecimal
        //DATE Timestamp
        map.put("VARCHAR2", new IkTypeModel());
        map.put("NUMBER", new IntegerModel());
        map.put("DATE", new DateTypeModel());
    }

    @Data
    public static class IntegerModel {
        String type = "integer";
    }

    @Data
    public static class IkTypeModel {
        String type = "text";
        String analyzer = "ik_max_word";
        String search_analyzer = "ik_max_word";
    }

    @Data
    public static class DateTypeModel {
        String type = "date";
        String format = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis";
    }

    @Data
    public class LocationTypeModel {
        String type = "geo_point";
    }
}
