package utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;


public class HttpGetCallable extends HttpRequestCallable implements Cloneable {
    private final HttpGet httpGet;

    private final HttpClient httpClient;

    HttpGetCallable(HttpClient httpClient, HttpGet httpGet) {
        this.httpGet = httpGet;
        this.httpClient = httpClient;
    }

    @Override
    public HttpResponse call() throws Exception {
        return httpClient.execute(httpGet);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
