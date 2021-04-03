package utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

public class HttpPostCallable extends HttpRequestCallable {
    private final HttpPost httpPost;

    private final HttpClient httpClient;

    HttpPostCallable(HttpClient httpClient, HttpPost httpPost) {
        this.httpPost = httpPost;
        this.httpClient = httpClient;
    }

    @Override
    public HttpResponse call() throws Exception {
        return httpClient.execute(httpPost);
    }
}
