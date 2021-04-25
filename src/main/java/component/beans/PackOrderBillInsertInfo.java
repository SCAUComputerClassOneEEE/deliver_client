package component.beans;

import com.alibaba.fastjson.JSONObject;
import utils.constructor.ConstructorUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class PackOrderBillInsertInfo {
    private String shipperName;
    private String shipperPhoneNumber;
    private String departure; // 省，市，详细地址
    // 收件人
    private String consiggeeName;
    private String consiggeePhoneNumber;
    private String address; // 省，市，详细地址
    private Timestamp commitArriveTime; // 约定到达时间，当前时间+一日或两日
    // package info
    private String packType; // 包裹类型
    private String detailMess; // 备注
    private Double packWeight; // 重量
    private Boolean dangerous; // 危险
    private Boolean inter; // 国际
    // bill info
    private String payType; // 立即付、月付。。。
    private Integer charge; // 计算得出的费用
    private Long customerId; // 支付的人的id

    public PackOrderBillInsertInfo() {

    }

    public PackOrderBillInsertInfo(JSONObject parse) {
        ConstructorUtil.newInstance(this, parse);
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperPhoneNumber() {
        return shipperPhoneNumber;
    }

    public void setShipperPhoneNumber(String shipperPhoneNumber) {
        this.shipperPhoneNumber = shipperPhoneNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getConsiggeeName() {
        return consiggeeName;
    }

    public void setConsiggeeName(String consiggeeName) {
        this.consiggeeName = consiggeeName;
    }

    public String getConsiggeePhoneNumber() {
        return consiggeePhoneNumber;
    }

    public void setConsiggeePhoneNumber(String consiggeePhoneNumber) {
        this.consiggeePhoneNumber = consiggeePhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCommitArriveTime() {
        return commitArriveTime;
    }

    public void setCommitArriveTime(Timestamp commitArriveTime) {
        this.commitArriveTime = commitArriveTime;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public String getDetailMess() {
        return detailMess;
    }

    public void setDetailMess(String detailMess) {
        this.detailMess = detailMess;
    }

    public Double getPackWeight() {
        return packWeight;
    }

    public void setPackWeight(Double packWeight) {
        this.packWeight = packWeight;
    }

    public Boolean getDangerous() {
        return dangerous;
    }

    public void setDangerous(Boolean dangerous) {
        this.dangerous = dangerous;
    }

    public Boolean getInter() {
        return inter;
    }

    public void setInter(Boolean inter) {
        this.inter = inter;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "PackOrderBillInsertInfo{" +
                "shipperName='" + shipperName + '\'' +
                ", shipperPhoneNumber='" + shipperPhoneNumber + '\'' +
                ", departure='" + departure + '\'' +
                ", consiggeeName='" + consiggeeName + '\'' +
                ", consiggeePhoneNumber='" + consiggeePhoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", commitArriveTime=" + commitArriveTime +
                ", packType='" + packType + '\'' +
                ", detailMess='" + detailMess + '\'' +
                ", packWeight=" + packWeight +
                ", dangerous=" + dangerous +
                ", inter=" + inter +
                ", payType='" + payType + '\'' +
                ", charge=" + charge +
                ", customerId=" + customerId +
                '}';
    }
}
