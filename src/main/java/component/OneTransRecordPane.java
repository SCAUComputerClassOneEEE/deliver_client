package component;

import component.beans.Transport;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.util.Objects;

/**
 * @Author: Sky
 * @Date: 2021/4/11 15:29
 */
public class OneTransRecordPane extends AnchorPane {
    private Label orderStatus;
    private ImageView statusImg;
    private Label timeOfRecord;
    private Line splitLine;
    private TextArea detailMassage;
    private int color;
    public OneTransRecordPane(Transport t,int color){
        this.color = color;
        orderStatus = new Label(t.getStatus());
        timeOfRecord = new Label(t.getInputTime().toString());
        splitLine = new Line();
        detailMassage = new TextArea();
        detailMassage.appendText(t.getTransDetailMessage());
        statusImg = new ImageView();
        init();
    }

    private void init(){
        this.setPrefSize(475,120);
        orderStatus.setLayoutX(32);
        orderStatus.setLayoutY(14);

        String picturePath;
        if (orderStatus.getText().equals("已签收")){
            picturePath = "ok.png";
        }else if (orderStatus.getText().equals("派件中")){
            picturePath = "deliver.png";
        }else if (orderStatus.getText().equals("揽件中")){
            picturePath = "lj.png";
        }else {
            picturePath = "car.png";
        }
        statusImg.setImage(new Image(Objects.requireNonNull(OneTransRecordPane.class.getResourceAsStream("/picture/"+picturePath))));

        statusImg.setLayoutX(36);
        statusImg.setLayoutY(46);
        statusImg.setFitHeight(38);
        statusImg.setFitWidth(38);

        timeOfRecord.setLayoutX(114);
        timeOfRecord.setLayoutY(14);

        splitLine.setLayoutX(100);
        splitLine.setLayoutY(0);
        splitLine.setEndX(0);
        splitLine.setEndY(120);
        //splitLine.getStrokeDashArray().addAll(4d);

        splitLine.setStroke(Paint.valueOf(color%2==1?"#edecf4":"#4E0E2E"));


        detailMassage.setLayoutX(126);
        detailMassage.setLayoutY(45);
        detailMassage.setPrefSize(307,55);
        detailMassage.setEditable(false);
        detailMassage.setStyle("-fx-background-color: #4E0E2E");
        detailMassage.setFont(Font.font(15));


        this.setMaxSize(475,111);
        this.setMinSize(475,111);
        this.setPrefSize(475,111);
        /*
            #a39391
            #EDECF4
         */
        String col = color % 2 == 1 ? "#a39391":"#EDECF4";

        this.setStyle("-fx-background-color: "+ col);

        this.getChildren().addAll(orderStatus,timeOfRecord,splitLine,detailMassage,statusImg);
    }
}
