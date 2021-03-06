package application;

import controller.userController.QueryTrackingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;
import service.ChangeService;

import static javafx.application.Application.launch;

public class UserMain extends Application {

    public void start(Stage primaryStage) throws Exception {
        ChangeService.stage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        try {
            FXMLLoader loader = new FXMLLoader();
            System.out.println(getClass().getResource("/user/QueryTracking.fxml"));
            loader.setLocation(getClass().getResource("/user/QueryTracking.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("Query Tracking");
            ChangeService.stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
