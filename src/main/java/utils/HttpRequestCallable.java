package utils;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public abstract class HttpRequestCallable implements Callable<HttpResponse> {

    public static class HttpRequestCallableBuilder {
        String url;
        HttpClientThreadPool.HttpMethod method;
        Map<String, Object> obMap;
        HttpClient httpClient;

        long timeOut;
        TimeUnit unit;

        public HttpRequestCallableBuilder(HttpClient httpClient) {
            this.httpClient = httpClient;
        }

        public HttpRequestCallableBuilder addURL(String url) {
            this.url = url;
            return this;
        }

        public HttpRequestCallableBuilder onMethod(HttpClientThreadPool.HttpMethod method) {
            this.method = method;
            return this;
        }

        public HttpRequestCallableBuilder addRequestContent(String s, Object o) throws HttpException {
            if (method.equals(HttpClientThreadPool.HttpMethod.GET) | method.equals(HttpClientThreadPool.HttpMethod.DELETE)) {
                obMap.put(s, o);
            } else if (method.equals(HttpClientThreadPool.HttpMethod.POST)) {

            } else {
                throw new HttpException("not set method.");
            }

            return null;
        }

        public HttpRequestCallableBuilder setTimeOut(long timeOut, TimeUnit unit) {
            this.timeOut = timeOut;
            this.unit = unit;
            return this;
        }

        public HttpRequestCallable build() throws HttpException {
            if (method.equals(HttpClientThreadPool.HttpMethod.GET) | method.equals(HttpClientThreadPool.HttpMethod.DELETE)) {
                URIBuilder builder; List<NameValuePair> pairs; HttpGet httpGet;
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

            } else {
                throw new HttpException("not set method.");
            }

            return null;
        }
    }
}
