package component.beans;

import com.alibaba.fastjson.JSONObject;
import utils.constructor.ConstructorUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class Transport {
    private Long transportId;
    private Long orderId;
    private Long carrierId;
    private String carrierType;
    private Long transportTimesOfCarrier;
    private Timestamp inputTime;
    private Timestamp outputTime;
    private Long nextCarrierId;
    private String nextCarrierType;
    private String status;
    private String transDetailMessage;

    public Transport(JSONObject parse) {
        ConstructorUtil.newInstance(this, parse);
    }

    @Override
    public String toString() {
        return "Transport{" +
                "transportId=" + transportId +
                ", orderId=" + orderId +
                ", carrierId=" + carrierId +
                ", carrierType='" + carrierType + '\'' +
                ", transportTimesOfCarrier=" + transportTimesOfCarrier +
                ", inputTime=" + inputTime +
                ", outputTime=" + outputTime +
                ", nextCarrierId=" + nextCarrierId +
                ", nextCarrierType='" + nextCarrierType + '\'' +
                ", status='" + status + '\'' +
                ", detailMessage='" + transDetailMessage + '\'' +
                '}';
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

    public String getTransDetailMessage() {
        return transDetailMessage;
    }

    public void setTransDetailMessage(String transDetailMessage) {
        this.transDetailMessage = transDetailMessage;
    }
}