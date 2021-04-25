package component;

import component.beans.BillView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import service.ChangeService;

/**
 * @Author: Sky
 * @Date: 2021/4/18 19:33
 */
public class OrderBillRecordPane extends AnchorPane {
    public static int selectNum = 0;
    private CheckBox isSelected;
    private Label orderId;
    private Label orderCreateTime;
    private Label receiver;
    private Label orderStatus;
    private Label isPaid;
    private Label charge;

    private int color;
    public OrderBillRecordPane(BillView billView,int color) {
        this.color = color;
        isSelected = new CheckBox();
        orderId = new Label("订单编号："+ billView.getOrderId());
        orderCreateTime = new Label("创建时间："+billView.getOrderCreateTime().toString());
        receiver = new Label("收件人："+billView.getReceiver());
        orderStatus = new Label("订单状态："+billView.getOrderStatus());
        isPaid = new Label(billView.getPaid() ? "订单已支付" : "订单未支付");
        charge = new Label("¥"+ billView.getCharge());
        init();
    }

    private void init() {
        isSelected.setLayoutX(21);
        isSelected.setLayoutY(27);
        isSelected.setOnMouseClicked(event -> {
            selectNum = isSelected.isSelected() ? selectNum + 1 : selectNum - 1 ;
            System.out.println("selectNum="+selectNum);
            ChangeService.packageController.synchronizeAllAction(selectNum);
        });

        orderId.setLayoutX(49);
        orderId.setLayoutY(27);
        orderId.setTextFill(Paint.valueOf("#381f21"));

        orderCreateTime.setLayoutX(229);
        orderCreateTime.setLayoutY(27);
        orderCreateTime.setTextFill(Paint.valueOf("#381f21"));

        receiver.setLayoutX(474);
        receiver.setLayoutY(27);
        receiver.setTextFill(Paint.valueOf("#381f21"));

        orderStatus.setLayoutX(605);
        orderStatus.setLayoutY(27);
        orderStatus.setTextFill(Paint.valueOf("#381f21"));

        isPaid.setLayoutX(749);
        isPaid.setLayoutY(27);
        isPaid.setTextFill(Paint.valueOf("#381f21"));

        charge.setLayoutX(846);
        charge.setLayoutY(27);
        charge.setTextFill(Paint.valueOf("#381f21"));

        String col = color % 2 == 1 ? "#a39391":"#EDECF4";
        this.setStyle("-fx-background-color: "+ col);

        this.setMinSize(910,74);
        this.setMaxSize(910,74);
        this.setPrefSize(910,74);
        this.getChildren().addAll(isSelected,orderId,orderCreateTime,receiver,orderStatus,isPaid,charge);
    }


    public long getOrderId(){
        return Long.parseLong(this.orderId.getText().split("：")[1]);
    }

    public CheckBox getSelectCheckBox(){
        return this.isSelected;
    }
}
