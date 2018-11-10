package com.justplay1994.github.oracle2es.core.service.model.current;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Package: com.justplay1994.github.oracle2es.core.service.model.current
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 17:25
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 17:25
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
public class ProcessBar {
    private static final Logger logger = LoggerFactory.getLogger(ProcessBar.class);

    private int totalNumber = 0;    //总数据量
    private int finishedNumber = 0; //已完成数据量

    synchronized public void addTotalNumber(int addNumber){
        totalNumber += addNumber;
    }

    synchronized public void addFinishedNumber(int addNumber){
        finishedNumber += addNumber;
    }

    public String printNumber(){
        return "(totalNumber="+totalNumber+", finishedNumber="+finishedNumber+")";
    }
    public String printPercent(){
        double percent = finishedNumber * 1.0 / totalNumber;
        return "(Has finished " + percent+")";
    }

    public String printAll(){
        return printNumber()+printPercent();
    }
}
