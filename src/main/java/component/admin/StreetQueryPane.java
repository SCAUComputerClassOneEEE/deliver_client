package component.admin;

import component.beans.StreetStatistics;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * @Author: Sky
 * @Date: 2021/4/24 17:04
 */
public class StreetQueryPane extends AnchorPane {
    private Label street;
    private Label count;

    private StreetStatistics s;
    public StreetQueryPane(StreetStatistics s){
        this.s = s;
        street = new Label(s.getStreet());
        count = new Label(""+s.getNumber()+"人");
        init();
    }

    private void init(){
        street.setLayoutX(28);
        street.setLayoutY(12);
        count.setLayoutX(170);
        count.setLayoutY(12);
        this.setMaxSize(237,43);
        this.setMinSize(237,43);
        this.getChildren().addAll(street,count);
    }

    public Label getStreet() {
        return street;
    }

    public void setStreet(Label street) {
        this.street = street;
    }

    public Label getCount() {
        return count;
    }

    public void setCount(Label count) {
        this.count = count;
    }
}
