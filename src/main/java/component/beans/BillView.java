package component.beans;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class BillView {

    private Long orderId;
    private Timestamp orderCreateTime;
    private String receiver;
    private String orderStatus;
    private Double charge;
    private Boolean paid;

    public BillView(JSONObject parse) {
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

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
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
