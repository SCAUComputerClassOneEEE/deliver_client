package component;

import component.beans.Bill;
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
    private Label paidType;
    private Label charge;

    public OrderBillRecordPane(Bill bill) {
        isSelected = new CheckBox();
        orderId = new Label(bill.getOrderId().toString());
        orderCreateTime = new Label();
        receiver = new Label("陈思宇");
        orderStatus = new Label("已签收");
        //paidType = new Label("月付"); 这个应该不用了如果只有月付的话
        charge = new Label("12");
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
        orderCreateTime.setLayoutX(212);
        orderCreateTime.setLayoutY(27);
        receiver.setLayoutX(445);
        receiver.setLayoutY(27);
        orderStatus.setLayoutX(532);
        orderStatus.setLayoutY(27);
        /*paidType.setLayoutX(612);
        paidType.setLayoutY(27);*/
        charge.setLayoutX(664);
        charge.setLayoutY(27);
        this.setMinSize(719,74);
        this.setMaxSize(719,74);
        this.getChildren().addAll(isSelected,orderId,orderCreateTime,receiver,orderStatus,charge);
    }

    public CheckBox getIsSelected(){
        return this.isSelected;
    }
}
