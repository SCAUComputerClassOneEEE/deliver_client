package controller.userController;

import component.beans.NoteSimpleRecord;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/20 15:49
 */
public class NoteDetailController implements Initializable {

    @FXML
    private Label orderId;

    @FXML
    private Label time;

    @FXML
    private Label damageDetail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(NoteSimpleRecord ns){
        orderId.setText("订单编号："+ns.getOrderId().toString());
        time.setText("时间："+ns.getTime().toString());
        damageDetail.setText("事故详情："+ns.getCarrierId().toString()+"号 "+ns.getCarrierType()+" 在第"+ns.getTransportTimesOfCarrier()+"次运输时发生事故");
    }

}

