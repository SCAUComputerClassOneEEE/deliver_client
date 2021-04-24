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
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        if (carrier_id.getText().equals("")||carrier_type.getText().equals("")||damage_record_time.getText().equals("")){
            root.setCenter(new Text("填完啊亲！"));
            stage.show();
        }else {
            int carrier_id_int;
            int damage_record_time_int;
            try {
                carrier_id_int = Integer.parseInt(carrier_id.getText());
                damage_record_time_int = Integer.parseInt(damage_record_time.getText());
                root.setCenter(new Text("ok"));
            }catch (Exception e){
                root.setCenter(new Text("叼你啊，1、3填数字啊"));
            }finally {
                stage.show();
            }

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
