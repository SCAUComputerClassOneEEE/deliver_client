package action;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ChangeService;

public class SignInAction {

    public  SignInAction(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/user/UserLogin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ChangeService.stage.setTitle("User login");
        primaryStage.setScene(scene);
    }

    public static void LoginShow(){
        ChangeService.stage.show();
    }

}
