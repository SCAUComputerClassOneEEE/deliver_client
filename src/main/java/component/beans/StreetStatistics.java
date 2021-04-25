package component.beans;


import com.alibaba.fastjson.JSONObject;
import utils.constructor.ConstructorUtil;

public class StreetStatistics {
    private String street;
    private Integer number;

    public StreetStatistics(JSONObject parse){
        ConstructorUtil.newInstance(this, parse);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
