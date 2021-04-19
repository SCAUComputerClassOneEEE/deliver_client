package controller.userController;

import com.alibaba.fastjson.JSONObject;
import component.OrderBillRecordPane;
import component.SimpleOrderMessagePane;
import component.beans.SimpleOrderInfoBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.http.HttpClientThreadPool;
import utils.http.HttpFutureTask;
import utils.http.HttpRequestCallable;

import java.io.*;
import java.net.URL;
import java.util.*;

public class PackageController implements Initializable {
    HttpClientThreadPool poolInstance = HttpClientThreadPool.getPoolInstance();

    interface Region {

    }

    static class City implements Region {
        String cityName;

        City(String cityName) {
            this.cityName = cityName;
        }

        @Override
        public String toString() {
            return cityName;
        }
    }

    static class Province implements Region {
        String provName;
        int id;
        List<City> cs = new ArrayList<>();

        Province(String provName, int id) {
            this.provName = provName;
            this.id = id;
        }

        void addCity(City region) {
            cs.add(region);
        }

        @Override
        public String toString() {
            return provName;
        }
    }

    private static Province[] provs = new Province[34];

    static {
        try {
            System.out.println(PackageController.class.getClassLoader().getResource("regionsData.txt"));
            InputStream resourceAsStream = PackageController.class.getClassLoader().getResourceAsStream("regionsData.txt");
            assert resourceAsStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String s;
            int arrIndex = 0;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(",");
                String var0 = split[0];
                int var1 = Integer.parseInt(split[1]);
                if (var1 == 0) provs[arrIndex++] = new Province(var0, arrIndex);
                else provs[var1 - 1].addCity(new City(var0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    上面的看不懂我就不整理了 --sky
     */

    /*
    本类是client的大头，该类是主要界面的控制器，存放大量界面功能逻辑代码，具体代码可以分块阅读，互不干扰
     */

    /*
    package按钮及其对应界面的控件与功能函数 --sky实现
     */
    @FXML
    private ScrollPane packages_package_scrollPane;

    // 查询用户的所有包裹简介
    @FXML
    private void queryPackageInformation() {
        setAllVisibleFalse();
        packages_package_scrollPane.setVisible(true);
        /*
        分页加载，一次加载五个，customerId需要输入，这里还未输入
         */
        loadPackage(18899715136L, 0, 5);
    }

    private void loadPackage(long customerId, int offset, int limit) {
        ArrayList<SimpleOrderMessagePane> results = new ArrayList<>();
        setAllVisibleFalse();
        packages_package_scrollPane.setVisible(true);
        HttpRequestCallable build = new HttpRequestCallable.HttpRequestCallableBuilder()
                .addURL("/query/list")
                .onMethod(HttpClientThreadPool.HttpMethod.GET)
                .addRequestContent("customer_id", customerId)
                .addRequestContent("offset", offset)
                .addRequestContent("length", limit)
                .build();
        HttpFutureTask futureTask = poolInstance.submitRequestTask(build);
        Iterator<?> content = null;
        while (content == null) {
            content = futureTask.getContentJSON();
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
        for (SimpleOrderMessagePane s : results) {
            packages_show_vBox.getChildren().add(s);
        }
    }


    @FXML
    private ComboBox<Region> packages_send_consiggee_province;

    @FXML
    private ComboBox<Region> packages_send_consiggee_city;

    @FXML
    private ComboBox<Region> packages_send_shipper_province;

    @FXML
    private ComboBox<Region> packages_send_shipper_city;

    @FXML
    private Text user_text_welcome;

    @FXML
    private Text user_text_username;

    @FXML
    private Button user_btn_package;

    @FXML
    private Button user_btn_send;

    @FXML
    private Button user_btn_bill;

    @FXML
    private Button user_btn_personal;

    @FXML
    private Button user_btn_notes;

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
    private TextField packages_personal_textfiled_street;

    @FXML
    private TextField packages_personal_textfiled_detailAddress;

    @FXML
    private TextField packages_personal_textfiled_account;

    @FXML
    private TextField packages_personal_textfiled_password;

    @FXML
    private TextField packages_personal_textfiled_againPassword;

    @FXML
    private TextField packages_personal_textfiled_customerPhone;

    @FXML
    private TextField packages_personal_textfiled_customerName;

    @FXML
    private Text packages_personal_text_again;

    @FXML
    private TextField packages_send_shipper_name;

    @FXML
    private TextField packages_send_shipper_phone;

    @FXML
    private TextField packages_send_shipper_detailAddress;

    @FXML
    private TextField packages_send_consiggee_name;

    @FXML
    private TextField packages_send_consiggee_phone;

    @FXML
    private TextField packages_send_consiggee_detailAddress;

    @FXML
    private CheckBox packages_send_packageType_file;

    @FXML
    private CheckBox packages_send_packageType_electronic;

    @FXML
    private CheckBox packages_send_packageType_dailyUsing;

    @FXML
    private CheckBox packages_send_packageType_clothe;

    @FXML
    private CheckBox packages_send_packageType_fresh;

    @FXML
    private CheckBox packages_send_packageType_food;

    @FXML
    private CheckBox packages_send_packageType_fragile;

    @FXML
    private CheckBox packages_send_packageType_makeup;

    @FXML
    private CheckBox packages_send_packageType_medicine;

    @FXML
    private CheckBox packages_send_packageType_other;

    @FXML
    private  TextField packages_send_packageType_other_details;

    @FXML
    private RadioButton packages_send_serviceType_nextDay;

    @FXML
    private RadioButton packages_send_serviceType_nextNextDay;

    @FXML
    private RadioButton packages_send_serviceType_not;

    @FXML
    private  CheckBox packages_send_speacialPackage_international;

    @FXML
    private  CheckBox packages_send_speacialPackage_dangerous;

    @FXML
    private  CheckBox packages_send_speacialPackage_other;

    @FXML
    private  CheckBox packages_send_speacialPackage_not;

    @FXML
    private  TextField packages_send_speacialPackage_detial;

    @FXML
    private RadioButton packages_send_payment_now;

    @FXML
    private RadioButton packages_send_serviceType_monthly;


    @FXML
    private Text protocol;

    private void addClickedAction2Protocol(){
        protocol.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root);
            root.setCenter(new Text("快递丢了老卢不负责的喔！"));
            stage.setScene(scene);
            stage.show();
        });
    }

    /*装订单列表的容器 --sky*/
    private static VBox packages_show_vBox = new VBox();



    @FXML
    private void sendExpress() {
        setAllVisibleFalse();
        packages_send_scrollPane.setVisible(true);
        packages_send_anchorPane.setVisible(true);

    }

    /*************************/
    @FXML
    private VBox orderBillVbox;

    @FXML
    private CheckBox allBill;
    @FXML
    private void allBillAction(){
        if (allBill.isSelected()){
            orderBillVbox.getChildren().forEach(o-> ((OrderBillRecordPane)o).getIsSelected().setSelected(true));
            OrderBillRecordPane.selectNum = orderBillVbox.getChildren().size();
        }else {
            orderBillVbox.getChildren().forEach(o-> ((OrderBillRecordPane)o).getIsSelected().setSelected(false));
            OrderBillRecordPane.selectNum = 0;
        }
    }

    public void synchronizeAllAction(int selectNum){
        allBill.setSelected(selectNum == orderBillVbox.getChildren().size());
    }
    @FXML
    private void queryBill() {
        setAllVisibleFalse();
        packages_bill_scrollPane.setVisible(true);
        packages_bill_anchorPane.setVisible(true);
        orderBillVbox.getChildren().clear();
        orderBillVbox.getChildren().add(new OrderBillRecordPane());
        orderBillVbox.getChildren().add(new OrderBillRecordPane());
        orderBillVbox.getChildren().add(new OrderBillRecordPane());
    }
    /*************************/

    @FXML
    private void modifiedPersonalInformation() {
        setAllVisibleFalse();
        packages_personal_scrollPane.setVisible(true);
        packages_personal_anchorPane.setVisible(true);
        packages_personal_text_again.setVisible(false);
        packages_personal_btn_modify.setVisible(true);
        packages_personal_textfiled_againPassword.setVisible(false);
        packages_personal_btn_save.setVisible(false);
        packages_personal_textfiled_againPassword.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_street.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_account.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_city.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_customerName.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_customerPhone.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_detailAddress.setStyle("-fx-background-color: rgb(241,241,241);");
        packages_personal_textfiled_password.setStyle("-fx-background-color: rgb(241,241,241);");

    }

    @FXML
    private void systemNotification() {
        setAllVisibleFalse();
        packages_notes_anchorPane.setVisible(true);
        packages_notes_anchorPane.setVisible(true);

    }

    @FXML
    private void modifiedAction() {
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
        packages_personal_textfiled_street.setStyle("-fx-background-color: rgb(255,255,255);");
        packages_personal_textfiled_password.setStyle("-fx-background-color: rgb(255,255,255);");


    }


    public void initialize(URL location, ResourceBundle resources) {
        setAllVisibleFalse();
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        HBox hBox = new HBox(33);
        hBox.setPadding(new Insets(10, 10, 10, 10));

        packages_send_shipper_province.setItems(FXCollections.observableArrayList(provs));

        packages_send_consiggee_province.setItems(FXCollections.observableArrayList(provs));

        packages_send_shipper_province.getSelectionModel().selectedItemProperty().addListener((o, t, t1) -> {
            Province province = (Province) t1;
            packages_send_shipper_city.setItems(FXCollections.observableArrayList(provs[province.id - 1].cs));
        });

        packages_send_consiggee_province.getSelectionModel().selectedItemProperty().addListener((o, t, t1) -> {
            Province province = (Province) t1;
            packages_send_consiggee_city.setItems(FXCollections.observableArrayList(provs[province.id - 1].cs));
        });

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
                if (packages_package_scrollPane.getVvalue() == 1.0) {
                    System.out.println("你触及了我的底线！");
                    loadPackage(18899715136L, 0, 5);
                }
            }
        });


        addClickedAction2Protocol();
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

    private void setAllVisibleFalse() {
        packages_package_scrollPane.setVisible(false);
        packages_send_scrollPane.setVisible(false);
        packages_bill_scrollPane.setVisible(false);
        packages_notes_scrollPane.setVisible(false);
        packages_personal_scrollPane.setVisible(false);
    }

}
