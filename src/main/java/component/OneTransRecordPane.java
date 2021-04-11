package component;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

/**
 * @Author: Sky
 * @Date: 2021/4/11 15:29
 */
public class OneTransRecordPane extends AnchorPane {
    private Label orderStatus;
    private Label timeOfRecord;
    private Line splitLine;
    private TextArea detailMassage;

    public OneTransRecordPane(){
        orderStatus = new Label("我是状态");
        timeOfRecord = new Label("2021-4-11 15:37:46");
        splitLine = new Line();
        detailMassage = new TextArea("我是详情");
        init();
    }

    private void init(){
        this.setPrefSize(475,120);
        orderStatus.setLayoutX(25);
        orderStatus.setLayoutY(14);

        timeOfRecord.setLayoutX(114);
        timeOfRecord.setLayoutY(14);

        splitLine.setLayoutX(100);
        splitLine.setLayoutY(0);
        splitLine.setEndX(0);
        splitLine.setEndY(120);

        detailMassage.setLayoutX(114);
        detailMassage.setLayoutY(34);
        detailMassage.setPrefSize(325,77);
        detailMassage.setEditable(false);
        this.getChildren().addAll(orderStatus,timeOfRecord,splitLine,detailMassage);
    }
}
