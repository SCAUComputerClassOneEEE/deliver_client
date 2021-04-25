package component.beans;

import com.alibaba.fastjson.JSONObject;
import utils.constructor.ConstructorUtil;

import java.lang.reflect.Field;

/**
 * @Author: Sky
 * @Date: 2021/4/24 16:31
 */
public class BillOfLastMonth {
    private Long customerId;
    private Integer sendPacksCount;
    private Double moneyNumber;
    private Double lastMonthArrears;

    public BillOfLastMonth(JSONObject parse) {
        ConstructorUtil.newInstance(this, parse);
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Integer getSendPacksCount() {
        return sendPacksCount;
    }

    public void setSendPacksCount(Integer sendPacksCount) {
        this.sendPacksCount = sendPacksCount;
    }

    public Double getMoneyNumber() {
        return moneyNumber;
    }

    public void setMoneyNumber(Double moneyNumber) {
        this.moneyNumber = moneyNumber;
    }

    public Double getLastMonthArrears() {
        return lastMonthArrears;
    }

    public void setLastMonthArrears(Double lastMonthArrears) {
        this.lastMonthArrears = lastMonthArrears;
    }
}
