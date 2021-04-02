package utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Callable;

public class HttpGetCallable implements Callable<HttpResponse> {
    private final HttpGet httpGet;
    private final HttpClient httpClient;
    HttpGetCallable(HttpClient httpClient, String url, List<NameValuePair> opList) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(url);
        builder.addParameters(opList);
        httpGet = new HttpGet(builder.build());
        this.httpClient = httpClient;
    }
    public HttpResponse call() throws Exception {
        System.out.println("http getting");
        return httpClient.execute(httpGet);
    }
}
