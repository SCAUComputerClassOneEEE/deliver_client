package controller.adminController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.alert.AlertStage;
import utils.myJudge.DigitJudge;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/24 19:21
 */
public class QueryUserController implements Initializable {

    private Stage stage;

    @FXML
    private TextField customer_id;

    @FXML
    private void search(){
        if (customer_id.getText().equals("") || !DigitJudge.isNumeric(customer_id.getText())){
            AlertStage.createAlertStage("请输入数字！").show();
            return;
        }

        System.out.println("搜索"+customer_id.getText()+"用户");

        try{
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QueryUserController.class.getResource("/admin/UserDetailView.fxml"));
            UserDetailController userDetailController = (UserDetailController)loader.getController();
            System.out.println("userDetailController == null "+(userDetailController ==null));

            /**
             * 不知道为什么这里获取控制器实例出错了
             */
            // userDetailController.setCustomer_id(Long.parseLong(customer_id.getText()));

            /**
             * adminHttp
             * 这里提供一个customer_id返回用户信息
             */

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
