package component.beans;

import com.alibaba.fastjson.JSONObject;
import utils.constructor.ConstructorUtil;

public class DamageRecord {
    private Long carrierId;
    private String carrierType;
    private Long damageRecordTime;

    public DamageRecord( ) {

    }

    public DamageRecord(JSONObject parse) {
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

    public Long getDamageRecordTime() {
        return damageRecordTime;
    }

    public void setDamageRecordTime(Long damageRecordTime) {
        this.damageRecordTime = damageRecordTime;
    }
}
