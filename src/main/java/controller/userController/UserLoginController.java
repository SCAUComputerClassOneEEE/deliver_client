package controller.userController;

import action.AfterLoginAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.http.HttpClientThreadPool;
import utils.http.HttpFutureTask;
import utils.http.HttpRequestCallable;

public class UserLoginController {
    @FXML
    private TextField user_text_phone;

    public long getCustomerId() {
        return Long.valueOf(user_text_phone.getText());
    }

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

        if (wLoginBool(userPhone, userPassword))
            AfterLoginAction.PackageShow();
        else {
            user_text_password.setText("");

        }
    }

    private static boolean wLoginBool(String phone, String passwd) {
        return /*httpFutureTask.getHttpStatus()*/ true;
       /* HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("phone", phone)
                .addRequestContent("passwd", passwd).build();
        HttpFutureTask httpFutureTask = HttpClientThreadPool.getPoolInstance().submitRequestTask(build);
        return httpFutureTask.getHttpStatus();*/
    }

    @FXML
    private void userClear(){
        user_text_phone.clear();
        user_text_password.clear();
    }
}
