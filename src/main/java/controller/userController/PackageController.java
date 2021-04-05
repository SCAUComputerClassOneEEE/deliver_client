package controller.userController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import component.SimpleOrderMessagePane;
import component.beans.SimpleOrderInfoBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.apache.http.impl.client.HttpClientBuilder;
import utils.HttpClientThreadPool;
import utils.HttpFutureTask;
import utils.HttpRequestCallable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;

public class PackageController implements Initializable {
    HttpClientThreadPool poolInstance = HttpClientThreadPool.getPoolInstance();

    @FXML
    private Text user_text_welcome;

    @FXML
    private Text user_text_username;

    @FXML
    private Button user_btn_package;

    @FXML
    private  Button user_btn_send;

    @FXML
    private  Button user_btn_bill;

    @FXML
    private  Button user_btn_personal;

    @FXML
    private  Button user_btn_notes;

    @FXML
    private AnchorPane user_pane_package_anchorPane;

    @FXML
    private AnchorPane packages_anchorPane;

    @FXML
    private AnchorPane packages_package_anchorPane;

    @FXML
    private AnchorPane packages_send_anchorPane;

    @FXML
    private AnchorPane packages_bill_anchorPane;

    @FXML
    private AnchorPane packages_notes_anchorPane;

    @FXML
    private AnchorPane packages_personal_anchorPane;

    @FXML
    private ScrollPane packages_package_scrollPane;

    @FXML
    private ScrollPane packages_send_scrollPane;

    @FXML
    private ScrollPane packages_bill_scrollPane;

    @FXML
    private ScrollPane packages_notes_scrollPane;

    @FXML
    private ScrollPane packages_personal_scrollPane;



    /*装订单列表的容器 --sky*/
    private static VBox packages_show_vBox = new VBox();

    //以下是button需要绑定的action
    @FXML
    private void queryPackageInformation(){
        //packages_show_vBox.getChildren().clear();
        addNewPage(18899715136L, 0, 5);
    }

    private void addNewPage(long customerId, int offset, int limit) {
        ArrayList<SimpleOrderMessagePane> results = new ArrayList<>();
        setVisibleFalse();
        packages_package_scrollPane.setVisible(true);
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/list")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("customer_id", customerId)
                .addRequestContent("offset", offset)
                .addRequestContent("length", limit)
                .build();
        HttpFutureTask futureTask = poolInstance.submitRequestTask(build);
        int i = 5;
        while (i -- > 0) {

            JSONArray contentJSON = futureTask.getContentJSON(50);
            if (contentJSON != null) {
                Iterator<Object> iterator = contentJSON.iterator();
                while (iterator.hasNext()) {
                    JSONObject parse = JSONObject.parseObject(iterator.next().toString());
                    SimpleOrderInfoBar simpleOrderInfoBar = new SimpleOrderInfoBar();
                    simpleOrderInfoBar.setOrderId(parse.getInteger("orderId"));
                    simpleOrderInfoBar.setOrderCreateTime(parse.getTimestamp("orderCreateTime"));
                    simpleOrderInfoBar.setOrderStatus(parse.getString("orderStatus"));
                    simpleOrderInfoBar.setReceiveName(parse.getString("receiveName"));
                    System.out.println(simpleOrderInfoBar.toString());
                    iterator.remove();
                    results.add(new SimpleOrderMessagePane(simpleOrderInfoBar));
                }
                break;
            }

            //
            System.out.println("等待：" + i);
        }
        /*
        这里需要提供描述订单的类的数据数组，然后循环添加
        */
        for (SimpleOrderMessagePane s: results){
            packages_show_vBox.getChildren().add(s);
        }
    }

    @FXML
    private void sendExpress(){
        setVisibleFalse();
    }

    @FXML
    private void queryBill(){

    }

    @FXML
    private void modifiedPersonalInformation(){

    }

    @FXML
    private void systemNotification(){

    }


    public void initialize(URL location, ResourceBundle resources) {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        Text t = new Text();
        t.setEffect(ds);
        t.setCache(true);
        t.setX(10.0f);
        t.setY(40.0f);
        t.setFill(Color.RED);
        t.setText("Epress View");
        t.setFont(Font.font(null, FontWeight.BOLD, 42));
        this.user_pane_package_anchorPane.getChildren().add(t);

        /*将订单列表容器加到anchor中 --sky*/
        this.packages_package_scrollPane.setContent(packages_show_vBox);
        // this.packages_package_scrollPane
        /*PerspectiveTransform pt = new PerspectiveTransform();
        pt.setUlx(10.0f);
        pt.setUly(10.0f);
        pt.setUrx(310.0f);
        pt.setUry(40.0f);
        pt.setLrx(310.0f);
        pt.setLry(60.0f);
        pt.setLlx(10.0f);
        pt.setLly(90.0f);

        this.user_pane_package_anchorPane.setEffect(pt);
        this.user_pane_package_anchorPane.setCache(true);

        Rectangle r = new Rectangle();
        r.setX(10.0f);
        r.setY(10.0f);
        r.setWidth(280.0f);
        r.setHeight(80.0f);
        r.setFill(Color.BLUE);

        Text t1 = new Text();
        t1.setX(20.0f);
        t1.setY(65.0f);
        t1.setText("Express View");
        t1.setFill(Color.YELLOW);
        t1.setFont(Font.font(null, FontWeight.BOLD, 36));
        this.user_pane_package_anchorPane.getChildren().add(r);
        this.user_pane_package_anchorPane.getChildren().add(t1);*/
    }

    private void setVisibleFalse(){
        packages_package_scrollPane.setVisible(false);
        packages_send_scrollPane.setVisible(false);
        packages_bill_scrollPane.setVisible(false);
        packages_notes_scrollPane.setVisible(false);
        packages_personal_scrollPane.setVisible(false);
    }
}
