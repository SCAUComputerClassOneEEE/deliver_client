package controller.adminController;

import component.SimpleOrderMessagePane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/24 19:21
 */
public class QueryUserController implements Initializable {

    private Stage stage;

    @FXML
    private TextField customer_id;

    @FXML
    private void search(){
        System.out.println("搜索"+customer_id.getText()+"用户");
        try{
            if (isNumeric(customer_id.getText())){
                stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SimpleOrderMessagePane.class.getResource("/admin/csynagekou.fxml"));
                ((csynagekouController)loader.getController()).setCustomer_id(Long.parseLong(customer_id.getText()));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
