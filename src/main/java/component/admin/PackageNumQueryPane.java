package component.admin;

import component.beans.NumberOfLastYear;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

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
