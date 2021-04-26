package component.admin;

import component.beans.Customer;
import component.beans.NumberOfLastYear;
import controller.adminController.QueryUserController;
import controller.adminController.UserDetailController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.http.HttpException;
import utils.alert.AlertStage;
import utils.http.AllHttpComUtils;

/**
 * @Author: Sky
 * @Date: 2021/4/24 17:04
 */
public class PackageNumQueryPane extends AnchorPane {
    private Label name;
    private Label count;

    private NumberOfLastYear n;
    public PackageNumQueryPane(NumberOfLastYear n){
        this.n = n;
        name = new Label(n.getCustomerName());
        count = new Label("寄件数："+n.getNumber());
        init();
    }

    private void init(){
        name.setLayoutX(36);
        name.setLayoutY(12);
        name.setTextFill(Paint.valueOf("#381f21"));
        count.setLayoutX(127);
        count.setLayoutY(12);
        count.setTextFill(Paint.valueOf("#381f21"));
        this.setMaxSize(237,43);
        this.setMinSize(237,43);
        this.setPrefSize(237,43);
        this.getChildren().addAll(name,count);
        this.setOnMouseClicked(event -> {
            try {
                Stage stage = new Stage();
                stage.setResizable(false);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(QueryUserController.class.getResource("/admin/UserDetailView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                UserDetailController userDetailController = loader.getController();
                Customer customer;
                try {
                    customer = AllHttpComUtils.selectCustomerById(n.getCustomerId());
                } catch (HttpException e) {
                    AlertStage.createAlertStage("服务器异常").show();
                    return;
                }

                userDetailController.init(customer);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public Label getCount() {
        return count;
    }

    public void setCount(Label count) {
        this.count = count;
    }
}
