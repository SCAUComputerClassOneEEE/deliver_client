package controller.adminController;

import component.SimpleOrderMessagePane;
import component.admin.PackageNumQueryPane;
import component.admin.StreetQueryPane;
import component.admin.ChargeQueryPane;
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
public class MainViewController implements Initializable {

    private Stage stage;

    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox2;

    @FXML
    private VBox vBox3;

    @FXML
    private void button1Fun(){
        try{
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimpleOrderMessagePane.class.getResource("/admin/AddDamageView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void button2Fun(){
        try{
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimpleOrderMessagePane.class.getResource("/admin/AddCarrierView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void button3Fun(){
        try{
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimpleOrderMessagePane.class.getResource("/admin/QueryUserView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void button4Fun(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init(){
        vBox1.getChildren().add(new StreetQueryPane());
        vBox2.getChildren().add(new ChargeQueryPane());
        vBox3.getChildren().add(new PackageNumQueryPane());
    }
}
