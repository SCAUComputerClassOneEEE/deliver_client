package component.beans;


import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class SimpleOrderInfoBar {
    private Integer orderId;
    private Timestamp orderCreateTime;
    private String receiveName;
    private String orderStatus;
    private String sendDetailAddress;
    private String receiveDetailAddress;

    public SimpleOrderInfoBar() {

    }
    public SimpleOrderInfoBar(JSONObject parse) {
        System.out.println(parse.toString());
        try {
            Field[] declaredFields = this.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.set(this,
                        parse.getObject(
                                field.getName(),
                                Class.forName(field.getGenericType().getTypeName())
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(this);
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
