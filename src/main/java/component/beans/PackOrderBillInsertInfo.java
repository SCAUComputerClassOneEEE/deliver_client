package component.beans;

import com.alibaba.fastjson.JSONObject;
import utils.constructor.ConstructorUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class PackOrderBillInsertInfo {
    // 发件人
    private String sname;
    private String sphoneNumber;
    private String departure; // 省，市，详细地址
    // 收件人
    private String cname;
    private String cphoneNumber;
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

    @Override
    public String toString() {
        return "PackOrderBillInsertInfo{" +
                "sName='" + sname + '\'' +
                ", sPhoneNumber='" + sphoneNumber + '\'' +
                ", departure='" + departure + '\'' +
                ", cName='" + cname + '\'' +
                ", cPhoneNumber='" + cphoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", commitArriveTime=" + commitArriveTime +
                ", packType='" + packType + '\'' +
                ", detailMess='" + detailMess + '\'' +
                ", packWeight=" + packWeight +
                ", isDangerous=" + dangerous +
                ", isInter=" + inter +
                ", payType='" + payType + '\'' +
                ", charge=" + charge +
                ", customerId=" + customerId +
                '}';
    }

    public String getsName() {
        return sname;
    }

    public void setsName(String sName) {
        this.sname = sName;
    }

    public String getsPhoneNumber() {
        return sphoneNumber;
    }

    public void setsPhoneNumber(String sPhoneNumber) {
        this.sphoneNumber = sPhoneNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getcName() {
        return cname;
    }

    public void setcName(String cName) {
        this.cname = cName;
    }

    public String getcPhoneNumber() {
        return cphoneNumber;
    }

    public void setcPhoneNumber(String cPhoneNumber) {
        this.cphoneNumber = cPhoneNumber;
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

    public double getPackWeight() {
        return packWeight;
    }

    public void setPackWeight(double packWeight) {
        this.packWeight = packWeight;
    }

    public boolean isDangerous() {
        return dangerous;
    }

    public void setDangerous(boolean dangerous) {
        dangerous = dangerous;
    }

    public boolean isInter() {
        return inter;
    }

    public void setInter(boolean inter) {
        inter = inter;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
