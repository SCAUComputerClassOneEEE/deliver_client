package utils.http;

import com.alibaba.fastjson.JSONObject;
import component.beans.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    public static List<SimpleOrderInfoBar> getSimpleOrderInfoBarPage(long customerId, int offset, int limit){
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/list")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("customer_id", customerId)
                .addRequestContent("offset", offset)
                .addRequestContent("length", limit)
                .build();
        HttpFutureTask futureTask = pool.submitRequestTask(build);
        List<SimpleOrderInfoBar> tList = getTList(SimpleOrderInfoBar.class, futureTask);
        assert tList != null;
        tList.forEach((s)->System.out.println(s.toString()));
        return tList;
    }

    public static BillOfLastMonth getBillOfLastMonth(long customerId) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/consumption")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("customer_id", customerId)
                .build();
        HttpFutureTask futureTask = pool.submitRequestTask(build);
        return getT(BillOfLastMonth.class, futureTask);
    }

    public static List<BillView> getAllBills(long customerId) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/bills")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("customer_id", customerId)
                .build();
        HttpFutureTask futureTask = pool.submitRequestTask(build);
        return getTList(BillView.class, futureTask);
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
        ArrayList<T> ts = new ArrayList<>();

        try {
            Constructor<T> t = tClass.getConstructor(JSONObject.class);
            Iterator<?> contentJSON = futureTask.getContentJSON();
            if (contentJSON == null) {
                System.err.println("contentJSON == null");
                return null;
            }
            if (!contentJSON.hasNext()) {
                System.err.println("!contentJSON.hasNext()");
                return null;
            }

            while (contentJSON.hasNext()) {
                JSONObject next = (JSONObject)contentJSON.next();
                T t1 = t.newInstance(next);
                ts.add(t1);
                contentJSON.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts;
    }

    private static <T> T getT(Class<T> tClass, HttpFutureTask futureTask) {
        T t = null;

        Iterator<?> contentJSON = futureTask.getContentJSON();
        if (contentJSON == null) return null;
        if (!contentJSON.hasNext()) return null;

        try {
            JSONObject next = (JSONObject)contentJSON.next();
            Constructor<T> tc = tClass.getConstructor(JSONObject.class);
            t = tc.newInstance(next);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
