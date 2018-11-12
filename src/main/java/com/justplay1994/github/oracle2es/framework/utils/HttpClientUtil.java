package com.justplay1994.github.oracle2es.framework.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JustPlay1994 on 2018/11/11.
 * https://github.com/JustPlay1994
 */

public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    static PoolingHttpClientConnectionManager cm;
    static CloseableHttpClient httpClient;
    static {

        cm = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加到200
        cm.setMaxTotal(200);
        // 将每个路由基础的连接增加到20
        cm.setDefaultMaxPerRoute(20);
        //将目标主机的最大连接数增加到50
        HttpHost localhost = new HttpHost("10.192.19.161", 80);
        cm.setMaxPerRoute(new HttpRoute(localhost), 50);

        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

    }

    public static String put(String url, String params) throws IOException {
        String result = "";

        HttpPut httpPut = new HttpPut(url);

        httpPut.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPut.setEntity(new StringEntity(params));
        HttpResponse response = httpClient.execute(httpPut);
        StatusLine status = response.getStatusLine();                   //获取返回的状态码
        HttpEntity entity = response.getEntity();                       //获取响应内容
        result = EntityUtils.toString(entity, "UTF-8");
        if (! (status.getStatusCode() == HttpStatus.SC_OK) ) {
            logger.error("put request error:\n"+result);
        }
        httpPut.abort();//中止请求，连接被释放回连接池
        return result;
    }

    public static String post(String url, String params) throws IOException {
        String result = "";

        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(params));
        HttpResponse response = httpClient.execute(httpPost);
        StatusLine status = response.getStatusLine();                   //获取返回的状态码
        HttpEntity entity = response.getEntity();                       //获取响应内容
        result = EntityUtils.toString(entity, "UTF-8");
        if (! (status.getStatusCode() == HttpStatus.SC_OK) ) {
            logger.error("post request error:\n"+result);
        }
        httpPost.abort();//中止请求，连接被释放回连接池
        return result;
    }

//    public static String put(String url, Map<String, String> paramMap) throws IOException {
//        String result = "";
//
//        HttpPut httpPut = new HttpPut(url);
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        if (!MapUtils.isEmpty(paramMap)){
//            for (String key: paramMap.keySet()){
//                params.add(new BasicNameValuePair(key,paramMap.get(key)));
//            }
//        }
//        httpPut.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//        HttpResponse response = httpClient.execute(httpPut);
//        StatusLine status = response.getStatusLine();                   //获取返回的状态码
//        HttpEntity entity = response.getEntity();                       //获取响应内容
//        result = EntityUtils.toString(entity, "UTF-8");
//        if (! (status.getStatusCode() == HttpStatus.SC_OK) ) {
//            logger.error("put request error:\n"+result);
//        }
//        httpPut.abort();//中止请求，连接被释放回连接池
//        return result;
//    }

    public static String get(String url, Map<String, String> paramMap) throws IOException, URISyntaxException {
        String result = "";
        URIBuilder builder = new URIBuilder(url);
        if (!MapUtils.isEmpty(paramMap)) {
            for (String key : paramMap.keySet()) {
                builder.setParameter(key, paramMap.get(key));
            }
        }

        HttpGet httpGet = new HttpGet(builder.build());
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(6000)
                .setConnectTimeout(6000)
                .setConnectionRequestTimeout(6000).build();
        httpGet.setConfig(config);
        HttpResponse response = httpClient.execute(httpGet);
        StatusLine status = response.getStatusLine();                   //获取返回的状态码
        HttpEntity entity = response.getEntity();                       //获取响应内容
        result = EntityUtils.toString(entity, "UTF-8");
        if (! (status.getStatusCode() == HttpStatus.SC_OK) ) {
            logger.error("put request error:\n"+result);
        }
        httpGet.abort();//中止请求，连接被释放回连接池
        return result;
    }

    public static String delete(String url) throws IOException {
        String result = "";

        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("Content-Type", "application/json;charset=UTF-8");
        HttpResponse response = httpClient.execute(httpDelete);
        StatusLine status = response.getStatusLine();                   //获取返回的状态码
        HttpEntity entity = response.getEntity();                       //获取响应内容
        result = EntityUtils.toString(entity, "UTF-8");
        if (! (status.getStatusCode() == HttpStatus.SC_OK) ) {
            logger.error("delete request error:\n"+result);
        }
        httpDelete.abort();//中止请求，连接被释放回连接池
        return result;
    }
}
