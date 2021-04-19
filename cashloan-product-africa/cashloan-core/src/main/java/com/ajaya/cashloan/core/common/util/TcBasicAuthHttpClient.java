package com.ajaya.cashloan.core.common.util;

import com.ajaya.cashloan.core.common.context.Global;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * 功能说明：基于安全认证的http请求工具类
 *
 * @author yanzhiqiang
 * @since 2020-02-15 16:06
 **/
public class TcBasicAuthHttpClient {

    private static final Logger log = LoggerFactory.getLogger(TcBasicAuthHttpClient.class);

    /**
     * 发送post请求
     *
     * @param url  请求地址
     * @param data 请求实体
     * @return String
     * @author wangxy
     * @date 2018年5月10日 下午4:36:17
     */
    public static String sendPost(String url, String data) {

        log.info("进入post请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：data=" + data);
        // 请求返回结果
        String resultJson = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(Global.getValue("ct_id"), Global.getValue("ct_key")));
            CloseableHttpClient createDefault = HttpClients.custom()
                    .setDefaultCredentialsProvider(credsProvider)
                    .build();

            StringEntity entity = new StringEntity(data, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            //执行请求
            CloseableHttpResponse response = createDefault.execute(httpPost);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            // 获取响应结果
            resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            log.error("发送post请求失败", e);
        } finally {
            httpPost.releaseConnection();
        }
        log.info("请求返回结果：result=" + resultJson);
        return resultJson;
    }

    /**
     * 模拟表单放请求
     * @param url
     * @param param
     * @param id
     * @param key
     * @return
     */
    public static String sendPost(String url, HashMap<String,Object> param,String id,String key) {

        log.info("进入post请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：data=" + JsonUtil.toString(param));
        // 请求返回结果
        String resultJson = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(id, key));
            CloseableHttpClient createDefault = HttpClients.custom()
                    .setDefaultCredentialsProvider(credsProvider)
                    .build();
            StringEntity entity = new StringEntity(JsonUtil.toString(param), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            //执行请求
            CloseableHttpResponse response = createDefault.execute(httpPost);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            // 获取响应结果
            resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            log.error("发送post请求失败", e);
        } finally {
            httpPost.releaseConnection();
        }
        log.info("请求返回结果：result=" + resultJson);
        return resultJson;
    }

    /**
     * 发送get请求
     * @param url 地址
     * @param headers 请求头map
     * @param params 参数map
     * @param encoding 编码
     * @return 返回结果
     */
    public static String sentGet(String url, Map<String, String> headers, Map<String, String> params , String encoding, String id , String key) {
        log.info("进入get请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：headers=" + JSON.toJSONString(headers));
        log.info("请求入参：data=" + JSON.toJSONString(params));

        long  startTime= System.currentTimeMillis();
        // 请求返回结果
        String resultJson = null;

        // 设置请求地址
        if (MapUtils.isNotEmpty(params)) {
            url = url + buildUrl(params);
        }
        HttpGet httpGet = new HttpGet(url);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(id, key));
        // 创建Client
        CloseableHttpClient createDefault = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();

        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(800000).setConnectionRequestTimeout(800000)
                    .setSocketTimeout(800000).build();
            httpGet.setConfig(requestConfig);
            // 设置请求头
            if (MapUtils.isNotEmpty(headers)) {
                for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                    httpGet.setHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
                }
            }
            // 发送请求,返回响应对象
            CloseableHttpResponse response = createDefault.execute(httpGet);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应结果
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            } else {
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
                log.error("响应失败，状态码：" + status);
            }

        } catch (Exception e) {
            log.error("发送get请求失败", e);
        } finally {
            httpGet.releaseConnection();
        }
        long  endTime= System.currentTimeMillis();
        log.info("请求返回结果：result=" + resultJson);
        log.info("请求响应时间：" + (endTime-startTime)+"毫秒");
        return resultJson;
    }
    /**
     * 构造get请求的参数
     *
     * @return 完整url
     */
    private static String buildUrl(Map<String, String> map) {
        if (MapUtils.isEmpty(map)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer("?");
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            stringBuffer.append(stringStringEntry.getKey()).append("=").append(stringStringEntry.getValue()).append("&");
        }
        String result = stringBuffer.toString();
        if (StringUtils.isNotEmpty(result)) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
