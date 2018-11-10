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

package com.justplay1994.github.oracle2es.core.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Package: com.justplay1994.github.db2es.config
 * @Project: db2es
 * @Description:   //TODO
 * @Creator: huangzezhou
 * @Create_Date: 2018/9/19 19:34
 * @Updater: huangzezhou
 * @Update_Date: 2018/9/19 19:34
 * @Update_Description: huangzezhou 补充
 **/
@Component
@ConfigurationProperties(prefix = "oracle2es")
//@PropertySource("classpath:config/oracle2es.properties")// 用来指定配置文件的位置
public class Oracle2esConfig {

    //由于oracle受限情况下，不能使用all_tables查询tablespace，所以查询不到tablespace，只能讲tablespace用owner替代了
    private String owner;

    //命名空间
    private String tableSpace;

    //只读的数据库列表，为空则读全库.eg: dbName
    private String justReadDB;

    //只读的表的列表，为空则读所有表。eg: dbName.tbName
    private String justReadTB;

    //需要跳过的库的列表，为空则不跳过。eg: dbName
    private String skipReadDB;

    //需要跳过的表的列表，为空则不跳过。eg: dbName.tbName
    private String skipReadTB;

    private String indexDB;/*索引使用的库名*/

    public String getOwner() {
        return owner;
    }

    /*------ util -------------*/

    public boolean isNull(String[] s){
        if (s == null)return true;
        if (s.length==1 && s[0].equals(""))return true;
        return false;
    }

    /**
     * 将字符串数组化，用逗号隔开
     * @return
     */
    public String[] arrayJustReadDB(){
        justReadDB = justReadDB.replaceAll(" ","");
        return justReadDB.split(",");
    }

    public String[] arrayJustReadTB(){
        justReadTB = justReadTB.replaceAll(" ","");
        return justReadTB.split(",");
    }

    public String[] arraySkipReadDB(){
        skipReadDB = skipReadDB.replaceAll(" ","");
        return skipReadDB.split(",");
    }

    public String[] arraySkipReadTB(){
        skipReadTB = skipReadTB.replaceAll(" ","");
        return skipReadTB.split(",");
    }

    /*-------- auto generator getter and setter ---------------*/

    public String getJustReadDB() {
        return justReadDB;
    }

    public void setJustReadDB(String justReadDB) {
        this.justReadDB = justReadDB;
    }

    public String getJustReadTB() {
        return justReadTB;
    }

    public void setJustReadTB(String justReadTB) {
        this.justReadTB = justReadTB;
    }

    public String getSkipReadDB() {
        return skipReadDB;
    }

    public void setSkipReadDB(String skipReadDB) {
        this.skipReadDB = skipReadDB;
    }

    public String getSkipReadTB() {
        return skipReadTB;
    }

    public void setSkipReadTB(String skipReadTB) {
        this.skipReadTB = skipReadTB;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTableSpace() {
        return tableSpace;
    }

    public void setTableSpace(String tableSpace) {
        this.tableSpace = tableSpace;
    }

    public String getIndexDB() {
        return indexDB;
    }

    public void setIndexDB(String indexDB) {
        this.indexDB = indexDB;
    }
}
