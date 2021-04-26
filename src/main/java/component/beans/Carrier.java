package component.beans;

import com.alibaba.fastjson.JSONObject;
import utils.constructor.ConstructorUtil;

public class Carrier {
    private Long carrierId;
    private String  carrierType;
    private Long deliveryTimes;
    private String connectPhoneNumber;
    private String detailMessage;

    public Carrier() {

    }

    public Carrier(Long carrierId, String carrierType, Long deliveryTimes, String connectPhoneNumber, String detailMessage) {
        this.carrierId = carrierId;
        this.carrierType = carrierType;
        this.deliveryTimes = deliveryTimes;
        this.connectPhoneNumber = connectPhoneNumber;
        this.detailMessage = detailMessage;
    }

    public Carrier(JSONObject parse) {
        ConstructorUtil.newInstance(this, parse);
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public Long getDeliveryTimes() {
        return deliveryTimes;
    }

    public void setDeliveryTimes(Long deliveryTimes) {
        this.deliveryTimes = deliveryTimes;
    }

    public String getConnectPhoneNumber() {
        return connectPhoneNumber;
    }

    public void setConnectPhoneNumber(String connectPhoneNumber) {
        this.connectPhoneNumber = connectPhoneNumber;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
