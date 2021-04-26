package component.beans;

import com.alibaba.fastjson.JSONObject;
import utils.constructor.ConstructorUtil;

public class ConsumptionOfLastYear {
    private Long customerId;
    private String customerName;
    private Double consumption;

    public ConsumptionOfLastYear(JSONObject parse) {
        ConstructorUtil.newInstance(this, parse);
    }

    public Long getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "ConsumptionOfLastYear{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", consumption=" + consumption +
                '}';
    }

    public void setCustomerId(Long orderId) {
        this.customerId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }
}
