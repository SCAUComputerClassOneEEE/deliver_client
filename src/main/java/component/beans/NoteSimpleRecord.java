package component.beans;

import com.alibaba.fastjson.JSONObject;
import utils.constructor.ConstructorUtil;

import java.sql.Timestamp;

/**
 * @Author: Sky
 * @Date: 2021/4/20 15:24
 */
public class NoteSimpleRecord {
    private Long orderId;
    private Integer carrierId;
    private String carrierType;
    private Timestamp time;
    private Integer transportTimesOfCarrier;

    public NoteSimpleRecord(JSONObject parse) {
        ConstructorUtil.newInstance(this, parse);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Integer carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getTransportTimesOfCarrier() {
        return transportTimesOfCarrier;
    }

    public void setTransportTimesOfCarrier(Integer transportTimesOfCarrier) {
        this.transportTimesOfCarrier = transportTimesOfCarrier;
    }
}



