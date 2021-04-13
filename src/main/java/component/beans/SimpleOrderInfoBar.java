package component.beans;


import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;

public class SimpleOrderInfoBar {
    private int orderId;
    private Timestamp orderCreateTime;
    private String receiveName;
    private String orderStatus;
    private String sendDetailAddress;
    private String receiveDetailAddress;

    public SimpleOrderInfoBar() {

    }
    public SimpleOrderInfoBar(JSONObject parse) {
        setOrderId(parse.getInteger("orderId"));
        setOrderCreateTime(parse.getTimestamp("orderCreateTime"));
        setOrderStatus(parse.getString("orderStatus"));
        setReceiveName(parse.getString("receiveName"));
        setSendDetailAddress(parse.getString("sendDetailAddress"));
        setReceiveDetailAddress(parse.getString("receiveDetailAddress"));
    }

    public int getOrderId() {
        return orderId;
    }

    public Timestamp getOrderCreateTime() {
        return orderCreateTime;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderCreateTime(Timestamp orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSendDetailAddress() {
        return sendDetailAddress;
    }

    public void setSendDetailAddress(String sendAdd) {
        this.sendDetailAddress = sendAdd;
    }

    public String getReceiveDetailAddress() {
        return receiveDetailAddress;
    }

    public void setReceiveDetailAddress(String receiveAdd) {
        this.receiveDetailAddress = receiveAdd;
    }
}
