package com.justplay1994.github.oracle2es.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justplay1994.github.oracle2es.core.service.model.mapping.*;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectMapperTest {

    @Test
    public void test() throws JsonProcessingException {
        Model1 model1 = new Model1();
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(model1);
        System.out.println(result);
    }

    @Test
    public void mapping() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PropertiesModel model = new PropertiesModel();
        String result = mapper.writeValueAsString(model);
        System.out.println(result);
    }

    @Test
    public void createMapping() throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        PropertiesModel propertiesModel = new PropertiesModel();
//        propertiesModel.add("location", new LocationTypeModel());
//        propertiesModel.add("column2", new DateTypeModel());
//        propertiesModel.add("column3", new TextTypeModel());
//        MappingModel mappingModel = new MappingModel(propertiesModel);
//        String result = mapper.writeValueAsString(mappingModel);
//        System.out.println(result);
    }

}

@Data
class Model1{
    Model2 model2;

    Model1(){
        model2 = new Model2();
    }
}

@Data
class Model2{
    String key="key";
    String value="value";
}
