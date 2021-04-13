package controller.userController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import component.SimpleOrderMessagePane;
import component.beans.SimpleOrderInfoBar;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


    @FXML
    private Button packages_personal_btn_upload;

    @FXML
    private Button packages_personal_btn_modify;

    @FXML
    private Button packages_personal_btn_save;

    @FXML
    private TextField packages_personal_textfiled_city;

    @FXML
    private  TextField packages_personal_textfiled_strret;

    @FXML
    private TextField packages_personal_textfiled_detailAddress;

    @FXML
    private  TextField packages_personal_textfiled_account;

    @FXML
    private  TextField packages_personal_textfiled_password;

    @FXML
    private TextField packages_personal_textfiled_againPassword;

    @FXML
    private TextField packages_personal_textfiled_customerPhone;

    @FXML
    private TextField packages_personal_textfiled_customerName;

    @FXML
    private  Text packages_personal_text_again;

    /*装订单列表的容器 --sky*/
    private static VBox packages_show_vBox = new VBox();

    //以下是button需要绑定的action
    @FXML
    private void queryPackageInformation(){
        setVisibleFalse();
        packages_package_scrollPane.setVisible(true);
        packages_show_vBox.getChildren().add(new SimpleOrderMessagePane());
        //addNewPage(18899715136L, 0, 5);
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
        int i = 20;
        while (i -- > 0) {
            Iterator<?> content = futureTask.getContentJSON();
            while (content.hasNext()) {
                JSONObject parse = JSONObject.parseObject(content.next().toString());
                SimpleOrderInfoBar simpleOrderInfoBar = new SimpleOrderInfoBar(parse);
                content.remove();
                results.add(new SimpleOrderMessagePane(simpleOrderInfoBar));
            }
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
        packages_send_scrollPane.setVisible(true);
        packages_send_anchorPane.setVisible(true);
    }

    @FXML
    private void queryBill(){
        setVisibleFalse();;
        packages_bill_scrollPane.setVisible(true);
        packages_bill_anchorPane.setVisible(true);
    }

    @FXML
    private void modifiedPersonalInformation(){
        setVisibleFalse();;
        packages_personal_scrollPane.setVisible(true);
        packages_personal_anchorPane.setVisible(true);
        packages_personal_text_again.setVisible(false);
        packages_personal_btn_modify.setVisible(true);
        packages_personal_textfiled_againPassword.setVisible(false);
        packages_personal_btn_save.setVisible(false);
        packages_personal_textfiled_againPassword.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_strret.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_account.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_city.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_customerName.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_customerPhone.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_detailAddress.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_strret.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_password.setStyle("-fx-background-color: rgb(241,241,241);");

    }

    @FXML
    private void systemNotification(){
        setVisibleFalse();;
        packages_notes_anchorPane.setVisible(true);
        packages_notes_anchorPane.setVisible(true);

    }

    @FXML
    private void modifiedAction(){
        packages_personal_btn_modify.setVisible(false);
        packages_personal_btn_save.setVisible(true);
        packages_personal_textfiled_againPassword.setVisible(true);
        packages_personal_text_again.setVisible(true);

        packages_personal_textfiled_againPassword.setStyle("-fx-background-color: rgb(255,255,255);");
        packages_personal_textfiled_account.setStyle("-fx-background-color: rgb(255,255,255);");
        packages_personal_textfiled_city.setStyle("-fx-background-color: rgb(255,255,255);");
        packages_personal_textfiled_customerName.setStyle("-fx-background-color: rgb(255,255,255);");
        packages_personal_textfiled_customerPhone.setStyle("-fx-background-color: rgb(255,255,255);");
        packages_personal_textfiled_detailAddress.setStyle("-fx-background-color: rgb(255,255,255);");
        packages_personal_textfiled_strret.setStyle("-fx-background-color: rgb(255,255,255);");
        packages_personal_textfiled_password.setStyle("-fx-background-color: rgb(255,255,255);");


    }

    public void initialize(URL location, ResourceBundle resources) {
        setVisibleFalse();
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
        this.packages_package_scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(packages_package_scrollPane.getVvalue()==1.0){
                    System.out.println("你触及了我的底线！");
                    addNewPage(18899715136L, 0, 5);
                }
            }
        });
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
