package controller.userController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
    private Label send2ReceivePath;

    @FXML
    private VBox parent; //显示路径的载体

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(int order_id,String sendAdd,String receiveAdd){
        // "1157843878343"、"广州"、"阳江"这三个字段需要数据填充
        orderId.setText("运单编号:"+order_id);
        send2ReceivePath.setText(sendAdd.substring(0,4)+"->"+receiveAdd.substring(0,4));
    }

}

