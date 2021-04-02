package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.*;

public class HttpClientThreadPool extends ThreadPoolExecutor {
    private static final String URL = "http://localhost:8080";
    private static final CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    public enum HttpMethod{
        GET, POST, DELETE, PUT
    }

    public static class HttpFutureTask extends FutureTask<HttpResponse> {

        public HttpFutureTask(Callable<HttpResponse> callable) {
            super(callable);
        }

        public JSONArray getContentJSON() throws InterruptedException, ExecutionException, TimeoutException, IOException {
            return getContentJSON(0);
        }

        public JSONArray getContentJSON(long mills) throws ExecutionException, InterruptedException, TimeoutException, IOException {
            if (mills < 0) mills = 0;
            HttpResponse httpResponse = mills == 0 ? get() : get(mills, TimeUnit.MILLISECONDS);

            HttpEntity entity;
            JSONArray jsonArray; int length;
            final byte[] readBuffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            entity = httpResponse.getEntity();
            do {
                length = entity.getContent().read(readBuffer);
                stringBuilder.append(new String(readBuffer));
            } while (length >= 1024);
            jsonArray = JSONArray.parseArray(stringBuilder.toString());
            return jsonArray;
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

            objectFutureTask = new HttpFutureTask(new HttpGetCallable(httpClient, URL + thisUrl, opList));
        } else {
            switch (method) {
                case POST:
                    final StringBuilder stringBuilder = new StringBuilder();
                    obMap.forEach((String s, Object o)->stringBuilder.append(o.toString()));

                    objectFutureTask = new HttpFutureTask(new HttpPostCallable(httpClient, URL + thisUrl, stringBuilder.toString()));
                    break;
                case PUT:
                case DELETE:
            }
        }

        execute(objectFutureTask);
        System.out.println("execute...");
        return objectFutureTask;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        /*HttpClientThreadPool pool = new HttpClientThreadPool(
                2,
                10,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("customer_id", 18899715136L);
        hashMap.put("offset",0);
        hashMap.put("length", 2);

        try {
            HttpFutureTask httpFutureTask = pool.submitRequestTask(
                    hashMap,
                    HttpMethod.GET,
                    "/query/list");
            JSONArray ob = httpFutureTask.getResponse();
            System.out.println("result");

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
