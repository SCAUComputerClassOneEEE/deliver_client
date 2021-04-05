package component;

import component.beans.SimpleOrderInfoBar;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * @Author: Sky
 * @Date: 2021/3/29 15:30
 */
public class SimpleOrderMessagePane extends AnchorPane {
    private Label order_id;
    private Label order_status;
    private Label receive_name;
    private Label order_creat_time;
    private Label detail;

    public SimpleOrderMessagePane(SimpleOrderInfoBar s){
        order_id = new Label(String.valueOf(s.getOrderId()));
        order_status = new Label(String.valueOf(s.getOrderStatus()));
        receive_name = new Label(s.getReceiveName());
        order_creat_time = new Label(s.getOrderCreateTime().toString());
        init();
    }

    public SimpleOrderMessagePane(){


    }

    private void init() {
        // order_id = new Label("我是订单号");
        order_id.setLayoutX(14);
        order_id.setLayoutY(23);
        // order_status = new Label("已签收");
        order_status.setLayoutX(135);
        order_status.setLayoutY(9);
        order_status.setPrefSize(75,48);
        order_status.setFont(new Font(24));
        order_status.setTextFill(Paint.valueOf("RED"));
        // receive_name = new Label("sky");
        receive_name.setLayoutX(244);
        receive_name.setLayoutY(23);
        // order_creat_time = new Label("2021-4-1 17:45:37");
        order_creat_time.setLayoutX(336);
        order_creat_time.setLayoutY(23);
        detail = new Label("查看详情");
        detail.setLayoutX(361);
        detail.setLayoutY(42);
        detail.setTextFill(Paint.valueOf("BLUE"));
        detail.setUnderline(true);
        this.getChildren().addAll(order_id,order_status,receive_name,order_creat_time,detail);
        this.setPrefSize(440,66);
    }

}
