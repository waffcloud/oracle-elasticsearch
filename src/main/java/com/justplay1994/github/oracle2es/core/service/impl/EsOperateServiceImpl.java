package com.justplay1994.github.oracle2es.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justplay1994.github.oracle2es.core.config.Oracle2esConfig;
import com.justplay1994.github.oracle2es.core.service.model.ColumnModel;
import com.justplay1994.github.oracle2es.core.service.model.DatabaseModel;
import com.justplay1994.github.oracle2es.core.service.model.TableModel;
import com.justplay1994.github.oracle2es.core.service.model.mapping.MappingModel;
import com.justplay1994.github.oracle2es.core.service.model.mapping.OracleEsTypeMappingModel;
import com.justplay1994.github.oracle2es.core.service.model.mapping.PropertiesModel;
import com.justplay1994.github.oracle2es.framework.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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

    private static final Logger logger = LoggerFactory.getLogger(EsOperateServiceImpl.class);

    @Autowired
    Oracle2esConfig config;

    ObjectMapper objectMapper = new ObjectMapper();

    //索引必须是小写，否则es报错
    public String indexName(String tbName) {
        String dbName = StringUtils.isEmpty(config.getIndexDb()) ? config.getOwner() : config.getIndexDb();
        return (tbName + "@" + dbName).toLowerCase();
    }

    /**
     * 处理逻辑一定要有入参，方便aop做around环绕通知，修改入参。
     *
     * @param databaseModel
     */
    public void createMapping(DatabaseModel databaseModel) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getMaxCreateMapping(),
                config.getMaxCreateMapping(), 100, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>());
        for (String tbName : databaseModel.tbs.keySet()) {    //遍历表
            TableModel tableModel = databaseModel.tbs.get(tbName);
            final String url = config.getEsUrl() + indexName(tbName);
            PropertiesModel propertiesModel = new PropertiesModel();
            for (ColumnModel columnModel : tableModel.getColumnModels()) {    //遍历列
                propertiesModel.add(columnModel.getName(), OracleEsTypeMappingModel.map.get(columnModel.getType()));
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
                            logger.error("create mapping error",e);
                        }
                    }
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        while (executor.getActiveCount() != 0){
            double process = executor.getCompletedTaskCount()*1.0/executor.getTaskCount()*100;
            logger.info("create mapping: " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount()+" "+process+"%");
            Thread.sleep(1000);
        }
        double process = executor.getCompletedTaskCount()*1.0/executor.getTaskCount()*100;
        logger.info("create mapping: " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount()+" "+process+"%");
        executor.shutdown();
        logger.info("create mapping finished!");
    }

    /**
     { "index":{ "_index": "db_poi_test@baidupoi_utf_8", "id":"123", "_type": "_doc"}}
     {"name":"新隆饭店","PHONE":"","X":"114.0779069446","CLA":"[[13,餐饮]]","Y":"22.9998449102232","TEL":"","location":{"lon":"114.0779069446","lat":"22.9998449102232"},"id":"1","ADDR":"广东省东莞市"}
     * @param tbName
     * @param pageData
     * @return
     */
    public List<String> generatorBulk(String tbName, List<HashMap> pageData) throws JsonProcessingException {
        List<String> result = new ArrayList<String>();
        for (HashMap row: pageData){
            String header = "{ \"index\":{ \"_index\": \""+indexName(tbName)+"\", \"_id\":\""+row.get("ROW_ID")+"\", \"_type\": \""+config.getIndexType()+"\"}}";
            HashMap location = new HashMap();
            location.put("lat",row.get(config.getLatColumn()));
            location.put("lon",row.get(config.getLatColumn()));
            row.put("location","location");
            row.remove("ROW_ID");
            String body = objectMapper.writeValueAsString(row);
            String bulk = header+"\n"+body;
            result.add(bulk);
        }
        return result;
    }


    public void deleteAllConflict(DatabaseModel databaseModel) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getMaxCreateMapping(),
                config.getMaxCreateMapping(), 100, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>());
        for (final String tbName : databaseModel.tbs.keySet()) {    //遍历表
            final String url = config.getEsUrl() + indexName(tbName);
            logger.info("delete index : "+indexName(tbName));
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpClientUtil.delete(url);
                    } catch (IOException e) {
                        logger.error("delete mapping error: " + indexName(tbName), e);
                    }
                }
            });
        }
        while (executor.getActiveCount() != 0){
            double process = executor.getCompletedTaskCount()*1.0/executor.getTaskCount()*100;
            logger.info("delete mapping: " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount()+" "+process+"%");
            Thread.sleep(1000);
        }
        double process = executor.getCompletedTaskCount()*1.0/executor.getTaskCount()*100;
        logger.info("delete mapping: " + executor.getCompletedTaskCount() + "/" + executor.getTaskCount()+" "+process+"%");
        executor.shutdown();
        logger.info("delete mapping finished!");
        Thread.sleep(1000);
    }


    public void inputData(String input) throws IOException {
        HttpClientUtil.post(config.getEsUrl()+"_bulk", input);
    }
}
