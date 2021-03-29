package controller.userController;

import action.afterLoginAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;


public class UserLoginController {
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
        afterLoginAction.PackageShow();

    }

    @FXML
    private void userClear(){
        user_text_phone.clear();
        user_text_password.clear();
    }

}
