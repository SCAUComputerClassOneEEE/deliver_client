package utils.http;

import com.alibaba.fastjson.JSONObject;
import component.SimpleOrderMessagePane;
import component.beans.*;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllHttpComUtils {
    private static final HttpClientThreadPool pool = HttpClientThreadPool.getPoolInstance();

    public static final String qr_pay_url =
            HttpRequestCallable.HttpRequestCallableBuilder.URL + "/payBill/QRPay";

    public static long createP_O_BInsertInfo(PackOrderBillInsertInfo packOrderBillInsertInfo) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/insert/order")
                .onMethod(HttpClientThreadPool.HttpMethod.POST)
                .addRequestContent("packOrderBillInsertInfo", packOrderBillInsertInfo)
                .build();
        HttpFutureTask httpFutureTask = pool.submitRequestTask(build);

        return httpFutureTask.getContentLong();
    }

    public static void updateCustomer(Customer customer) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/user/customer")
                .onMethod(HttpClientThreadPool.HttpMethod.POST)
                .addRequestContent("customer", customer)
                .build();
        pool.submitRequestTask(build)/*.getStatusOK()*/;
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
        return getT(Customer.class, httpFutureTask);
    }

    /**
     *
     * @param customerId phone number
     * @param offset page offset
     * @param limit page size
     * @return List<SimpleOrderInfoBar>
     */
    public static List<SimpleOrderInfoBar> getSimpleOrderInfoBarPage(long customerId, int offset, int limit) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/list")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("customer_id", customerId)
                .addRequestContent("offset", offset)
                .addRequestContent("length", limit)
                .build();
        HttpFutureTask futureTask = pool.submitRequestTask(build);

        return getTList(SimpleOrderInfoBar.class, futureTask);
    }

    public static List<Bill> getAllBills(long customerId) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/bills")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("customer_id", customerId)
                .build();
        HttpFutureTask futureTask = pool.submitRequestTask(build);
        return getTList(Bill.class, futureTask);
    }

    private static List<Transport> getTransportsOfOrder(long orderId) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/transport")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("order_id", orderId)
                .build();

        HttpFutureTask futureTask = pool.submitRequestTask(build);
        return getTList(Transport.class, futureTask);
    }

    private static <T> List<T> getTList(Class<T> tClass, HttpFutureTask futureTask) {
        Iterator<?> contentJSON = futureTask.getContentJSON();
        if (contentJSON == null) return null;
        if (!contentJSON.hasNext()) return null;
        if (!(contentJSON.next().getClass().equals(tClass))) return null;
        ArrayList<T> ts = new ArrayList<>();
        while (contentJSON.hasNext()) {
            Object next = contentJSON.next();
            T t = (T)next;
            ts.add(t);
            contentJSON.remove();
        }
        return ts;
    }

    private static <T> T getT(Class<T> tClass, HttpFutureTask futureTask) {
        Iterator<?> contentJSON = futureTask.getContentJSON();
        if (contentJSON == null) return null;
        if (!contentJSON.hasNext()) return null;
        if (!(contentJSON.next().getClass().equals(tClass))) return null;
        return (T)contentJSON.next();
    }
}
