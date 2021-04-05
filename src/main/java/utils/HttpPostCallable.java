package utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class HttpPostCallable extends HttpRequestCallable {
    private final HttpPost httpPost;

    private final CloseableHttpClient httpClient;

    HttpPostCallable(CloseableHttpClient httpClient, HttpPost httpPost) {
        this.httpPost = httpPost;
        this.httpClient = httpClient;
    }

    @Override
    public HttpResponse call() throws Exception {
        return httpClient.execute(httpPost);
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }
}
