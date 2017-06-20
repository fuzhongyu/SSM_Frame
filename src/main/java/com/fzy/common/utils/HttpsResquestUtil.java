package com.fzy.common.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.SSLContext;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by fuzhongyu on 2017/5/24.
 */
public class HttpsResquestUtil {

    private static Logger logger= LoggerFactory.getLogger(HttpsResquestUtil.class);

    /**
     * 创建https请求对象
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, new TrustStrategy() {
                        // 信任所有
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            logger.error("请求失败", e);
        }
        return HttpClients.createDefault();
    }
    /**
     * get请求
     * @param url
     * @return
     */
    public static String doGet(String url) throws Exception{
        CloseableHttpClient httpClient = HttpsResquestUtil.createSSLClientDefault();
        HttpGet get = new HttpGet();
        //超时设置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();//设置请求和传输超时时间
        get.setConfig(requestConfig);

        get.setURI(new URI(url));
        HttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;
    }


    /**
     * post请求
     * @param url
     * @param dataBody
     * @return
     */
    public static String doPost(String url, String dataBody) throws Exception{
        CloseableHttpClient httpClient = HttpsResquestUtil.createSSLClientDefault();
        HttpPost httpPost = new HttpPost(new URI(url));
        //超时设置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);

        httpPost.setEntity(new StringEntity(dataBody, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String responseContent = "";
        if (null != entity) {
            responseContent = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        }
        return responseContent;
    }

    /**
     * post请求，修改源方法，可以增加mime type
     * @param url
     * @param dataBody
     * @param contentType
     * @return
     */
    public static String doPost(String url, String dataBody,String contentType) throws Exception{
        CloseableHttpClient httpClient = HttpsResquestUtil.createSSLClientDefault();
        HttpPost httpPost = new HttpPost(new URI(url));
        //超时设置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);

        Header header=new BasicHeader("Content-Type",contentType);
        httpPost.setHeader(header);

        httpPost.setEntity(new StringEntity(dataBody, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String responseContent = "";
        if (null != entity) {
            responseContent = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        }
        return responseContent;
    }
}
