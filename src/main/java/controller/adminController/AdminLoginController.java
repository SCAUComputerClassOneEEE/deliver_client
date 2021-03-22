package controller.adminController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class AdminLoginController {
    @FXML
    private TextField ad_text_account;

    @FXML
    private PasswordField ad_text_password;

    @FXML
    private Button ad_btn_login;

    @FXML
    private  Button ad_btn_clear;

    @FXML
    private void adminLogin(){
        String adminAccount=ad_text_account.getText();
        String adminPaddword=ad_text_password.getText();

    }

    @FXML
    private  void adminClear(){
        ad_text_account.clear();
        ad_text_password.clear();
    }

}
