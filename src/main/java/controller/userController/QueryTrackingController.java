package controller.userController;

import action.SignInAction;
import component.SimpleOrderMessagePane;
import component.beans.Transport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
        if ("".equals(user_text_trackingno.getText()) || !DigitJudge.isNumeric(user_text_trackingno.getText())) return;
        long trackingNo = Long.parseLong(user_text_trackingno.getText());
        try{
            Stage detailStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimpleOrderMessagePane.class.getResource("/user/OrderDetail.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            detailStage.setScene(scene);

            OrderDetailController odc = loader.getController();
            odc.init(trackingNo,"广东省;广州市;154","广东省;阳江市;154");

            AllHttpComUtils
                    .getTransportsOfOrder(trackingNo)
                    .forEach(odc::addNewRecord);

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


}
