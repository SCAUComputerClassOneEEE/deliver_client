package utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

public class HttpPostCallable implements Callable<HttpResponse> {
    private final HttpPost httpPost;
    private final HttpClient httpClient;

    HttpPostCallable(HttpClient httpClient, String url, String bodyString) throws UnsupportedEncodingException {
        httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(bodyString);
        httpPost.setEntity(entity);
        this.httpClient = httpClient;
    }
    public HttpResponse call() throws Exception {
        return httpClient.execute(httpPost);
    }
}
