package component.beans;

import com.alibaba.fastjson.JSONObject;
import javafx.scene.image.Image;

import java.io.*;
import java.lang.reflect.Field;
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

    public Customer() {

    }

    public Customer(JSONObject parse) {
        System.out.println(parse.toString());
        try {
            Field[] declaredFields = this.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.set(this,
                        parse.getObject(
                                field.getName(),
                                Class.forName(field.getGenericType().getTypeName())
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(this);
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

    public void setAvatar(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileBytes = new byte[(int)file.length()];
        fileInputStream.read(fileBytes);
        String avatar = Base64.getEncoder().encodeToString(fileBytes);
        setAvatar(avatar);
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
