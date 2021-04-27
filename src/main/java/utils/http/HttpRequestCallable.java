package utils.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public abstract class HttpRequestCallable implements Callable<HttpResponse> {

    public abstract void close() throws IOException;

    public static class HttpRequestCallableBuilder {
        public static final String URL = "http://172.18.215.241:8080";

        String url;
        HttpClientThreadPool.HttpMethod method;
        Map<String, Object> obMap;
        CloseableHttpClient httpClient;

        long timeOut;
        TimeUnit unit;

        public HttpRequestCallableBuilder() {
            this.httpClient = HttpClientBuilder.create().build();
        }

        /*添加url地址*/
        public HttpRequestCallableBuilder addURL(String url) {
            this.url = URL + url;
            return this;
        }

        /*http方式*/
        public HttpRequestCallableBuilder onMethod(HttpClientThreadPool.HttpMethod method) {
            this.method = method;
            return this;
        }

        /*请求参数体*/
        public HttpRequestCallableBuilder addRequestContent(String s, Object o) {
            if (obMap == null) {
                obMap = new HashMap<>();
            }
            obMap.put(s, o);
            return this;
        }

        /*超时时间*/
        public HttpRequestCallableBuilder setTimeOut(long timeOut, TimeUnit unit) {
            this.timeOut = timeOut;
            this.unit = unit;
            return this;
        }

        public HttpRequestCallable build() {
            if (method.equals(HttpClientThreadPool.HttpMethod.GET)) {
                URIBuilder builder;
                List<NameValuePair> pairs;
                HttpGet httpGet;
                try {
                    builder = new URIBuilder(url);
                    pairs = new ArrayList<>();
                    obMap.forEach((s, o)->pairs.add(new BasicNameValuePair(s, o.toString())));
                    builder.addParameters(pairs);
                    httpGet = new HttpGet(builder.build());
                    return new HttpGetCallable(httpClient, httpGet);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (method.equals(HttpClientThreadPool.HttpMethod.POST)) {
                HttpPost post;
                post = new HttpPost(url);
                String s = JSONObject.toJSONString(obMap.values().toArray()[0]);
                StringEntity stringEntity = new StringEntity(s, Charset.defaultCharset());
                // System.out.println(stringEntity);
                stringEntity.setContentType("application/json");
                post.setEntity(stringEntity);
                return new HttpPostCallable(httpClient, post);
            } else {

            }

            return null;
        }
    }

}
