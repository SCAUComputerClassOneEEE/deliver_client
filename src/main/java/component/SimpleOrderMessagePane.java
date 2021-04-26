package component;

import com.alibaba.fastjson.JSONObject;
import component.beans.PackOrderBillInsertInfo;
import component.beans.SimpleOrderInfoBar;
import component.beans.Transport;
import controller.userController.DetailMessageController;
import controller.userController.OrderDetailController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.http.AllHttpComUtils;
import utils.http.HttpClientThreadPool;
import utils.http.HttpFutureTask;
import utils.http.HttpRequestCallable;

import java.util.Iterator;
import java.util.List;

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
    private static Stage detailStage;
    private SimpleOrderInfoBar s;

    private int color;
    public SimpleOrderMessagePane(SimpleOrderInfoBar s,int color){
        this.color = color;
        this.s = s;
        order_id = new Label("订单编号："+String.valueOf(s.getOrderId()));
        order_status = new Label("订单状态："+String.valueOf(s.getOrderStatus()));
        receive_name = new Label("收件人："+s.getReceiveName());
        order_creat_time = new Label("订单创建时间："+s.getOrderCreateTime().toString());
        init();
    }

    public SimpleOrderMessagePane(){
        order_id = new Label(String.valueOf(1));
        order_status = new Label(String.valueOf("已签收"));
        receive_name = new Label("csy");
        order_creat_time = new Label("2021/4/11 15:01:26");
        detailStage = new Stage();
        init();
    }

    private void init() {
        /*
        设置位置、字体大小颜色、添加功能等
         */
        order_id.setLayoutX(32);
        order_id.setLayoutY(33);
        order_id.setTextFill(Paint.valueOf("#631c23"));

        order_status.setLayoutX(256);
        order_status.setLayoutY(33);
        order_status.setTextFill(Paint.valueOf("#631c23"));

        receive_name.setLayoutX(407);
        receive_name.setLayoutY(33);
        receive_name.setTextFill(Paint.valueOf("#631c23"));

        order_creat_time.setLayoutX(533);
        order_creat_time.setLayoutY(33);
        order_creat_time.setTextFill(Paint.valueOf("#631c23"));

        detail = new Label("查看详情");
        detail.setLayoutX(820);
        detail.setLayoutY(33);
        detail.setTextFill(Paint.valueOf(color%2==1?"#fce8d8":"#263859"));
        detail.setUnderline(true);
        addFun2Detail();

        String col = color%2==1?" #a39391":"#EDECF4";
        this.setStyle("-fx-background-color:"+col);
        this.setPrefSize(917,85);
        this.setMaxSize(917,85);
        this.setMinSize(917,85);
        this.getChildren().addAll(order_id,order_status,receive_name,order_creat_time,detail);
    }

    private void addFun2Detail(){
        // 查看详情之后的函数
        detail.setOnMouseClicked(event -> {
            try{
                detailStage = new Stage();
                detailStage.setResizable(false);
                detailStage.sizeToScene();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SimpleOrderMessagePane.class.getResource("/user/DetailMessage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                detailStage.setScene(scene);
                DetailMessageController detailMessageController = loader.getController();

                // 应该给order_id对应的详细信息
                PackOrderBillInsertInfo packOrderBillInsertInfo = AllHttpComUtils.getPackOrderBillInsertInfo(s.getOrderId());
                if (packOrderBillInsertInfo==null) System.out.println("老卢干假工");

                detailMessageController.setOrder_id(s.getOrderId());
                // 填充
                assert packOrderBillInsertInfo != null;
                detailMessageController.fillData(packOrderBillInsertInfo);

                if (detailStage.isShowing()){
                    detailStage.close();
                }

                detailStage.initModality(Modality.APPLICATION_MODAL);
                detailStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
