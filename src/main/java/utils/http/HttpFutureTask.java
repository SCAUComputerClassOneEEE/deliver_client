package utils.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
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

    public boolean tryGetServerStop() {
        syncGetResponse(0);
        return httpResponse == null;
    }

    private void syncGetResponse(long mills) {
        if (mills < 0) mills = 0;
        try {
            if (httpResponse == null) {
                httpResponse = mills == 0 ? get() : get(mills, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStatusOK() {
        syncGetResponse(0);
        if (httpResponse == null) return false;
        return httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    private InputStream getContentInputStream(long mills) throws IOException {
        if (mills < 0) mills = 0;
        syncGetResponse(mills);
        if (httpResponse == null || !isStatusOK()) {
            return null;
        }
        return httpResponse.getEntity().getContent();
    }

    private String getContentString(long mills) {
        if (mills < 0) mills = 0;
        try {
            int length = 0;
            int r;
            final byte[] readBuffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            InputStream content = getContentInputStream(mills);
            if (content == null) {
                System.err.println("getContentInputStream(mills) = null");
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
            System.out.println(stringBuilder);
            return stringBuilder.toString();
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
        } finally {
            try {
                callable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return longLong;
    }

    public Object getContent() { return getContent(0); }

    public Object getContent(long mills) {
        return JSONObject.parse(getContentString(mills));
    }

    public Iterator<?> getContentJSONArray() {
        return getContentJSONArray(0);
    }

    public Iterator<?> getContentJSONArray(long mills) { return JSONArray.parseArray(getContentString(mills)).iterator(); }
}
