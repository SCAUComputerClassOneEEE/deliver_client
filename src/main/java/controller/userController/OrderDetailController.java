package controller.userController;

import component.OneTransRecordPane;
import component.beans.Transport;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/10 14:29
 */
public class OrderDetailController implements Initializable {

    @FXML
    private Label orderId;

    @FXML
    private Label send2ReceivePath;

    @FXML
    private VBox parent; //显示路径的载体

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("加载订单详情界面");
    }

    public void init(int order_id,String sendAdd,String receiveAdd){
        // "1157843878343"、"广州"、"阳江"这三个字段需要数据填充
        orderId.setText("运单编号:"+order_id);
        send2ReceivePath.setText(sendAdd.split(";")[1]+"->"+receiveAdd.split(";")[1]);
    }

    public void addNewRecord(Transport t){
        parent.getChildren().add(1,new OneTransRecordPane(t));
    }
}
