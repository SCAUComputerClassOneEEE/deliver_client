package component.admin;

import component.beans.ConsumptionOfLastYear;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

/**
 * @Author: Sky
 * @Date: 2021/4/24 17:04
 */
public class ChargeQueryPane extends AnchorPane {
    private Label name;
    private Label count;

    private ConsumptionOfLastYear c;
    public ChargeQueryPane(ConsumptionOfLastYear c){
        this.c = c;
        name = new Label(c.getCustomerName());
        count = new Label("消费"+c.getConsumption()+"元");
        init();
    }

    private void init(){
        name.setLayoutX(28);
        name.setLayoutY(12);
        name.setTextFill(Paint.valueOf("#381f21"));
        count.setLayoutX(115);
        count.setLayoutY(12);
        count.setTextFill(Paint.valueOf("#381f21"));
        this.setMaxSize(237,43);
        this.setMinSize(237,43);
        this.setPrefSize(237,43);
        this.getChildren().addAll(name,count);
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
