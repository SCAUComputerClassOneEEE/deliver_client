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
    private Label time;

    @FXML
    private Label damageDetail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(int order_id,String sendAdd,String receiveAdd){

    }

}

