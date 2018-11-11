package com.justplay1994.github.oracle2es.core.service.model.mapping;

import com.justplay1994.github.oracle2es.core.config.Oracle2esConfig;
import com.justplay1994.github.oracle2es.framework.utils.SpringContextUtils;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */

/**
 *
 {
     "mappings": {
         "_doc": {
             "properties": {
                 "location": { "type": "geo_point"  },
                 "columnName1":{
                    "type": "text",
                    "analyzer": "ik_max_word",
                    "search_analyzer": "ik_max_word"
                 },
                 "columnName2":{
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
                 },
                 "columnName3":{
                    "type": "text"
                 }
             }
         }
     }
 }
 */
@Data
public class MappingModel {
    private HashMap mappings;

    public MappingModel(PropertiesModel propertiesModel){
        Oracle2esConfig config = new SpringContextUtils().getBean(Oracle2esConfig.class);
        mappings = new HashMap();
        mappings.put(config.getIndexType(), propertiesModel);
    }
}
