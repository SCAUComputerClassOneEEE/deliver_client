package controller.adminController;

import component.SimpleOrderMessagePane;
import component.admin.ChargeQueryPane;
import component.admin.PackageNumQueryPane;
import component.admin.StreetQueryPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/22 20:10
 */
public class csynagekouController implements Initializable {

    private long customer_id;

    @FXML
    private void lahei(){
        System.out.println("拉黑你");
    }

    @FXML
    private void xiaofei(){
        System.out.println("查看你");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCustomer_id(long customer_id){
        this.customer_id = customer_id;
    }

}
