package controller.adminController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/22 20:10
 */
public class UserDetailController implements Initializable {

    @FXML
    private Button modify;

    @FXML
    private Label authorization;

    @FXML
    private void modifyFun(){
        if (modify.getText().equals("允许登录")){
            authorization.setText("允许登录");
            modify.setText("禁止登录");
        }else {
            authorization.setText("禁止登录");
            modify.setText("允许登录");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modify.setText("允许登录");
        authorization.setText("禁止登录");
    }

}
