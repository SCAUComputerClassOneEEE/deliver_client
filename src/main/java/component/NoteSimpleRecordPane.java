package component;

import component.beans.NoteSimpleRecord;
import controller.userController.NoteDetailController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @Author: Sky
 * @Date: 2021/4/20 15:20
 */
public class NoteSimpleRecordPane extends AnchorPane {
    private Rectangle photo;
    private Label message;
    private Label time;
    private Circle isRead;
    private NoteSimpleRecord ns;

    private Stage noteDetailStage;

    private int color;
    private long orderId;
    private String damageDetail;// 2号火车第3次运输出现事故

    public NoteSimpleRecordPane(NoteSimpleRecord ns,int color){
        this.color = color;
        this.ns = ns;
        String mess = "你有一个运输出现异常的订单";
        message = new Label(mess);
        time = new Label(ns.getTime().toString());
        photo = new Rectangle(55,55);
        isRead = new Circle(5);
        init();
    }

    private void  init(){

        photo.setLayoutX(22);
        photo.setLayoutY(20);
        photo.setFill(Paint.valueOf(color%2==1?"#631c23":"#005792"));

        message.setLayoutX(112);
        message.setLayoutY(27);
        message.setTextFill(Paint.valueOf("#631c23"));

        time.setLayoutX(770);
        time.setLayoutY(60);
        time.setTextFill(Paint.valueOf("#631c23"));

        isRead.setLayoutX(888);
        isRead.setLayoutY(27);
        isRead.setFill(Paint.valueOf(color%2==1?"#fce8d8":"#13334c"));

        String col = color % 2 == 1 ? "#a39391":"#EDECF4";
        this.setStyle("-fx-background-color: "+ col);

        this.setPrefSize(926,94);
        this.setMaxSize(926,94);
        this.setMinSize(926,94);
        this.getChildren().addAll(photo,message,time,isRead);

        this.setOnMouseClicked(event -> {

            try{
                isRead.setVisible(false);
                noteDetailStage.setResizable(false);
                noteDetailStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(NoteSimpleRecordPane.class.getResource("/user/NoteDetail.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                noteDetailStage.setScene(scene);

                NoteDetailController noteDetailController = loader.getController();
                // System.out.println(ns);
                noteDetailController.init(ns);
                noteDetailStage.initModality(Modality.APPLICATION_MODAL);
                noteDetailStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
