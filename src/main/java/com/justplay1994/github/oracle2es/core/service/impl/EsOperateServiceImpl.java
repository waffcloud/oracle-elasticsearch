package com.justplay1994.github.oracle2es.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justplay1994.github.oracle2es.core.config.Oracle2esConfig;
import com.justplay1994.github.oracle2es.core.service.model.ColumnModel;
import com.justplay1994.github.oracle2es.core.service.model.DatabaseModel;
import com.justplay1994.github.oracle2es.core.service.model.TableModel;
import com.justplay1994.github.oracle2es.core.service.model.mapping.IkTypeModel;
import com.justplay1994.github.oracle2es.core.service.model.mapping.MappingModel;
import com.justplay1994.github.oracle2es.core.service.model.mapping.PropertiesModel;
import com.justplay1994.github.oracle2es.framework.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */

@Service
public class EsOperateServiceImpl {

    @Autowired
    Oracle2esConfig config;

    public String indexName(String tbName) {
        String dbName = StringUtils.isEmpty(config.getIndexDb()) ? config.getOwner() : config.getIndexDb();
        return tbName + "@" + dbName.toLowerCase();
    }

    /**
     * 处理逻辑一定要有入参，方便aop做around环绕通知，修改入参。
     *
     * @param databaseModel
     */
    public void createMapping(DatabaseModel databaseModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getMaxCreateMapping(),
                config.getMaxCreateMapping(), 100, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>());
        for (String tbName : databaseModel.tbs.keySet()) {    //遍历表
            TableModel tableModel = databaseModel.tbs.get(tbName);
            final String url = config.getEsUrl() + indexName(tbName);
            PropertiesModel propertiesModel = new PropertiesModel();
            for (ColumnModel columnModel : tableModel.getColumnModels()) {    //遍历列
                //TODO 根据具体类型判断，来调试。
                propertiesModel.add(columnModel.getName(), new IkTypeModel());
            }
            MappingModel mappingModel = new MappingModel(propertiesModel);
            final String params;
            try {
                params = objectMapper.writeValueAsString(mappingModel);
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpClientUtil.put(url, params);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        while (executor.getActiveCount() != 0){
            System.out.println("create mapping: "+executor.getCompletedTaskCount()+"/"+executor.getTaskCount());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    //TODO
    public List<String> generatorBulk(String tbName, List<HashMap> pageData) {
        for (HashMap row: pageData){

        }
        return null;
    }

}
