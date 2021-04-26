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
        alert.setResizable(false);
        return alert;
    }
    public static boolean checkNotNullInput(String error, String... input) {
        for (String s : input){
            if (s.trim().equals("")) {
                AlertStage.createAlertStage(error).show();
                return true;
            }
        }
        return false;
    }
    public static boolean checkString2LongLegality(String error, String input) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException e) {
            AlertStage.createAlertStage(error).show();
            return false;
        }
        return true;
    }
}
