package component.beans;

import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;

public class Transport {
    private long transportId;
    private long orderId;
    private long carrierId;
    private String carrierType;
    private long transportTimesOfCarrier;
    private Timestamp inputTime;
    private Timestamp outputTime;
    private long nextCarrierId;
    private String nextCarrierType;
    private String status;
    //private String detailMessage;

    public Transport(JSONObject parse) {
        setCarrierId(parse.getLong("carrierId"));
        setCarrierType(parse.getString("carrierType"));
        //setDetailMessage(parse.getString("detailMessage"));
        setTransportId(parse.getLong("transportId"));
        setOrderId(parse.getLong("orderId"));
        setTransportTimesOfCarrier(parse.getLong("transportTimesOfCarrier"));
        setInputTime(parse.getTimestamp("inputTime"));
        setOutputTime(parse.getTimestamp("outputTime"));
        setNextCarrierId(parse.getLong("nextCarrierId"));
        setNextCarrierType(parse.getString("nextCarrierType"));
        setStatus(parse.getString("status"));
    }

    public long getTransportId() {
        return transportId;
    }

    public void setTransportId(long transportId) {
        this.transportId = transportId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(long carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public long getTransportTimesOfCarrier() {
        return transportTimesOfCarrier;
    }

    public void setTransportTimesOfCarrier(long transportTimesOfCarrier) {
        this.transportTimesOfCarrier = transportTimesOfCarrier;
    }

    public Timestamp getInputTime() {
        return inputTime;
    }

    public void setInputTime(Timestamp inputTime) {
        this.inputTime = inputTime;
    }

    public Timestamp getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(Timestamp outputTime) {
        this.outputTime = outputTime;
    }

    public long getNextCarrierId() {
        return nextCarrierId;
    }

    public void setNextCarrierId(long nextCarrierId) {
        this.nextCarrierId = nextCarrierId;
    }

    public String getNextCarrierType() {
        return nextCarrierType;
    }

    public void setNextCarrierType(String nextCarrierType) {
        this.nextCarrierType = nextCarrierType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

/*    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }*/
}