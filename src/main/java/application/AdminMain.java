package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ChangeService;

/**
 * @Author: Sky
 * @Date: 2021/4/22 19:50
 */
public class AdminMain extends Application {

    public void start(Stage primaryStage) throws Exception {
        ChangeService.adminStage = primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader();
            System.out.println(getClass().getResource("/admin/AdminLogin.fxml"));
            loader.setLocation(getClass().getResource("/admin/AdminLogin.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            ChangeService.adminStage.setScene(scene);
            ChangeService.adminStage.setTitle("AdminServer");
            ChangeService.adminStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
