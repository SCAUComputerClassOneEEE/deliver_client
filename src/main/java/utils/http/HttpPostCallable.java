package utils.http;

import org.apache.http.HttpResponse;
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
    public HttpResponse call() {
        try {
            return httpClient.execute(httpPost);
        } catch (Exception e) {
            System.err.println("server stop");
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }
}
