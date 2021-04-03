package utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class HttpClientThreadPool extends ThreadPoolExecutor {
    private static final String URL = "http://localhost:8080";
    private static final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private static volatile HttpClientThreadPool pool;

    public static HttpClientThreadPool getPoolInstance() {
        if (pool == null) {
            synchronized (HttpClientThreadPool.class) {
                if (pool == null) {
                    return pool = new HttpClientThreadPool(
                            3,
                            10,
                            2,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>());
                }
            }
        }
        return pool;
    }

    public enum HttpMethod{
        GET, POST, DELETE, PUT
    }

    private HttpClientThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public HttpFutureTask submitRequestTask(String thisUrl, HttpMethod method, Map<String, Object> obMap) {
        HttpFutureTask objectFutureTask = null;
        try {
            if (method.equals(HttpMethod.GET)) {
                objectFutureTask = new HttpFutureTask(new HttpGetCallable(httpClient, URL + thisUrl, obMap));
            } else if (method.equals(HttpMethod.POST)) {
                objectFutureTask = new HttpFutureTask(new HttpPostCallable(httpClient, URL + thisUrl, obMap));
            } else if (method.equals(HttpMethod.DELETE)) {

            }
        } catch (Exception e) {
            return null;
        }

        if (objectFutureTask != null) {
            execute(objectFutureTask);
        }
        return objectFutureTask;
    }

    public Thread shutDown() {
        Thread thread = new Thread(() -> {
            try {
                shutdownNow();
                boolean shut;
                do {
                    shut = !super.awaitTermination(2, TimeUnit.SECONDS);
                } while (shut);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return thread;
    }
}
