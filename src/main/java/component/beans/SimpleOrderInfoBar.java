package component.beans;


import java.sql.Timestamp;

public class SimpleOrderInfoBar {
    private int orderId;
    private Timestamp orderCreateTime;
    private String receiveName;
    private String orderStatus;

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
}
