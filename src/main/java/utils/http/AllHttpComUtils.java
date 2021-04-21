package utils.http;

import com.alibaba.fastjson.JSONObject;
import component.SimpleOrderMessagePane;
import component.beans.Customer;
import component.beans.PackOrderBillInsertInfo;
import component.beans.SimpleOrderInfoBar;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllHttpComUtils {
    private static final HttpClientThreadPool pool = HttpClientThreadPool.getPoolInstance();

    public static final String qr_pay_url =
            HttpRequestCallable.HttpRequestCallableBuilder.URL + "/payBill/QRPay";

/*    public static boolean payQR(List<Long> billIds) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/payBill/QRPay")
                .onMethod(HttpClientThreadPool.HttpMethod.POST)
                .addRequestContent("billIds", billIds)
                .build();
        HttpFutureTask httpFutureTask = pool.submitRequestTask(build);
        return httpFutureTask.getStatusOK();
    }*/

    public static long createP_O_BInsertInfo(PackOrderBillInsertInfo packOrderBillInsertInfo) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/user/login")
                .onMethod(HttpClientThreadPool.HttpMethod.POST)
                .addRequestContent("packOrderBillInsertInfo", packOrderBillInsertInfo)
                .build();
        HttpFutureTask httpFutureTask = pool.submitRequestTask(build);
        Iterator<?> contentJSON = httpFutureTask.getContentJSON();
        if (contentJSON == null) return 0;
        return JSONObject.parseObject(contentJSON.next().toString()).getLong("billId");
    }

    public static Customer login(String name, String passwd, boolean cusOrAd) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/user/login")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("phone_number", name)
                .addRequestContent("password", passwd)
                .addRequestContent("type", cusOrAd ? 1 : 0)
                .build();
        HttpFutureTask httpFutureTask = pool.submitRequestTask(build);
        Iterator<?> contentJSON = httpFutureTask.getContentJSON();
        if (contentJSON == null) return null;
        return new Customer(JSONObject.parseObject(contentJSON.next().toString()));
    }

    public static List<SimpleOrderInfoBar> getSimpleOrderInfoBarPage(long customerId, int offset, int limit) {
        List<SimpleOrderInfoBar> res = new ArrayList<>();

        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/list")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("customer_id", customerId)
                .addRequestContent("offset", offset)
                .addRequestContent("length", limit)
                .build();
        HttpFutureTask futureTask = pool.submitRequestTask(build);
        Iterator<?> content = null;
        while (content == null) {
            content = futureTask.getContentJSON();
            while (content.hasNext()) {
                JSONObject parse = JSONObject.parseObject(content.next().toString());
                res.add(new SimpleOrderInfoBar(parse));
                content.remove();
            }
        }
        return res;
    }
}
