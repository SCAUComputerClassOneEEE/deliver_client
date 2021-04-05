package utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;


public class HttpGetCallable extends HttpRequestCallable {
    private final HttpGet httpGet;

    private final CloseableHttpClient httpClient;

    HttpGetCallable(CloseableHttpClient httpClient, HttpGet httpGet) {
        this.httpGet = httpGet;
        this.httpClient = httpClient;
    }

    @Override
    public HttpResponse call() throws Exception {
        return httpClient.execute(httpGet);
    }

    public void close() throws IOException {
        this.httpClient.close();
    }

}
