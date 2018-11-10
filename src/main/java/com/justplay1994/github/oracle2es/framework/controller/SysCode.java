package com.justplay1994.github.oracle2es.framework.controller;

import java.util.HashMap;

/**
 * @Package: com.cetccity.operationcenter.baseframework.http
 * @Project: unified-auth
 * @Description: //TODO
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/2 14:46
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/2 14:46
 * @Update_Description: huangzezhou 补充
 **/

public class SysCode {

    public static HashMap<Integer,String> map = new HashMap<Integer,String>(){{
        put(SYS_SUCCESS_CODE, SYS_SUCCESS_MESSAGE);
        put(UNKNOWN_ERROR_CODE,UNKNOWN_ERROR_MESSAGE);
    }};

    public static final int SYS_SUCCESS_CODE = 0;
    public static final String SYS_SUCCESS_MESSAGE = "正常响应请求";

    public static final int UNKNOWN_ERROR_CODE = -1;
    public static final String UNKNOWN_ERROR_MESSAGE = "系统发生未知错误";

}
