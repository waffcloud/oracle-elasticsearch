package com.justplay1994.github.oracle2es.framework.controller.model;

import java.util.HashMap;

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
