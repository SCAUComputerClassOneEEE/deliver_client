package utils;

import java.util.concurrent.*;

public class HttpClientThreadPool extends ThreadPoolExecutor {
    private static volatile HttpClientThreadPool pool;
    private static final int core = Runtime.getRuntime().availableProcessors();

    public static HttpClientThreadPool getPoolInstance() {
        if (pool == null) {
            synchronized (HttpClientThreadPool.class) {
                if (pool == null) {
                    return pool = new HttpClientThreadPool(
                            core / 2 + 1,
                            core + 1,
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
}
