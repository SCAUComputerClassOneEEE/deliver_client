package component.beans;

import com.alibaba.fastjson.JSONObject;

public class Bill {
    private Long orderId;
    private Long customerId;
    private Integer charge;
    private Integer batchNumber;
    private String payType;
    private Boolean isPaid;

    public Bill(JSONObject parse) {
        orderId = parse.getLong("orderId");
        customerId = parse.getLong("customerId");
        charge = parse.getInteger("charge");
        batchNumber = parse.getInteger("batchNumber");
        payType = parse.getString("payType");
        isPaid = parse.getBoolean("isPaid");
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
