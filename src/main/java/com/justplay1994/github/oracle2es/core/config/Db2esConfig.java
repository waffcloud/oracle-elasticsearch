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
 * @Create_Date: 2018/9/26 14:13
 * @Updater: huangzezhou
 * @Update_Date: 2018/9/26 14:13
 * @Update_Description: huangzezhou 补充
 **/
@Component
@ConfigurationProperties(prefix = "db2es")
public class Db2esConfig {

    private String latColumn;

    private String lonColumn;

    private String esUrl;

    private int maxThreadCount;

    private String indexType;

    private String indexDB;

    private int rowQueueSize;

    private int esBulkQueueSize;

    private int esBulkSize;

    private int pageSize;

    private long queueWaitTime;

    public String getLatColumn() {
        return latColumn;
    }

    public void setLatColumn(String latColumn) {
        this.latColumn = latColumn;
    }

    public String getLonColumn() {
        return lonColumn;
    }

    public void setLonColumn(String lonColumn) {
        this.lonColumn = lonColumn;
    }

    public int getEsBulkQueueSize() {
        return esBulkQueueSize;
    }

    public void setEsBulkQueueSize(int esBulkQueueSize) {
        this.esBulkQueueSize = esBulkQueueSize;
    }

    public long getQueueWaitTime() {
        return queueWaitTime;
    }

    public void setQueueWaitTime(long queueWaitTime) {
        this.queueWaitTime = queueWaitTime;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowQueueSize() {
        return rowQueueSize;
    }

    public void setRowQueueSize(int rowQueueSize) {
        this.rowQueueSize = rowQueueSize;
    }

    public int getEsBulkSize() {
        return esBulkSize;
    }

    public void setEsBulkSize(int esBulkSize) {
        this.esBulkSize = esBulkSize;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getIndexDB() {
        return indexDB;
    }

    public void setIndexDB(String indexDB) {
        this.indexDB = indexDB;
    }

    public String getEsUrl() {
        return esUrl;
    }

    public int getMaxThreadCount() {
        return maxThreadCount;
    }

    public void setMaxThreadCount(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    public void setEsUrl(String esUrl) {
        this.esUrl = esUrl;
    }
}
