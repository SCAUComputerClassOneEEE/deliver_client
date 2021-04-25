package utils.alert;

import javafx.scene.control.Alert;

/**
 * @Author: Sky
 * @Date: 2021/4/24 20:02
 */
public class AlertStage {
    public static Alert createAlertStage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR ,message);
        alert.setTitle("error");
        alert.setHeaderText("Please handle the exception:");
        return alert;
    }
}
