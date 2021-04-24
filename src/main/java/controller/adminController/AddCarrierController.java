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
        System.out.println("要修改");
    }

    @FXML
    private void add(){
        System.out.println("要新增");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
