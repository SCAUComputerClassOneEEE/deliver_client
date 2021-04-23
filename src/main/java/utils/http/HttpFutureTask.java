package utils.http;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.*;

public class HttpFutureTask extends FutureTask<HttpResponse> {
    private final HttpRequestCallable callable;
    private HttpResponse httpResponse;

    public HttpFutureTask(HttpRequestCallable callable) {
        super(callable);
        this.callable = callable;
    }

    public boolean getStatusOK() {
        return getResponse(0).getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    public HttpResponse getResponse(long mills) {
        if (mills < 0) mills = 0;
        try {
            if (httpResponse == null) {
                httpResponse = mills == 0 ? get() : get(mills, TimeUnit.MILLISECONDS);
                if (httpResponse == null)
                    return null;
                if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                    return null;
                }

                return httpResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterator<?> getContentJSON() {
        return getContentJSON(0);
    }

    public long getContentLong() {
        long longLong = 0L;
        try {
            InputStream content = getContentInputStream(0);
            if (content == null) return 0;
            byte[] longBytes = new byte[9];
            int read = content.read(longBytes);
            if (read > 9) throw new IOException("long read: " + read);
            int i = 0;
            //System.out.println(Arrays.toString(longBytes));
            byte[] _longBytes = new byte[9];
            while(i < read) {
                _longBytes[8 - i ++] = longBytes[read - i];
            }
            //System.out.println(Arrays.toString(_longBytes));

            for (byte b : _longBytes) {
                longLong *= 10;
                longLong += b == 0 ? 0 : b - '0';
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return longLong;
    }

    public static void main(String[] args) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/test")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("powers", 3)
                .build();
        HttpFutureTask httpFutureTask = HttpClientThreadPool.getPoolInstance().submitRequestTask(build);
        System.out.println(httpFutureTask.getContentLong());
    }

    private InputStream getContentInputStream(long mills) throws IOException {
        if (mills < 0) mills = 0;
        final HttpResponse httpResponse;
        if ((httpResponse = getResponse(mills)) == null) {
            return null;
        }
        return httpResponse.getEntity().getContent();
    }

    public Iterator<?> getContentJSON(long mills) {
        if (mills < 0) mills = 0;
        try {
            int length = 0;
            int r;
            final byte[] readBuffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            InputStream content = getContentInputStream(mills);
            if (content == null) {
                //System.err.println("getContentInputStream(mills) = null");
                return null;
            }

            while ((r = content.read()) != -1) {
                if (length == 1024) {
                    String s = new String(readBuffer, 0, length);
                    stringBuilder.append(s);
                    length = 0;
                }
                readBuffer[length ++] = (byte)r;
            }
            String s = new String(readBuffer, 0, length);
            stringBuilder.append(s);
            //System.out.println(stringBuilder);
            return JSONArray.parseArray(stringBuilder.toString()).iterator();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                callable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
