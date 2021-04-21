package utils.http;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.InputStream;
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
                if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                    return null;
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

    public Iterator<?> getContentJSON(long mills) {
        if (mills < 0) mills = 0;
        try {
            final HttpResponse httpResponse;
            if ((httpResponse = getResponse(mills)) == null) {
                return null;
            }
            int length = 0;
            int r;
            final byte[] readBuffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            InputStream content = httpResponse.getEntity().getContent();
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
            System.out.println(stringBuilder);
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
