package component.admin;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * @Author: Sky
 * @Date: 2021/4/24 17:04
 */
public class PackageNumQueryPane extends AnchorPane {
    private Label name;
    private Label count;

    public PackageNumQueryPane(){
        name = new Label("孙考毅");
        count = new Label("寄件数："+2);
        init();
    }

    private void init(){
        name.setLayoutX(36);
        name.setLayoutY(12);
        count.setLayoutX(165);
        count.setLayoutY(12);
        this.setMaxSize(237,43);
        this.setMinSize(237,43);
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
