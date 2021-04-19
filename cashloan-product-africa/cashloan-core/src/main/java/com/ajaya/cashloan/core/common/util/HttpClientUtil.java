package com.ajaya.cashloan.core.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能说明： (HttpClient工具类)
 *
 * @author yanzhiqiang
 * @since 2020-02-13 11:12
 */
public class HttpClientUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 发送post请求
     *
     * @param url      请求地址
     * @param headers  请求头map
     * @param data     请求json
     * @param encoding 字符集
     * @return String
     */
    public static String sendPost(String url, Map<String, String> headers, String data, String encoding) {
        log.info("进入post请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：headers=" + JSON.toJSONString(headers));
        log.info("请求入参：data=" + data);
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setConnectionRequestTimeout(200000)
                .setSocketTimeout(200000).build();
        httpPost.setConfig(requestConfig);

        try {
            // 设置请求地址
            httpPost.setURI(new URI(url));
            // 设置请求头
            setHeader(headers, httpPost);
            // 设置实体
            httpPost.setEntity(new StringEntity(data, "UTF-8"));
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPost);
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
            log.error("发送post请求失败", e);
        } finally {
            httpPost.releaseConnection();
        }
        log.info("请求返回结果：result=" + resultJson);
        return resultJson;
    }

    private static void setHeader(Map<String, String> headers, HttpPost httpPost) {
        if (headers != null) {
            Header[] allHeader = new BasicHeader[headers.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                i++;
            }
            httpPost.setHeaders(allHeader);
        }
    }

    /**
     * 发送post请求
     *
     * @param url      请求地址
     * @param headers  请求头map
     * @param param    请求参数map
     * @param encoding 字符集
     * @return String
     */
    public static String sendPost(String url, Map<String, String> headers, Map<String, String> param, String encoding) {
        log.info("进入post请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：headers=" + JSON.toJSONString(headers));
        Map<String, String> param2 = new HashMap<>();
        param2.putAll(param);
        param2.remove("borrower_pan_doc");
        param2.remove("borrower_adhaar_doc");
        param2.remove("borrower_photo_doc");
        //loan_sanction_letter
        //loan_agreement_doc
        param2.remove("loan_agreement_doc");
        param2.remove("loan_sanction_letter");
        param2.remove("document");
        log.info("请求入参：data=" + JSON.toJSONString(param2));
        long startTime = System.currentTimeMillis();
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(800000).setConnectionRequestTimeout(800000)
                .setSocketTimeout(800000).build();
        httpPost.setConfig(requestConfig);

        try {
            // 设置请求地址
            httpPost.setURI(new URI(url));
            // 设置请求头
            setHeader(headers, httpPost);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
                System.out.println("entity--length:" + entity.getContentLength());
            }
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPost);
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
            log.error("发送post请求失败", e);
        } finally {
            httpPost.releaseConnection();
        }
        long endTime = System.currentTimeMillis();
        log.info("请求响应时间：" + (endTime - startTime) + "毫秒");
        log.info("请求返回结果：result=" + resultJson);
        return resultJson;
    }

    /**
     * 发送get请求
     *
     * @param url      地址
     * @param headers  请求头map
     * @param params   参数map
     * @param encoding 编码
     * @return 返回结果
     */
    public static String sentGet(String url, Map<String, String> headers, Map<String, String> params, String encoding) {
        log.info("进入get请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：headers=" + JSON.toJSONString(headers));
        log.info("请求入参：data=" + JSON.toJSONString(params));

        long startTime = System.currentTimeMillis();
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 设置请求地址
        if (MapUtils.isNotEmpty(params)) {
            url = url + buildUrl(params);
        }
        URI uri = null;
        try {
            URL urls =   urls = new URL(url);
            uri = new URI(urls.getProtocol(), urls.getHost(), urls.getPath(), urls.getQuery(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpGet   httpGet = new HttpGet(uri);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(8000).setConnectionRequestTimeout(8000)
                    .setSocketTimeout(8000).build();
            httpGet.setConfig(requestConfig);
            // 设置请求头
            if (MapUtils.isNotEmpty(headers)) {
                for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                    httpGet.setHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
                }
            }
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpGet);
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
        long endTime = System.currentTimeMillis();
        log.info("请求返回结果：result=" + resultJson);
        log.info("请求响应时间：" + (endTime - startTime) + "毫秒");
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

    /**
     * 发送get请求
     *
     * @param url     地址
     * @param headers 请求头map
     * @param params  参数map
     * @param fos     输出流
     * @return 返回结果
     */
    public static int sentGetDownLoad(String url, Map<String, String> headers, Map<String, String> params, OutputStream fos) {
        log.info("进入get请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：headers=" + JSON.toJSONString(headers));
        log.info("请求入参：data=" + JSON.toJSONString(params));

        long startTime = System.currentTimeMillis();
        // 请求返回结果
        int status = 400;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 设置请求地址
        if (MapUtils.isNotEmpty(params)) {
            url = url + buildUrl(params);
        }
        HttpGet httpGet = new HttpGet(url);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setConnectionRequestTimeout(20000)
                    .setSocketTimeout(20000).build();
            httpGet.setConfig(requestConfig);
            // 设置请求头
            if (MapUtils.isNotEmpty(headers)) {
                for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                    httpGet.setHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
                }
            }
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpGet);
            // 获取响应状态
            status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应结果
                response.getEntity().writeTo(fos);
            } else {
                response.getEntity().writeTo(fos);
                log.error("响应失败，状态码：" + status);
            }

        } catch (Exception e) {
            log.error("发送get请求失败", e);
        } finally {
            httpGet.releaseConnection();
        }
        long endTime = System.currentTimeMillis();
        log.info("请求返回状态：status=" + status);
        log.info("请求响应时间：" + (endTime - startTime) + "毫秒");
        return status;
    }
}