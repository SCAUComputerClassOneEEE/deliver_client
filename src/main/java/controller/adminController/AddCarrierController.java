package controller.adminController;

import component.beans.Carrier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.apache.http.HttpException;
import utils.alert.AlertStage;
import utils.http.AllHttpComUtils;
import utils.myJudge.DigitJudge;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/24 19:10
 */
public class AddCarrierController implements Initializable {

    @FXML
    private TextField carrier_id;
    @FXML
    private TextField carrier_type;
    @FXML
    private TextField deliver_time;
    @FXML
    private TextField connect_phone;
    @FXML
    private TextField detail_message;

    @FXML
    private void modify(){
        if (!inputDataJudge())return;
        Carrier carrier = setValue();
        try {
            AllHttpComUtils.updateCarrier(carrier);
        } catch (HttpException e) {
            AlertStage.createAlertStage("服务器出错").show();
            return;
        }

        System.out.println("要修改");
    }

    @FXML
    private void add(){
        if (!inputDataJudge())return;
        Carrier carrier = setValue();
        try {
            AllHttpComUtils.createCarrier(carrier);
        } catch (HttpException e) {
            AlertStage.createAlertStage("服务器出错").show();
            return;
        }
        System.out.println("要新增");
    }

    private boolean inputDataJudge(){
        if ("".equals(carrier_id.getText())||"".equals(carrier_type.getText())||"".equals(deliver_time.getText())||"".equals(connect_phone.getText())||"".equals(detail_message.getText())){
            AlertStage.createAlertStage("请输入全部信息").show();
            return false;
        }
        if (!DigitJudge.isNumeric(carrier_id.getText())||!DigitJudge.isNumeric(deliver_time.getText())){
            AlertStage.createAlertStage("第1、3行请填数字").show();
            return false;
        }
        return true;
    }

    private Carrier setValue(){
        long carrierId = Long.parseLong(carrier_id.getText());
        String carrierType = carrier_type.getText();
        Long deliverTime = Long.parseLong(deliver_time.getText());
        String connectPhone = connect_phone.getText();
        String detailMessage = detail_message.getText();
        return new Carrier(carrierId, carrierType, deliverTime, connectPhone, detailMessage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
