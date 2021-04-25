package controller.adminController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

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
        /**
         * 暂时没做输入值检测
         */
        long carrierId = Long.parseLong(carrier_id.getText());
        String carrierType = carrier_type.getText();
        int deliverTime = Integer.parseInt(deliver_time.getText());
        String connectPhone = connect_phone.getText();
        String detailMessage = detail_message.getText();

        /**
         * adminHttp
         * 老卢在这里做修改 查有没有carrierId、carrierType，没有不能改
         */

        System.out.println("要修改");
    }

    @FXML
    private void add(){
        /**
         * 暂时没做输入值检测
         */
        long carrierId = Long.parseLong(carrier_id.getText());
        String carrierType = carrier_type.getText();
        int deliverTime = Integer.parseInt(deliver_time.getText());
        String connectPhone = connect_phone.getText();
        String detailMessage = detail_message.getText();

        /**
         * 老卢在这里做新增 直接增
         */
        System.out.println("要新增");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
