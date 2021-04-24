package component;

import com.alibaba.fastjson.JSONObject;
import component.beans.SimpleOrderInfoBar;
import component.beans.Transport;
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

    public SimpleOrderMessagePane(SimpleOrderInfoBar s){
        this.s = s;
        order_id = new Label(String.valueOf(s.getOrderId()));
        order_status = new Label(String.valueOf(s.getOrderStatus()));
        receive_name = new Label(s.getReceiveName());
        order_creat_time = new Label(s.getOrderCreateTime().toString());
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
        order_id.setLayoutX(14);
        order_id.setLayoutY(23);

        order_status.setLayoutX(135);
        order_status.setLayoutY(9);
        order_status.setPrefSize(75,48);
        order_status.setFont(new Font(24));
        order_status.setTextFill(Paint.valueOf("RED"));

        receive_name.setLayoutX(244);
        receive_name.setLayoutY(23);

        order_creat_time.setLayoutX(336);
        order_creat_time.setLayoutY(23);

        detail = new Label("查看详情");
        detail.setLayoutX(361);
        detail.setLayoutY(42);
        detail.setTextFill(Paint.valueOf("BLUE"));
        detail.setUnderline(true);
        addFun2Detail();

        this.getChildren().addAll(order_id,order_status,receive_name,order_creat_time,detail);
        this.setPrefSize(440,66);
    }

    private void addFun2Detail(){
        detail.setOnMouseClicked(event -> {

            try{
                detailStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SimpleOrderMessagePane.class.getResource("/user/OrderDetail.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                detailStage.setScene(scene);

                /*
                 * 这里需要发送获取数据的请求 获取之后生成界面 --sky
                 */
                OrderDetailController odc = loader.getController();
                odc.init(s.getOrderId(),s.getSendDetailAddress(),s.getReceiveDetailAddress());

                AllHttpComUtils
                        .getTransportsOfOrder(Long.parseLong(order_id.getText()))
                        .forEach(odc::addNewRecord);

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
