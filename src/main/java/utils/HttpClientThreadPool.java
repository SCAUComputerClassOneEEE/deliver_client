package utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class HttpClientThreadPool extends ThreadPoolExecutor {
    private static final String URL = "http://localhost:8080";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public enum HttpMethod{
        GET, POST, DELETE, PUT
    }

    private static class HttpGetCallable implements Callable<HttpResponse>{
        private final HttpGet httpGet;
        HttpGetCallable(String url, List<NameValuePair> opList) throws URISyntaxException {
            URIBuilder builder = new URIBuilder(url);
            builder.addParameters(opList);
            httpGet = new HttpGet(builder.build());
        }
        public HttpResponse call() throws Exception {
            return httpClient.execute(httpGet);
        }
    }

    private static class HttpPostCallable implements Callable<HttpResponse>{
        private final HttpPost httpPost;
        HttpPostCallable(String url, String bodyString) throws UnsupportedEncodingException {
            httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(bodyString);
            httpPost.setEntity(entity);
        }
        public HttpResponse call() throws Exception {
            return httpClient.execute(httpPost);
        }
    }

    public static class HttpFutureTask extends FutureTask<HttpResponse> {

        public HttpFutureTask(Callable<HttpResponse> callable) {
            super(callable);
        }

        public JSONObject getOb() throws ExecutionException, InterruptedException {
            HttpResponse httpResponse = get();
            HttpEntity entity = httpResponse.getEntity();
            return JSONObject.parseObject(entity.toString());
        }
    }

    public HttpClientThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public HttpFutureTask submitRequestTask(Map<String, Object> obMap, HttpMethod method, String thisUrl) throws URISyntaxException, UnsupportedEncodingException {
        HttpFutureTask objectFutureTask = null;
        if (method.equals(HttpMethod.GET)) {
            final List<NameValuePair> opList = new ArrayList<>();
            obMap.forEach((String s, Object o)-> opList.add(new BasicNameValuePair(s, o.toString())));
            objectFutureTask = new HttpFutureTask(new HttpGetCallable(URL + thisUrl, opList));
        } else {
            switch (method) {
                case POST:
                    final StringBuilder stringBuilder = new StringBuilder();
                    obMap.forEach((String s, Object o)->stringBuilder.append(o.toString()));
                    objectFutureTask = new HttpFutureTask(new HttpPostCallable(URL + thisUrl, stringBuilder.toString()));
                    break;
                case PUT:
                case DELETE:
            }
        }

        execute(objectFutureTask);
        return objectFutureTask;
    }

}
