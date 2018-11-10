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

package com.justplay1994.github.oracle2es.core.service.model;

import lombok.Data;

/**
 * @Package: com.justplay1994.github.oracle2es.core.service.model
 * @Project: oracle-elasticsearch
 * @Creator: huangzezhou
 * @Create_Date: 2018/11/10 19:05
 * @Updater: huangzezhou
 * @Update_Date: 2018/11/10 19:05
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@Data
public class PageModel {

    private int pageNum;    //页码号,从1开始
    private int pageSize;   //页大小

    /**
     * 获取开始下标，从1开始
     * @return
     */
    public int getStartNum(){
        return (pageNum-1) * pageSize + 1;
    }

    /**
     * 获取最后一个数的下标，从1开始
     * @return
     */
    public int getEndNum(){
        return pageNum * pageSize;
    }

}
