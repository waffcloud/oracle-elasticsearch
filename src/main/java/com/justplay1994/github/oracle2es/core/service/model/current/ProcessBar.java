package com.justplay1994.github.oracle2es.core.service.model.current;

import lombok.Data;
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

    private int totalNumber;    //总数据量
    private int finishedNumber = 0; //已完成数据量，包含已失败的数量
    private int failedNumber = 0; //已失败的数量。

    public ProcessBar(int totalNumber){
        this.totalNumber = totalNumber;
    }

    public int getFinishedNumber(){
        return finishedNumber;
    }

    synchronized public void addTotalNumber(int addNumber){
        totalNumber += addNumber;
    }

    synchronized public void addFinishedNumber(int addNumber){
        finishedNumber += addNumber;
    }

    synchronized public void addFailedNumber(int addNumber){failedNumber += addNumber;}

    public String print(){
        double finishedPercent = finishedNumber * 1.0 / totalNumber * 100;
        double failedPercent = failedNumber * 1.0 / finishedPercent * 100;
        return "finished : "+finishedPercent +"%("+failedPercent+"% failed) "+
                "[total: "+totalNumber+", finished: "+finishedNumber+", failed: "+ failedNumber+"]";
    }
}
