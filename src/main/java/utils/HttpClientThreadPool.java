package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import component.beans.SimpleOrderInfoBar;

import java.util.*;
import java.util.concurrent.*;

public class HttpClientThreadPool extends ThreadPoolExecutor {
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

    public HttpFutureTask submitRequestTask(HttpRequestCallable callable) {
        HttpFutureTask objectFutureTask = null;
        if (callable != null) {
            objectFutureTask = new HttpFutureTask(callable);
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
            }
        });
        thread.start();
        return thread;
    }

    public static void main(String[] args) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/list")
                .onMethod(HttpMethod.GET)
                .addRequestContent("customer_id", 18899715136L)
                .addRequestContent("offset", 0)
                .addRequestContent("length", 5)
                .build();
        HttpClientThreadPool poolInstance = HttpClientThreadPool.getPoolInstance();

        // 生成异步任务
        HttpFutureTask futureTask = poolInstance.submitRequestTask(build);
        //
        int i = 5;
        while (i -- > 0) {

            JSONArray contentJSON = futureTask.getContentJSON(50);
            if (contentJSON != null) {
                Iterator<Object> iterator = contentJSON.iterator();
                while (iterator.hasNext()) {
                    JSONObject parse = JSONObject.parseObject(iterator.next().toString());
                    SimpleOrderInfoBar simpleOrderInfoBar = new SimpleOrderInfoBar();
                    simpleOrderInfoBar.setOrderId(parse.getInteger("orderId"));
                    simpleOrderInfoBar.setOrderCreateTime(parse.getTimestamp("orderCreateTime"));
                    simpleOrderInfoBar.setOrderStatus(parse.getString("orderStatus"));
                    System.out.println(simpleOrderInfoBar.toString());
                    iterator.remove();
                }
                break;
            }

            //
            System.out.println("等待：" + i);
        }
    }
}
