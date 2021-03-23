package controller.userController;

import action.SignInAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class QueryTrackingController {

    @FXML
    private Button user_btn_signIn;

    @FXML
    private  TextField user_text_trackingno;

    @FXML
    private Button user_btn_enter;

    @FXML
    private void signIn() throws Exception {
        SignInAction.LoginShow();
    }

    @FXML
    private void query(){
        String trackingNo=user_text_trackingno.getText();

        //
    }


}
