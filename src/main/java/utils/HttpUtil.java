package utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtil {

    private static final String url = "http://localhost:8080";

    private static final CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    void doGet() {

    }

    void doPut() {

    }
}
