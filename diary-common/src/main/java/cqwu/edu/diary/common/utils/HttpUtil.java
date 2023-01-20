package cqwu.edu.diary.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private static final String APPLICATION_JSON = "application/json";
    private static final CloseableHttpClient HTTPCLIENT;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final RequestConfig CONFIG;

    static {
        CONFIG = RequestConfig.custom().setConnectTimeout(60000)
                .setSocketTimeout(15000).build();
        HTTPCLIENT = HttpClientBuilder.create().setDefaultRequestConfig(CONFIG)
                .build();
    }

    private HttpUtil() {

    }

    /**
     * post 请求
     *
     * @param serverUrl 请求URL
     * @param path      路径
     * @param json      请求数据
     * @param timeout   请求超时时间
     * @return java.lang.String
     */
    public static String postRequest(String serverUrl, String path, String json, int timeout) {

        return postRequest(serverUrl, path, json, timeout, null);
    }

    /**
     * post请求
     *
     * @param serverUrl 请求URL
     * @param path      路径
     * @param json      请求数据
     * @param timeout   超时时间
     * @param headers   请求头
     * @return java.lang.String
     */
    public static String postRequest(String serverUrl, String path, String json, int timeout, Collection<Header> headers) {

        String result = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = HTTP_PREFIX + serverUrl + path;
        HttpPost httpPost = new HttpPost(url);
        if (!StringUtils.isEmpty(json) && json.length() < 1024) {
            LOGGER.info("=> postRequest request: {},\n json{} ", url, json);
        } else {
            LOGGER.info("=> postRequest request: {}\n too large content", url);
        }
        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(timeout)
            .setConnectTimeout(2000)
            .build();
        httpPost.setConfig(requestConfig);
        try {
            StringEntity se = new StringEntity(json, "UTF-8");
            se.setContentType("application/json;charset=UTF-8");
            se.setContentEncoding(StandardCharsets.UTF_8.toString());
            httpPost.setEntity(se);
            //设置请求的Header参数
            if (null != headers && !headers.isEmpty()) {
                httpPost.setHeaders(headers.toArray(new Header[headers.size()]));
            }
            CloseableHttpResponse response1 = httpclient.execute(httpPost);
            LOGGER.info("=> postRequest response1.getStatusLine(): {}", response1.getStatusLine());
            if (response1.getStatusLine()
                    .getStatusCode() == HttpStatus.SC_OK) {
                result = response1.getStatusLine()
                    .toString();
                HttpEntity entity = response1.getEntity();
                String jsonStr = EntityUtils.toString(entity, StandardCharsets.UTF_8.toString());
                EntityUtils.consume(entity);
                result = jsonStr;
            }
            return result;
        } catch (IOException e) {
            LOGGER.error("postRequest method exception",e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOGGER.error("postRequest method close client exception", e);
            }
        }
        return result;
    }

    public static String doPostJson(final String url, final String param) {
        if (StringUtils.isBlank(url)) {
            LOGGER.error("doPostJson method param url is null");
            throw new RuntimeException("doPostJson method param url is null");
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity st = new StringEntity(param, APPLICATION_JSON, DEFAULT_CHARSET);
            httpPost.addHeader("Content-Type", APPLICATION_JSON);
            httpPost.setEntity(st);
            httpPost.setConfig(CONFIG);
            CloseableHttpResponse response = HTTPCLIENT.execute(httpPost);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :"
                        + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, DEFAULT_CHARSET);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            LOGGER.error("doPostJson method close client exception", e);
            throw new RuntimeException("doPostJson method close client exception");
        }
    }

}