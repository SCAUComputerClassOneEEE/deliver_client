package component.beans;

import java.sql.Timestamp;

public class PackOrderBillInsertInfo {
    // 发件人
    private String sName;
    private String sPhoneNumber;
    private String departure; // 省，市，详细地址
    // 收件人
    private String cName;
    private String cPhoneNumber;
    private String address; // 省，市，详细地址
    private Timestamp commitArriveTime; // 约定到达时间，当前时间+一日或两日
    // package info
    private String packType; // 包裹类型
    private String detailMess; // 备注
    private double packWeight; // 重量
    private boolean isDangerous; // 危险
    private boolean isInter; // 国际
    // bill info
    private String payType; // 立即付、月付。。。
    private int charge; // 计算得出的费用
    private long customerId; // 支付的人的id

    @Override
    public String toString() {
        return "PackOrderBillInsertInfo{" +
                "sName='" + sName + '\'' +
                ", sPhoneNumber='" + sPhoneNumber + '\'' +
                ", departure='" + departure + '\'' +
                ", cName='" + cName + '\'' +
                ", cPhoneNumber='" + cPhoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", commitArriveTime=" + commitArriveTime +
                ", packType='" + packType + '\'' +
                ", detailMess='" + detailMess + '\'' +
                ", packWeight=" + packWeight +
                ", isDangerous=" + isDangerous +
                ", isInter=" + isInter +
                ", payType='" + payType + '\'' +
                ", charge=" + charge +
                ", customerId=" + customerId +
                '}';
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPhoneNumber() {
        return sPhoneNumber;
    }

    public void setsPhoneNumber(String sPhoneNumber) {
        this.sPhoneNumber = sPhoneNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcPhoneNumber() {
        return cPhoneNumber;
    }

    public void setcPhoneNumber(String cPhoneNumber) {
        this.cPhoneNumber = cPhoneNumber;
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
        return isDangerous;
    }

    public void setDangerous(boolean dangerous) {
        isDangerous = dangerous;
    }

    public boolean isInter() {
        return isInter;
    }

    public void setInter(boolean inter) {
        isInter = inter;
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
