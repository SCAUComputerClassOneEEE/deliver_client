package controller.userController;

import action.AfterLoginAction;
import com.alibaba.fastjson.JSONObject;
import component.beans.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.http.HttpException;
import utils.alert.AlertStage;
import utils.http.AllHttpComUtils;
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
        if (AlertStage.checkNotNullInput("请输入账号！", userPhone))
            return;
        String userPassword=user_text_password.getText();
        if (AlertStage.checkNotNullInput("请输入密码！", userPassword))
            return;
        /*customer = new Customer();
        customer.setCustomerId(Long.parseLong(userPhone));
        customer.setCustomerPassword(""+userPassword);
        AfterLoginAction.PackageShow();*/
        try {
            customer = AllHttpComUtils.login(userPhone, userPassword, true);
        } catch (Exception e) {
            // e.printStackTrace();
            AlertStage.createAlertStage("服务器出错").show();
            return;
        }
        if (customer != null)
            AfterLoginAction.PackageShow();
        else {
            user_text_password.setText("");
            AlertStage.createAlertStage("账号或密码错误").show();
        }
    }

    public long getCustomerId() {
        return customer.getCustomerId();
    }

    public static Customer getCustomer() { return customer; }

    public static void setCustomer(Customer newCustomer) { customer = newCustomer; }

    @FXML
    private void userClear(){
        user_text_phone.clear();
        user_text_password.clear();
    }
}
