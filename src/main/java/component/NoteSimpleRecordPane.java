package component;

import component.beans.NoteSimpleRecord;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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

    public NoteSimpleRecordPane(NoteSimpleRecord ns){
        this.ns = ns;
        photo = new Rectangle(55,55);
        message = new Label(ns.getMessage());
        time = new Label(ns.getTime());
        isRead = new Circle(5);
        init();
    }

    public NoteSimpleRecordPane(){
        this.ns = new NoteSimpleRecord("你的快递丢了","2021/4/20 16:02",false);
        photo = new Rectangle(55,55);
        message = new Label(ns.getMessage());
        time = new Label(ns.getTime());
        isRead = new Circle(5);
        init();
    }

    private void  init(){

        photo.setLayoutX(22);
        photo.setLayoutY(20);
        photo.setFill(Color.BLUE);

        message.setLayoutX(112);
        message.setLayoutY(27);

        time.setLayoutX(770);
        time.setLayoutY(60);

        isRead.setLayoutX(888);
        isRead.setLayoutY(27);
        isRead.setFill(Color.RED);
        isRead.setVisible(!ns.isRead());

        this.setPrefSize(926,94);
        this.setMaxSize(926,94);
        this.setMinSize(926,94);
        this.getChildren().addAll(photo,message,time,isRead);

        this.setOnMouseClicked(event -> {

            try{
                ns.setRead(true);
                isRead.setVisible(false);

                noteDetailStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(NoteSimpleRecordPane.class.getResource("/user/NoteDetail.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                noteDetailStage.setScene(scene);

                noteDetailStage.initModality(Modality.APPLICATION_MODAL);
                noteDetailStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
