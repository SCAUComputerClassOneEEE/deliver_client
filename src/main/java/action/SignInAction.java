package action;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ChangeService;

public class SignInAction {

    public  SignInAction(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/user/UserLogin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ChangeService.stage.setTitle("User login");
        primaryStage.setScene(scene);
        System.out.println("进入signINAction了!");
    }

    public static void LoginShow() throws Exception {
        new SignInAction(ChangeService.stage);
        System.out.println("进入LoginShow了.....");
        ChangeService.stage.show();
    }

}
