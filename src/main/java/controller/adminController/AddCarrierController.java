package controller.adminController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import utils.alert.AlertStage;
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
        setValue();
        /**
         * adminHttp
         * 老卢在这里做修改 查有没有carrierId、carrierType，没有不能改
         */

        System.out.println("要修改");
    }

    @FXML
    private void add(){
        if (!inputDataJudge())return;
        setValue();

        /**
         * 老卢在这里做新增 直接增
         */
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

    private void setValue(){
        long carrierId = Long.parseLong(carrier_id.getText());
        String carrierType = carrier_type.getText();
        int deliverTime = Integer.parseInt(deliver_time.getText());
        String connectPhone = connect_phone.getText();
        String detailMessage = detail_message.getText();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
