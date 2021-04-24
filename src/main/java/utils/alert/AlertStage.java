package utils.alert;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @Author: Sky
 * @Date: 2021/4/24 20:02
 */
public class AlertStage {
    public static Stage createAlertStage(String message){
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        root.setCenter(new Text(message));
        return stage;
    }
}
