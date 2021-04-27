package controller.userController;

import action.SignInAction;
import component.SimpleOrderMessagePane;
import component.beans.PackOrderBillInsertInfo;
import component.beans.Transport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.alert.AlertStage;
import utils.http.AllHttpComUtils;
import utils.myJudge.DigitJudge;

import java.util.List;


public class QueryTrackingController {

    @FXML
    private Button user_btn_signIn;

    @FXML
    private  TextField user_text_trackingno;

    @FXML
    private Button user_btn_enter;

    @FXML
    private void signIn() throws Exception {
        SignInAction.LoginShow();
    }

    @FXML
    private void query() {
        if ("".equals(user_text_trackingno.getText()) || !DigitJudge.isNumeric(user_text_trackingno.getText())) {
            AlertStage.createAlertStage("请输入正确的订单号").show();
            return;
        }
        long trackingNo = Long.parseLong(user_text_trackingno.getText());
        try{
            Stage detailStage = new Stage();
            detailStage.setResizable(false);
            detailStage.sizeToScene();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimpleOrderMessagePane.class.getResource("/user/DetailMessage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            detailStage.setScene(scene);

            DetailMessageController dmc = loader.getController();

            PackOrderBillInsertInfo packOrderBillInsertInfo = AllHttpComUtils.getPackOrderBillInsertInfo(trackingNo);
            System.out.println(packOrderBillInsertInfo);

            dmc.setOrder_id(trackingNo);
            if (packOrderBillInsertInfo == null){
                AlertStage.createAlertStage("订单不存在").show();
                return;
            }
            dmc.fillData(packOrderBillInsertInfo);


            if (detailStage.isShowing()){
                detailStage.close();
            }
            detailStage.initModality(Modality.APPLICATION_MODAL);
            detailStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
    }

    public Button getUser_btn_enter(){
        return this.user_btn_enter;
    }
}
