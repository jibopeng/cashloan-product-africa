package com.ajaya.cashloan.core.common.util;

import com.alibaba.fastjson.JSON;
import com.ajaya.cashloan.core.common.model.HttpClientUtilResponse;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;


/**
 * 功能说明： (HttpClient工具类)
 *
 * @author yanzhiqiang
 * @since 2020-02-13 11:12
 */
public class HttpClientKudosUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpClientKudosUtil.class);

    /**
     * 发送post请求
     * @param url      请求地址
     * @param headers  请求头map
     * @param data     请求json
     * @param encoding 字符集
     * @return String
     */
    public static HttpClientUtilResponse sendPost(String url, Map<String, String> headers, String data, String encoding) {
        log.info("进入post请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：headers=" + JSON.toJSONString(headers));
        log.info("请求入参：data=" + ((data != null && data.length() > 1000) ? data.substring(0, 1000) : data));
        HttpClientUtilResponse httpClientUtilResponse = new HttpClientUtilResponse();
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
            httpPost.setEntity(new StringEntity(data,"UTF-8"));
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPost);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            // 获取响应结果
            resultJson = EntityUtils.toString(response.getEntity(), encoding);
            httpClientUtilResponse.setHttpStatus(status);
            httpClientUtilResponse.setResult(resultJson);
        } catch (Exception e) {
            log.error("发送post请求失败", e);
        } finally {
            httpPost.releaseConnection();
        }
        log.info("请求返回结果：result=" + resultJson);
        return httpClientUtilResponse;
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


}