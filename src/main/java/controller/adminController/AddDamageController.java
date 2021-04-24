package controller.adminController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.alert.AlertStage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/24 17:22
 */
public class AddDamageController implements Initializable {

    @FXML
    private Button submit;

    @FXML
    private TextField carrier_id;

    @FXML
    private TextField carrier_type;

    @FXML
    private TextField damage_record_time;

    @FXML
    private void submitDamage(){
        if (carrier_id.getText().equals("")||carrier_type.getText().equals("")||damage_record_time.getText().equals("")){
            AlertStage.createAlertStage("填完啊亲！").show();
        }else {
            int carrier_id_int;
            int damage_record_time_int;
            try {
                carrier_id_int = Integer.parseInt(carrier_id.getText());
                damage_record_time_int = Integer.parseInt(damage_record_time.getText());
                AlertStage.createAlertStage("ok").show();
            }catch (Exception e){
                AlertStage.createAlertStage("叼你啊，1、3填数字啊").show();
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
