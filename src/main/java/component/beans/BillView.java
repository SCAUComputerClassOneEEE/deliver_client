package component.beans;

import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;

public class BillView {

    private long orderId;
    private Timestamp orderCreateTime;
    private String receiver;
    private String orderStatus;
    private double charge;
    private boolean isPaid;

    public BillView(JSONObject parse) {
        System.out.println(parse.toString());
        orderId = parse.getLong("orderId");
        orderCreateTime = parse.getTimestamp("orderCreateTime");
        receiver = parse.getString("receiver");
        orderStatus = parse.getString("orderStatus");
        charge = parse.getDouble("charge");
        isPaid = parse.getBoolean("paid");
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Timestamp orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
}
