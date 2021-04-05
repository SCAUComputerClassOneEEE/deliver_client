package utils;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.concurrent.*;

public class HttpFutureTask extends FutureTask<HttpResponse> {
    private HttpRequestCallable callable;

    public HttpFutureTask(HttpRequestCallable callable) {
        super(callable);
        this.callable = callable;
    }

    public JSONArray getContentJSON() {
        return getContentJSON(0);
    }

    public JSONArray getContentJSON(long mills) {
        if (mills < 0) mills = 0;
        try {
            HttpResponse httpResponse = mills == 0 ? get() : get(mills, TimeUnit.MILLISECONDS);
            if (httpResponse == null) return null;

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
