package controller.adminController;

import action.AfterLoginAction;
import component.beans.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.ChangeService;
import utils.alert.AlertStage;
import utils.http.AllHttpComUtils;

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
        String adminAccount = ad_text_account.getText();
        String adminPassword = ad_text_password.getText();

        if (AlertStage.checkNotNullInput("请输入账号！", adminAccount))
            return;
        if (AlertStage.checkNotNullInput("请输入密码！", adminPassword))
            return;
        Customer customer;
        try {
            customer = AllHttpComUtils.login(adminAccount, adminPassword, false);
        } catch (Exception e) {
            // e.printStackTrace();
            AlertStage.createAlertStage("服务器出错").show();
            return;
        }
        if (customer != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/admin/MainView.fxml"));
            Parent root = loader.load();
            ChangeService.mainViewController = loader.getController();
            Scene scene = new Scene(root);
            ChangeService.adminStage.setTitle("管理端");
            ChangeService.adminStage.setScene(scene);
        }
        else {
            ad_text_password.setText("");
            AlertStage.createAlertStage("账号或密码错误").show();
        }
    }

    @FXML
    private  void adminClear(){
        ad_text_account.clear();
        ad_text_password.clear();
    }

}
