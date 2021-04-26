package controller.userController;

import component.NoteSimpleRecordPane;
import component.OrderBillRecordPane;
import component.SimpleOrderMessagePane;
import component.beans.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ChangeService;
import utils.alert.AlertStage;
import utils.http.AllHttpComUtils;
import utils.image.QRCodeUtil;

import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PackageController implements Initializable {
    private ArrayList<CheckBox> checkBoxes1 = new ArrayList<>();
    private ArrayList<CheckBox> checkBoxes2 = new ArrayList<>();

    private String gray="-fx-background-color: rgb(237,236,244);";
    private String white="-fx-background-color: rgb(255，255,255)";

    interface Region { }

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

    /*
    * 文件加载静态块
    * */
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

    /**
     * 自动初始化函数
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        setAllInvisible();
        packages_package_scrollPane.setVisible(true);
        queryPackageInformation();
        user_text_username.setText(ChangeService.userLoginController.getCustomer().getCustomerName());
        /**
         * 监听器去到对应的函数里面加
         */
        initBigPane();
        initPackage();
        initSend();
        initBill();
        initPersonal();
        initNote();
    }

    /*
    上面的看不懂我就不整理了 --sky
     */

    /*
    本类是client的大头，该类是主要界面的控制器，存放大量界面功能逻辑代码，具体代码可以分块阅读，互不干扰
     */

    /**
     * 第零个功能
     * 属于整个大的面板的控件
     */
    private void get0() {
        getBigPaneFXML();
    }

    private void initBigPane() {
        packages_personal_textfiled_customerID.setText(""+ UserLoginController.getCustomer().getCustomerId());


    }

    /**
     * 第一个功能
     * package按钮及其对应界面的控件与功能函数 --sky实现
     */
    private void get1() {
        getPackageFXML();
    }

    private int offset = 0;
    //private int lastCount = 0;
    private final int size  = 5;
    // 查询用户的所有包裹简介
    @FXML
    private void queryPackageInformation() {
        offset = 0;
        setAllInvisible();
        packages_package_scrollPane.setVisible(true);
        packages_show_vBox.setVisible(true);
        AnchorPane title =  (AnchorPane)packages_show_vBox.getChildren().get(0);
        packages_show_vBox.getChildren().clear();
        packages_show_vBox.getChildren().add(title);
        /*
        分页加载，一次加载五个，customerId需要输入，这里还未输入
         */
        loadPackage(ChangeService.userLoginController.getCustomerId(), offset, size);
    }

    // 加载一次记录
    private void loadPackage(long customerId, int offset, int limit) {
        final ArrayList<SimpleOrderMessagePane> results = new ArrayList<>();
        setAllInvisible();
        packages_package_scrollPane.setVisible(true);
        List<SimpleOrderInfoBar> simpleOrderInfoBarPage = AllHttpComUtils.getSimpleOrderInfoBarPage(customerId, offset, limit);
        if (simpleOrderInfoBarPage == null) return;
        AtomicInteger i = new AtomicInteger(1);
        simpleOrderInfoBarPage.forEach((s) -> results.add(new SimpleOrderMessagePane(s, i.getAndIncrement())));
        /*
        这里需要提供描述订单的类的数据数组，然后循环添加
        */
        this.offset+=results.size();
        for (SimpleOrderMessagePane s : results) {
            System.out.println(",,");
            packages_show_vBox.getChildren().add(s);
        }
    }

    // 给查询包裹的Scroll添加滑动到底部的监听器
    private void addAction2Scroll() {
        /*将订单列表容器加到anchor中 --sky*/
        this.packages_package_scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (packages_package_scrollPane.getVvalue() == 1.0) {
                    System.out.println("你触及了我的底线！");
                    // 18899需要获取动态账号
                    loadPackage(ChangeService.userLoginController.getCustomerId(), offset, size);
                }
            }
        });
    }

    private void initPackage() {
        addAction2Scroll();
    }

    /**
     * 第二个功能
     * send --csy实现
     */
    private void get2() {
        getSendFXML();
    }
    /* send 模块功能函数 */

    // 省部下拉级联
    private void addProvinceFun() {
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
    }

    // 老卢丢了不负责
    private void addClickedAction2Protocol() {
        protocol.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root);
            AlertStage.createAlertStage("快递丢了老卢不负责的喔！");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        });
    }

    private void pacTypeOnlyOne() {
        if (packages_send_packageType_file.isSelected()) {
            packages_send_packageType_electronic.setSelected(false);
            packages_send_packageType_dailyUsing.setSelected(false);
            packages_send_packageType_clothe.setSelected(false);
            packages_send_packageType_fresh.setSelected(false);
            packages_send_packageType_food.setSelected(false);
            packages_send_packageType_fragile.setSelected(false);
            packages_send_packageType_makeup.setSelected(false);
            packages_send_packageType_medicine.setSelected(false);
        } else if (packages_send_packageType_electronic.isSelected()) {

        }

    }


    //提交按钮
    @FXML
    private void addClickedActionSend() {
        PackOrderBillInsertInfo packOrderBillInsertInfo = new PackOrderBillInsertInfo();
        // 发件人名字
        {
            String trim = packages_send_shipper_name.getText().trim();
            if (AlertStage.checkNotNullInput("请输入发件人名字", trim)) return;
            packOrderBillInsertInfo.setShipperName(trim);
        }
        // 发件人手机
        {
            String trim = packages_send_shipper_phone.getText().trim();
            if (AlertStage.checkNotNullInput("请输入发件人手机", trim)) return;
            packOrderBillInsertInfo.setShipperPhoneNumber(trim);
        }
        // 发件人地址
        try {
            String prov = packages_send_shipper_province.getValue().toString();
            String city = packages_send_shipper_city.getValue().toString();
            String ds = packages_send_shipper_detailAddress.getText().trim();
            if (AlertStage.checkNotNullInput("请输入发件人详细地址", ds)) return;
            packOrderBillInsertInfo.setDeparture(prov + ';' + city + ";" + ds);
        } catch (NullPointerException nullPointerException) {
            AlertStage.createAlertStage("请选择省份或城市").show();
            return;
        }
        // 收件人名字
        {
            String trim = packages_send_consiggee_name.getText().trim();
            if (AlertStage.checkNotNullInput("请输入收件人名字", trim)) return;
            packOrderBillInsertInfo.setConsiggeeName(trim);
        }
        // 收件人手机
        {
            String trim = packages_send_consiggee_phone.getText().trim();
            if (AlertStage.checkNotNullInput("请输入发件人手机", trim)) return;
            packOrderBillInsertInfo.setConsiggeePhoneNumber(trim);
        }
        // 收件人地址
        try {
            String prov = packages_send_consiggee_province.getValue().toString();
            String city = packages_send_consiggee_city.getValue().toString();
            String ds = packages_send_consiggee_detailAddress.getText().trim();
            if (AlertStage.checkNotNullInput("请输入收件人详细地址", ds)) return;
            packOrderBillInsertInfo.setAddress(prov + ';' + city + ";" + ds);
        } catch (NullPointerException nullPointerException) {
            AlertStage.createAlertStage("请选择省份或城市").show();
            return;
        }
        // 承诺到达时间
        {
            int days = packages_send_serviceType_nextDay.isSelected() ? 1 : (packages_send_serviceType_nextNextDay.isSelected() ? 2 : 3);
            Timestamp timestamp = new Timestamp(new Date().getTime() + days * 24 * 60 * 60 * 1000);
            packOrderBillInsertInfo.setCommitArriveTime(timestamp);
            if (packages_send_serviceType_nextDay.isSelected()) {
                packOrderBillInsertInfo.setPackType("次日达");
            } else if (packages_send_serviceType_nextNextDay.isSelected()) {
                packOrderBillInsertInfo.setPackType("后日达");
            } else {
                packOrderBillInsertInfo.setPackType("不需要特殊服务");
            }
        }
        //package info
        //传是哪一种包裹类型
        {
            boolean flag = false;
            for (CheckBox e : checkBoxes1) {
                if (e.isSelected()) {
                    packOrderBillInsertInfo.setPackType(e.getText());
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                packOrderBillInsertInfo.setPackType("null");
            }

            packOrderBillInsertInfo.setDetailMess(packages_send_speacialPackage_detial.getText().trim());
            packOrderBillInsertInfo.setDangerous(packages_send_speacialPackage_dangerous.isSelected());
            packOrderBillInsertInfo.setInter(packages_send_speacialPackage_international.isSelected());
            packOrderBillInsertInfo.setPackWeight(1.0);
        }
        //bill info
        //费用
        {
            if (packages_send_consiggee_province.getValue().toString().equals("广东省")) {
                packOrderBillInsertInfo.setCharge(8);
            } else {
                packOrderBillInsertInfo.setCharge(12);
            }
        }
        //id
        packOrderBillInsertInfo.setCustomerId(ChangeService.userLoginController.getCustomerId());///要用户名

        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        // 二维码
        if (packages_send_payment_now.isSelected()) {
            packOrderBillInsertInfo.setPackType("pay now");
            final String url = AllHttpComUtils.qr_pay_url;
            long billId = AllHttpComUtils.createP_O_BInsertInfo(packOrderBillInsertInfo);
            if (billId == 0) {
                AlertStage.createAlertStage("服务器已下线").show();
                return;
            }
            Image fxImage = QRCodeUtil.encode2FXImage(
                            url + "/id=" + billId,
                            null,
                            true);
            if (fxImage != null) {
                root.setCenter(new ImageView(fxImage));
            } else {
                AlertStage.createAlertStage("二维码上传异常").show();
                return;
            }
        }
        else if (packages_send_payment_monthly.isSelected()) {
            if (AllHttpComUtils.createP_O_BInsertInfo(packOrderBillInsertInfo) == 0) {
                AlertStage.createAlertStage("服务器已下线").show();
                return;
            }
            packOrderBillInsertInfo.setPayType("月支付");
            root.setCenter(new TextField("已进入待支付"));
        }
        System.out.println(packOrderBillInsertInfo);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
    }

    // 发送按钮
    @FXML
    private void sendExpress() {
        setAllInvisible();
        packages_send_scrollPane.setVisible(true);
        packages_send_anchorPane.setVisible(true);
    }

    private void initSend() {
        addProvinceFun();
        addClickedAction2Protocol();
        setAllSelectTrue();
        {
            /*
            * 搞个初值~
            * */
            packages_send_serviceType_nextDay.setSelected(true);
            packages_send_payment_now.setSelected(true);
            packages_send_speacialPackage_not.setSelected(true);
        }
        ToggleGroup group = new ToggleGroup(); // 创建一个按钮小组
        packages_send_serviceType_nextDay.setToggleGroup(group); // 把单选按钮1加入到按钮小组
        packages_send_serviceType_nextNextDay.setToggleGroup(group); // 把单选按钮2加入到按钮小组
        packages_send_serviceType_not.setToggleGroup(group); // 把单选按钮3加入到按钮小组

        ToggleGroup group1 = new ToggleGroup(); // 创建一个按钮小组
        packages_send_payment_monthly.setToggleGroup(group1); // 把单选按钮1加入到按钮小组
        packages_send_payment_now.setToggleGroup(group1); // 把单选按钮2加入到按钮小组


        Collections.addAll(checkBoxes1, packages_send_packageType_file, packages_send_packageType_electronic, packages_send_packageType_dailyUsing,
                packages_send_packageType_clothe, packages_send_packageType_fragile, packages_send_packageType_fresh, packages_send_packageType_food,
                packages_send_packageType_makeup, packages_send_packageType_medicine);
        addListener2Checkbox(checkBoxes1);


        Collections.addAll(checkBoxes2, packages_send_speacialPackage_international, packages_send_speacialPackage_dangerous, packages_send_speacialPackage_not);
        addListener2Checkbox(checkBoxes2);


    }

    private void addListener2Checkbox(ArrayList<CheckBox> checkBoxes) {
        checkBoxes.forEach(e -> {
            e.setOnMouseClicked(event -> {
                if (e.isSelected()) { //未被选择的情况
                    checkBoxes.forEach(o -> {
                        o.setSelected(false);
                        o.setDisable(true);
                    });
                    e.setSelected(true);
                    e.setDisable(false);
                } else {
                    checkBoxes.forEach(o -> {
                        o.setSelected(false);
                        o.setDisable(false);
                    });
                }
            });
        });
    }

    /**
     * 第三个功能
     * bill --sky实现
     */
    private void get3() {
        getBillFXML();
    }

    private List<BillView> allBillViews;

    @FXML
    private Label billMessage1;
    @FXML
    private Label billMessage2;
    @FXML
    private Label billMessage3;
    // 全选按钮的功能
    @FXML
    private void allBillAction() {
        if (allBill.isSelected()) {
            orderBillVbox.getChildren().forEach(o -> ((OrderBillRecordPane) o).getSelectCheckBox().setSelected(true));
            OrderBillRecordPane.selectNum = orderBillVbox.getChildren().size();
        } else {
            orderBillVbox.getChildren().forEach(o -> ((OrderBillRecordPane) o).getSelectCheckBox().setSelected(false));
            OrderBillRecordPane.selectNum = 0;
        }
    }

    // 同步全选按钮
    public void synchronizeAllAction(int selectNum) {
        allBill.setSelected(selectNum == orderBillVbox.getChildren().size());
    }

    @FXML
    private void queryBill() {
        orderBillVbox.getChildren().clear();
        setAllInvisible();
        packages_bill_scrollPane.setVisible(true);
        packages_bill_anchorPane.setVisible(true);
        orderBillVbox.getChildren().clear();
        System.out.println("正在查询");
        allBillViews = AllHttpComUtils.getAllBills(ChangeService.userLoginController.getCustomerId());
        if(allBillViews!=null){
            queryAllBill();
        }
        BillOfLastMonth billOfLastMonth = AllHttpComUtils.getBillOfLastMonth(ChangeService.userLoginController.getCustomerId());
        System.out.println(billOfLastMonth);
        int 上个月寄件数 = billOfLastMonth.getSendPacksCount();
        double 上个月消费数 = billOfLastMonth.getMoneyNumber();
        double 上个月欠款数 = billOfLastMonth.getLastMonthArrears();
        billMessage1.setText("上个月寄件数:"+上个月寄件数);
        billMessage2.setText("上个月消费数:"+上个月消费数);
        billMessage3.setText("上个月欠款数:"+上个月欠款数);

    }

    private void initBill() {

    }

    @FXML
    private void queryAllBill(){
        OrderBillRecordPane.selectNum=0;
        allBill.setSelected(false);
        orderBillVbox.getChildren().clear();
        AtomicInteger j = new AtomicInteger(1);
        allBillViews.forEach(o->orderBillVbox.getChildren().add(new OrderBillRecordPane(o, j.getAndIncrement())));
        for (int i=0;i<orderBillVbox.getChildren().size();i++){
            ((OrderBillRecordPane)orderBillVbox.getChildren().get(i)).getSelectCheckBox().setDisable(true);
        }
        allBill.setDisable(true);
    }

    @FXML
    private void queryUnpaidBill(){
        OrderBillRecordPane.selectNum=0;
        allBill.setSelected(false);
        orderBillVbox.getChildren().clear();
        AtomicInteger j = new AtomicInteger(1);
        allBillViews.forEach(o->{
            if (!o.getPaid()){
                orderBillVbox.getChildren().add(new OrderBillRecordPane(o, j.getAndIncrement()));
            }
        });
        for (int i=0;i<orderBillVbox.getChildren().size();i++){
            ((OrderBillRecordPane)orderBillVbox.getChildren().get(i)).getSelectCheckBox().setDisable(false);
        }
        allBill.setDisable(false);
    }

    @FXML
    private void pay(){
        System.out.println("老卢给二维码");
        List<Long> order_ids = new ArrayList<>();
        orderBillVbox.getChildren().forEach(o->{
            if (((OrderBillRecordPane)o).getSelectCheckBox().isSelected()){
                order_ids.add(((OrderBillRecordPane)o).getOrderId());
            }
        });
        if (order_ids.size()==0){
            AlertStage.createAlertStage("请选择账单").show();
            return;
        }
        Image fxImage = QRCodeUtil.encode2FXImage(order_ids,null,true);

        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        final String url = AllHttpComUtils.qr_pay_url;
        if (fxImage != null) {
            root.setCenter(new ImageView(fxImage));
        } else {
            root.setCenter(new TextField("error qr."));
        }
        // root.setCenter(new TextField("进入待支付"));

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
    }
    /**
     * 第四个功能
     * personal --csy实现
     */
    private void get4() {
        getPersonalFXML();
    }

    @FXML
    private void modifiedPersonalInformation() {
        setAllInvisible();
        packages_personal_scrollPane.setVisible(true);
        packages_personal_anchorPane.setVisible(true);
        packages_personal_text_again.setVisible(false);
        packages_personal_btn_modify.setVisible(true);
        packages_personal_textfiled_againPassword.setVisible(false);
        packages_personal_btn_save.setVisible(false);
        packages_personal_textfiled_account.setStyle(gray);
        packages_personal_textfiled_againPassword.setStyle(gray);
        packages_personal_textfiled_street.setStyle(gray);
        packages_personal_textfiled_city.setStyle(gray);
        packages_personal_textfiled_customerName.setStyle(gray);
        packages_personal_textfiled_customerPhone.setStyle(gray);
        packages_personal_textfiled_detailAddress.setStyle(gray);
        packages_personal_textfiled_password.setStyle(gray);


        packages_personal_textfiled_againPassword.setEditable(false);
        packages_personal_textfiled_street.setEditable(false);
        packages_personal_textfiled_account.setEditable(false);
        packages_personal_textfiled_city.setEditable(false);
        packages_personal_textfiled_customerName.setEditable(false);
        packages_personal_textfiled_customerPhone.setEditable(false);
        packages_personal_textfiled_detailAddress.setEditable(false);
        packages_personal_textfiled_password.setEditable(false);
    }




    @FXML
    private void modifiedAction() {
        packages_personal_btn_modify.setVisible(false);
        packages_personal_btn_save.setVisible(true);
        packages_personal_textfiled_againPassword.setVisible(true);
        packages_personal_text_again.setVisible(true);
        packages_personal_textfiled_againPassword.setStyle(white);
        packages_personal_textfiled_account.setStyle(white);
        packages_personal_textfiled_city.setStyle(white);
        packages_personal_textfiled_customerName.setStyle(white);
        packages_personal_textfiled_customerPhone.setStyle(white);
        packages_personal_textfiled_detailAddress.setStyle(white);
        packages_personal_textfiled_street.setStyle(white);
        packages_personal_textfiled_password.setStyle(white);

        packages_personal_textfiled_againPassword.setEditable(true);
        packages_personal_textfiled_street.setEditable(true);
        packages_personal_textfiled_account.setEditable(true);
        packages_personal_textfiled_city.setEditable(true);
        packages_personal_textfiled_customerName.setEditable(true);
        packages_personal_textfiled_customerPhone.setEditable(true);
        packages_personal_textfiled_detailAddress.setEditable(true);
        packages_personal_textfiled_password.setEditable(true);
    }

    @FXML
    private void savePersoanlInfromationAction(){
        String pass =packages_personal_textfiled_password.getText();
        String again=packages_personal_textfiled_againPassword.getText();


        if (!pass.equals(again)){
            AlertStage.createAlertStage("两次密码输入不一致，请重新输入！").show();
        }
        else {
            Customer newCus=ChangeService.userLoginController.getCustomer();
            newCus.setCity(packages_personal_textfiled_city.getText());
            newCus.setStreet(packages_personal_textfiled_street.getText());
            newCus.setDetailAddress(packages_personal_textfiled_detailAddress.getText());
            newCus.setCustomerId(Long.parseLong(packages_personal_textfiled_customerPhone.getText()));
            newCus.setCustomerPassword(pass);
            packages_personal_textfiled_customerID.setText(packages_personal_textfiled_customerPhone.getText());

            AlertStage.createAlertStage("保存成功！").show();

            packages_personal_textfiled_againPassword.setStyle(gray);
            packages_personal_textfiled_street.setStyle(gray);
            packages_personal_textfiled_city.setStyle(gray);
            packages_personal_textfiled_customerName.setStyle(gray);
            packages_personal_textfiled_customerPhone.setStyle(gray);
            packages_personal_textfiled_detailAddress.setStyle(gray);
            packages_personal_textfiled_password.setStyle(gray);
            packages_personal_textfiled_account.setStyle(gray);
            packages_personal_textfiled_againPassword.setVisible(false);
            packages_personal_btn_modify.setVisible(true);
            packages_personal_text_again.setVisible(false);


        }




    }

    @FXML
    private void uploadAction() {
        packages_personal_btn_upload.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images" ,"."),
                    new FileChooser.ExtensionFilter("JPG","*.jpg"),
                    new FileChooser.ExtensionFilter("PNG","*.png"));
            //fileChooser.setInitialDirectory(new File(System.getProperty("")));
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            File file = fileChooser.showOpenDialog(stage);
            try {
                ChangeService.userLoginController.getCustomer().setAvatar(file);

                packages_personal_avatar.setImage(new Image(new FileInputStream(file)));

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    //初始化客户的信息
    private void initPersonal() {

        Customer customer = ChangeService.userLoginController.getCustomer();
        if (customer != null) {
            packages_personal_textfiled_customerName.setText(customer.getCustomerName());
            packages_personal_textfiled_city.setText(customer.getCity());
            packages_personal_textfiled_street.setText(customer.getStreet());
            packages_personal_textfiled_detailAddress.setText(customer.getDetailAddress());
            packages_personal_textfiled_customerPhone.setText(customer.getCustomerId().toString());
            packages_personal_textfiled_account.setText(customer.getAccount());
            packages_personal_textfiled_password.setText(customer.getCustomerPassword());
            packages_personal_textfiled_againPassword.setText(customer.getCustomerPassword());
            String avatar = customer.getAvatar();
            if (avatar != null && avatar.equals("")) {
                byte[] decode = Base64.getDecoder().decode(avatar);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(decode);
                packages_personal_avatar.setImage(new Image(inputStream));
            } else {
                packages_personal_avatar.setImage(
                        new Image(PackageController.class.getResourceAsStream("/picture/头像1.jpg")));
            }
        }

    }

    /**
     * 第五个功能
     * note --sky
     */
    private void get5() {
        getNoteFXML();
    }

    private void initNote() {
        noteVbox.getChildren().clear();
    }

    @FXML
    private void queryNote() {
        setAllInvisible();
        packages_notes_scrollPane.setVisible(true);
        int color = noteVbox.getChildren().size()+1;
        System.out.println(color);

        NoteSimpleRecord noteSimpleRecord = AllHttpComUtils.getNoteSimpleRecord(ChangeService.userLoginController.getCustomerId());

        noteVbox.getChildren().add(new NoteSimpleRecordPane(noteSimpleRecord, color));
    }

    /**
     * 将所有界面调至不可见
     */


    private void setAllInvisible() {
        packages_package_scrollPane.setVisible(false);
        packages_send_scrollPane.setVisible(false);
        packages_bill_scrollPane.setVisible(false);
        packages_notes_scrollPane.setVisible(false);
        packages_personal_scrollPane.setVisible(false);
    }

    private void setAllSelectedFalse() {
        packages_send_packageType_file.setDisable(true);
        packages_send_packageType_electronic.setDisable(true);
        packages_send_packageType_dailyUsing.setDisable(true);
        packages_send_packageType_clothe.setDisable(true);
        packages_send_packageType_fresh.setDisable(true);
        packages_send_packageType_food.setDisable(true);
        packages_send_packageType_fragile.setDisable(true);
        packages_send_packageType_makeup.setDisable(true);
        packages_send_packageType_medicine.setDisable(true);
    }

    private void setAllSelectTrue() {
        packages_send_packageType_file.setDisable(false);
        packages_send_packageType_electronic.setDisable(false);
        packages_send_packageType_dailyUsing.setDisable(false);
        packages_send_packageType_clothe.setDisable(false);
        packages_send_packageType_fresh.setDisable(false);
        packages_send_packageType_food.setDisable(false);
        packages_send_packageType_fragile.setDisable(false);
        packages_send_packageType_makeup.setDisable(false);
        packages_send_packageType_medicine.setDisable(false);

    }

    /**
     * FXML控件太多了，已按顺序放后面了
     */

    // 0
    private void getBigPaneFXML() {
    }

    @FXML
    private Text user_text_welcome;

    @FXML
    private Text user_text_username;

    @FXML
    private AnchorPane packages_anchorPane;


    // 1
    private void getPackageFXML() {
    }

    @FXML
    private Button user_btn_package;

    @FXML
    private AnchorPane user_pane_package_anchorPane;

    // 滑动条
    @FXML
    private ScrollPane packages_package_scrollPane;

    // 装订单列表的容器
    @FXML
    private VBox packages_show_vBox;

    // 2
    private void getSendFXML() {
    }

    @FXML
    private AnchorPane packages_send_anchorPane;

    @FXML
    private ScrollPane packages_send_scrollPane;

    @FXML
    private Button user_btn_send;
    /*
    发送者、接受者名字与电话
     */
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

    /*
    四个城市选择的下拉栏
     */
    @FXML
    private ComboBox<Region> packages_send_consiggee_province;

    @FXML
    private ComboBox<Region> packages_send_consiggee_city;

    @FXML
    private ComboBox<Region> packages_send_shipper_province;

    @FXML
    private ComboBox<Region> packages_send_shipper_city;

    /*
    包裹类型复选框
     */
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
    private TextField packages_send_packageType_other_details;

    /*
    有关包裹特殊性的描述
     */
    @FXML
    private CheckBox packages_send_speacialPackage_international;

    @FXML
    private CheckBox packages_send_speacialPackage_dangerous;

    @FXML
    private CheckBox packages_send_speacialPackage_other;

    @FXML
    private CheckBox packages_send_speacialPackage_not;

    @FXML
    private TextField packages_send_speacialPackage_detial;

    /*
    有关包裹时间限制的描述
     */
    @FXML
    private RadioButton packages_send_serviceType_nextDay;

    @FXML
    private RadioButton packages_send_serviceType_nextNextDay;

    @FXML
    private RadioButton packages_send_serviceType_not;

    /*
    有关包裹付款方式的描述
     */
    @FXML
    private RadioButton packages_send_payment_now;

    @FXML
    private RadioButton packages_send_payment_monthly;

    /*
    提交订单
     */

    @FXML
    private Button packages_send_btn_send;

    /*
    服务协议
     */
    @FXML
    private Text protocol;

    // 3
    private void getBillFXML() {
    }

    @FXML
    private ScrollPane packages_bill_scrollPane;

    @FXML
    private AnchorPane packages_bill_anchorPane;

    @FXML
    private Button user_btn_bill;

    @FXML
    private VBox orderBillVbox;

    @FXML
    private CheckBox allBill;

    // 4
    private void getPersonalFXML() {
    }

    // 面板
    @FXML
    private ScrollPane packages_personal_scrollPane;

    @FXML
    private AnchorPane packages_personal_anchorPane;

    @FXML
    private Button user_btn_personal;
    // 控件
    @FXML
    private Button packages_personal_btn_upload;

    @FXML
    private Text packages_personal_textfiled_customerID;

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
    private ImageView packages_personal_avatar;

    @FXML
    private Text packages_personal_text_again;

    // 5
    private void getNoteFXML() {
    }

    @FXML
    private VBox noteVbox;

    @FXML
    private ScrollPane packages_notes_scrollPane;

    @FXML
    private Button user_btn_notes;
}
