package controller.adminController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.ChangeService;

import java.io.IOException;


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
    private void adminLogin() throws IOException {
        String adminAccount=ad_text_account.getText();
        String adminPassword=ad_text_password.getText();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/admin/MainView.fxml"));
        Parent root = loader.load();
        ChangeService.mainViewController = loader.getController();
        Scene scene = new Scene(root);
        ChangeService.adminStage.setTitle("管理端");
        ChangeService.adminStage.setScene(scene);
        System.out.println("进入管理端了");
        /**
         * adminHttp
         * 这里要做登录
         */
    }

    @FXML
    private  void adminClear(){
        ad_text_account.clear();
        ad_text_password.clear();
    }

}
