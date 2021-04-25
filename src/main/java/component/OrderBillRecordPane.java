package component;

import component.beans.BillView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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

    public OrderBillRecordPane(BillView billView) {
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
        isSelected.setLayoutY(26);
        isSelected.setOnMouseClicked(event -> {
            selectNum = isSelected.isSelected() ? selectNum + 1 : selectNum - 1 ;
            System.out.println("selectNum="+selectNum);
            ChangeService.packageController.synchronizeAllAction(selectNum);
        });

        orderId.setLayoutX(49);
        orderId.setLayoutY(27);

        orderCreateTime.setLayoutX(229);
        orderCreateTime.setLayoutY(27);

        receiver.setLayoutX(474);
        receiver.setLayoutY(27);

        orderStatus.setLayoutX(605);
        orderStatus.setLayoutY(27);

        isPaid.setLayoutX(749);
        isPaid.setLayoutY(27);

        charge.setLayoutX(846);
        charge.setLayoutY(27);

        this.setMinSize(910,74);
        this.setMaxSize(910,74);
        this.getChildren().addAll(isSelected,orderId,orderCreateTime,receiver,orderStatus,isPaid,charge);
    }


    public long getOrderId(){
        return Long.parseLong(this.orderId.getText().split("：")[1]);
    }

    public CheckBox getSelectCheckBox(){
        return this.isSelected;
    }
}
