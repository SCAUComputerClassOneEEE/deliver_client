package controller.userController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PackageController {
    @FXML
    private Text user_text_welcome;

    @FXML
    private Text user_text_username;

    @FXML
    private Button user_btn_package;

    @FXML
    private  Button user_btn_send;

    @FXML
    private  Button user_btn_bill;

    @FXML
    private  Button user_btn_personal;

    @FXML
    private  Button user_btn_notes;


    //以下是button需要绑定的action
    @FXML
    private void queryPackageInformation(){

    }

    @FXML
    private void sendExpress(){

    }

    @FXML
    private void queryBill(){

    }

    @FXML
    private void modifiedPersonalInformation(){

    }

    @FXML
    private void systemNotification(){

    }
}
