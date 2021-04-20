package controller.userController;

import action.AfterLoginAction;
import com.alibaba.fastjson.JSONObject;
import component.beans.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.http.HttpClientThreadPool;
import utils.http.HttpFutureTask;
import utils.http.HttpRequestCallable;

import java.util.Iterator;

public class UserLoginController {

    private static Customer customer;

    @FXML
    private TextField user_text_phone;

    @FXML
    private PasswordField user_text_password;

    @FXML
    private Button user_btn_login;

    @FXML
    private  Button getUser_btn_clear;

    @FXML
    private void userLogin() throws Exception {
        String userPhone=user_text_phone.getText();
        String userPassword=user_text_password.getText();
        AfterLoginAction.PackageShow();
//        customer = wLoginBool(userPhone, userPassword);
//        if (customer != null)
//            AfterLoginAction.PackageShow();
//        else {
//            user_text_password.setText("");
//
//        }
    }

    public long getCustomerId() {
        return customer.getCustomerId();
    }

    public static Customer getCustomer() { return customer; }

    public static void setCustomer(Customer newCustomer) { customer = newCustomer; }

    private static Customer wLoginBool(String phone, String passwd) {
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/user/login")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("phone_number", phone)
                .addRequestContent("password", passwd)
                .addRequestContent("type", 1)
                .build();
        HttpFutureTask httpFutureTask = HttpClientThreadPool.getPoolInstance().submitRequestTask(build);
        Iterator<?> contentJSON = httpFutureTask.getContentJSON();
        if (contentJSON == null) return null;
        return new Customer(JSONObject.parseObject(contentJSON.next().toString()));
    }

    @FXML
    private void userClear(){
        user_text_phone.clear();
        user_text_password.clear();
    }
}
