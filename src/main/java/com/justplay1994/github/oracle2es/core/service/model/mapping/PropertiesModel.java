package com.justplay1994.github.oracle2es.core.service.model.mapping;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */
@Data
public class PropertiesModel {
    HashMap properties = new HashMap();

    public void add(String colName, Object property){
        properties.put(colName, property);
    }
}
