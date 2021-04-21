package component.beans;

import com.alibaba.fastjson.JSONObject;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class Customer {
    private Long customerId;
    private String customerPassword;
    private String customerName;
    private String city;
    private String street;
    private String detailAddress;
    private String account;
    private String avatar;

    public Customer(JSONObject parse) {
        customerId = parse.getLong("");
        customerPassword = parse.getString("");
        customerName = parse.getString("");
        city = parse.getString("");
        street = parse.getString("");
        detailAddress = parse.getString("");
        account = parse.getString("");
        avatar = parse.getString("");
    }

    public Image getCustomerAvatarImg() {
        return new Image(
                new ByteArrayInputStream(
                        Base64.getDecoder().decode(
                                avatar
                        )
                )
        );
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
