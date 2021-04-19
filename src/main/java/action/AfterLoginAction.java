package action;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ChangeService;

public class AfterLoginAction {

    public AfterLoginAction(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/user/Packages.fxml"));
        Parent root = loader.load();
        ChangeService.packageController = loader.getController();
        Scene scene = new Scene(root);
        ChangeService.stage.setTitle("Package View");
        primaryStage.setScene(scene);
        System.out.println("进入AfterLoginAction了!");
    }

    public static void PackageShow() throws Exception {
        new AfterLoginAction(ChangeService.stage);
        System.out.println("进入PackagenShow了.....");
        ChangeService.stage.show();
    }
}
