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
import org.apache.http.HttpException;
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
    private String white="-fx-background-color: rgb(255,255,255)";

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
    * ?????????????????????
    * */
    static {
        try {
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
     * ?????????????????????
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        setAllInvisible();
        packages_package_scrollPane.setVisible(true);
        queryPackageInformation();
        user_text_username.setText(UserLoginController.getCustomer().getCustomerName());
        /*
         * ???????????????????????????????????????
         */
        initBigPane();
        initPackage();
        initSend();
        initBill();
        initPersonal();
        initNote();
    }

    /*
    ???????????????????????????????????? --sky
     */

    /*
    ?????????client????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */

    /**
     * ???????????????
     * ?????????????????????????????????
     */
    private void get0() {
        getBigPaneFXML();
    }

    private void initBigPane() {
        packages_personal_textfiled_customerID.setText(""+ UserLoginController.getCustomer().getCustomerId());


    }

    /**
     * ???????????????
     * package???????????????????????????????????????????????? --sky??????
     */
    private void get1() {
        getPackageFXML();
    }

    private int offset = 0;
    //private int lastCount = 0;
    private final int size  = 8;
    // ?????????????????????????????????
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
        ????????????????????????????????????customerId?????????????????????????????????
         */
        loadPackage(ChangeService.userLoginController.getCustomerId(), offset, size);
    }

    // ??????????????????
    private void loadPackage(long customerId, int offset, int limit) {
        final ArrayList<SimpleOrderMessagePane> results = new ArrayList<>();
        setAllInvisible();
        packages_package_scrollPane.setVisible(true);
        List<SimpleOrderInfoBar> simpleOrderInfoBarPage = AllHttpComUtils.getSimpleOrderInfoBarPage(customerId, offset, limit);
        if (simpleOrderInfoBarPage == null) return;
        AtomicInteger i = new AtomicInteger(1);
        simpleOrderInfoBarPage.forEach((s) -> results.add(new SimpleOrderMessagePane(s, i.getAndIncrement())));
        /*
        ????????????????????????????????????????????????????????????????????????
        */
        this.offset+=results.size();
        for (SimpleOrderMessagePane s : results) {
            packages_show_vBox.getChildren().add(s);
        }
    }

    // ??????????????????Scroll?????????????????????????????????
    private void addAction2Scroll() {
        /*???????????????????????????anchor??? --sky*/
        this.packages_package_scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (packages_package_scrollPane.getVvalue() == 1.0) {
                    System.out.println("???????????????????????????");
                    // 18899????????????????????????
                    loadPackage(ChangeService.userLoginController.getCustomerId(), offset, size);
                }
            }
        });
    }

    private void initPackage() {
        addAction2Scroll();
    }

    /**
     * ???????????????
     * send --csy??????
     */
    private void get2() {
        getSendFXML();
    }
    /* send ?????????????????? */

    // ??????????????????
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

    // ?????????????????????
    private void addClickedAction2Protocol() {
        protocol.setOnMouseClicked(event -> AlertStage.createAlertStage("????????????????????????????????????").show());
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


    //????????????
    @FXML
    private void addClickedActionSend() {
        if (!agree.isSelected()){
            AlertStage.createAlertStage("??????????????????????????????").show();
            return;
        }
        PackOrderBillInsertInfo packOrderBillInsertInfo = new PackOrderBillInsertInfo();
        // ???????????????
        {
            String trim = packages_send_shipper_name.getText().trim();
            if (AlertStage.checkNotNullInput("????????????????????????", trim)) return;
            packOrderBillInsertInfo.setShipperName(trim);
        }
        // ???????????????
        {
            String trim = packages_send_shipper_phone.getText().trim();
            if (AlertStage.checkNotNullInput("????????????????????????", trim)) return;
            packOrderBillInsertInfo.setShipperPhoneNumber(trim);
        }
        // ???????????????
        try {
            String prov = packages_send_shipper_province.getValue().toString();
            String city = packages_send_shipper_city.getValue().toString();
            String ds = packages_send_shipper_detailAddress.getText().trim();
            if (AlertStage.checkNotNullInput("??????????????????????????????", ds)) return;
            packOrderBillInsertInfo.setDeparture(prov + ';' + city + ";" + ds);
        } catch (NullPointerException nullPointerException) {
            AlertStage.createAlertStage("????????????????????????").show();
            return;
        }
        // ???????????????
        {
            String trim = packages_send_consiggee_name.getText().trim();
            if (AlertStage.checkNotNullInput("????????????????????????", trim)) return;
            packOrderBillInsertInfo.setConsiggeeName(trim);
        }
        // ???????????????
        {
            String trim = packages_send_consiggee_phone.getText().trim();
            if (AlertStage.checkNotNullInput("????????????????????????", trim)) return;
            packOrderBillInsertInfo.setConsiggeePhoneNumber(trim);
        }
        // ???????????????
        try {
            String prov = packages_send_consiggee_province.getValue().toString();
            String city = packages_send_consiggee_city.getValue().toString();
            String ds = packages_send_consiggee_detailAddress.getText().trim();
            if (AlertStage.checkNotNullInput("??????????????????????????????", ds)) return;
            packOrderBillInsertInfo.setAddress(prov + ';' + city + ";" + ds);
        } catch (NullPointerException nullPointerException) {
            AlertStage.createAlertStage("????????????????????????").show();
            return;
        }
        // ??????????????????
        {
            int days = packages_send_serviceType_nextDay.isSelected() ? 1 : (packages_send_serviceType_nextNextDay.isSelected() ? 2 : 3);
            Timestamp timestamp = new Timestamp(new Date().getTime() + days * 24 * 60 * 60 * 1000);
            packOrderBillInsertInfo.setCommitArriveTime(timestamp);
            if (packages_send_serviceType_nextDay.isSelected()) {
                packOrderBillInsertInfo.setPackType("?????????");
            } else if (packages_send_serviceType_nextNextDay.isSelected()) {
                packOrderBillInsertInfo.setPackType("?????????");
            } else {
                packOrderBillInsertInfo.setPackType("?????????????????????");
            }
        }
        //package info
        //???????????????????????????
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
        //??????
        {
            if (packages_send_consiggee_province.getValue().toString().equals("?????????")) {
                packOrderBillInsertInfo.setCharge(8);
            } else {
                packOrderBillInsertInfo.setCharge(12);
            }
        }
        //id
        packOrderBillInsertInfo.setCustomerId(ChangeService.userLoginController.getCustomerId());///????????????

        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        // ?????????
        if (packages_send_payment_now.isSelected()) {
            packOrderBillInsertInfo.setPayType("?????????");
            final String url = AllHttpComUtils.qr_pay_url;
            long billId = AllHttpComUtils.createP_O_BInsertInfo(packOrderBillInsertInfo);
            if (billId == 0) {
                AlertStage.createAlertStage("??????????????????").show();
                return;
            }
            Image fxImage = QRCodeUtil.encode2FXImage(
                            url + "?id=" + billId,
                            null,
                            true);
            if (fxImage != null) {
                root.setCenter(new ImageView(fxImage));
            } else {
                AlertStage.createAlertStage("?????????????????????").show();
                return;
            }
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.sizeToScene();
        }
        else if (packages_send_payment_monthly.isSelected()) {
            packOrderBillInsertInfo.setPayType("?????????");
            if (AllHttpComUtils.createP_O_BInsertInfo(packOrderBillInsertInfo) == 0) {
                AlertStage.createAlertStage("??????????????????").show();
                return;
            }

            AlertStage.createAlertStage("??????????????????").show();
            root.setCenter(new TextField("??????????????????"));
        }

    }

    // ????????????
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
            * ????????????~
            * */
            packages_send_serviceType_nextDay.setSelected(true);
            packages_send_payment_now.setSelected(true);
            packages_send_speacialPackage_not.setSelected(true);
        }
        ToggleGroup group = new ToggleGroup(); // ????????????????????????
        packages_send_serviceType_nextDay.setToggleGroup(group); // ???????????????1?????????????????????
        packages_send_serviceType_nextNextDay.setToggleGroup(group); // ???????????????2?????????????????????
        packages_send_serviceType_not.setToggleGroup(group); // ???????????????3?????????????????????

        ToggleGroup group1 = new ToggleGroup(); // ????????????????????????
        packages_send_payment_monthly.setToggleGroup(group1); // ???????????????1?????????????????????
        packages_send_payment_now.setToggleGroup(group1); // ???????????????2?????????????????????


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
                if (e.isSelected()) { //?????????????????????
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
     * ???????????????
     * bill --sky??????
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
    // ?????????????????????
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

    // ??????????????????
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
        System.out.println("????????????");
        allBillViews = AllHttpComUtils.getAllBills(ChangeService.userLoginController.getCustomerId());
        if(allBillViews!=null){
            queryAllBill();
        }
        BillOfLastMonth billOfLastMonth = AllHttpComUtils.getBillOfLastMonth(ChangeService.userLoginController.getCustomerId());
        System.out.println(billOfLastMonth);
        int ?????????????????? = billOfLastMonth.getSendPacksCount();
        double ?????????????????? = billOfLastMonth.getMoneyNumber();
        double ?????????????????? = billOfLastMonth.getLastMonthArrears();
        System.out.println("??????????????????" + ??????????????????);
        billMessage1.setText("??????????????????:"+??????????????????);
        billMessage2.setText("??????????????????:"+??????????????????);
        billMessage3.setText("??????????????????:"+??????????????????);

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
        System.out.println("??????????????????");
        List<Long> order_ids = new ArrayList<>();
        orderBillVbox.getChildren().forEach(o->{
            if (((OrderBillRecordPane)o).getSelectCheckBox().isSelected()){
                order_ids.add(((OrderBillRecordPane)o).getOrderId());
            }
        });
        if (order_ids.size()==0){
            AlertStage.createAlertStage("???????????????").show();
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
        // root.setCenter(new TextField("???????????????"));

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.sizeToScene();
    }
    /**
     * ???????????????
     * personal --csy??????
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

        modifyPersonalButtonStyleChange(gray);
        modifyPersonalButtonSetEditable(false);
    }

    @FXML
    private void modifiedAction() {
        packages_personal_btn_modify.setVisible(false);
        packages_personal_btn_save.setVisible(true);
        packages_personal_textfiled_againPassword.setVisible(true);
        packages_personal_text_again.setVisible(true);

        modifyPersonalButtonStyleChange(white);
        modifyPersonalButtonSetEditable(true);
    }

    @FXML
    private void savePersonalInformationAction(){
        String pass =packages_personal_textfiled_password.getText();
        String again=packages_personal_textfiled_againPassword.getText();

        if (AlertStage.checkNotNullInput("????????????", pass, again)) return;

        if (!pass.equals(again)){
            AlertStage.createAlertStage("????????????????????????????????????????????????").show();
        }
        else {
            Customer newCus= UserLoginController.getCustomer();
            System.out.println(newCus);
            newCus.setCity(packages_personal_textfiled_city.getText());
            newCus.setStreet(packages_personal_textfiled_street.getText());
            newCus.setDetailAddress(packages_personal_textfiled_detailAddress.getText());
            newCus.setCustomerId(Long.parseLong(packages_personal_textfiled_customerPhone.getText()));
            newCus.setCustomerPassword(pass);
            try {
                AllHttpComUtils.updateCustomer(newCus);
            } catch (HttpException e) {
                AlertStage.createAlertStage(e.getMessage()).show();
                return;
            }
            packages_personal_textfiled_customerID.setText(packages_personal_textfiled_customerPhone.getText());

            AlertStage.createAlertStage("???????????????").show();

            modifyPersonalButtonStyleChange(gray);
            packages_personal_btn_save.setVisible(false);
            packages_personal_textfiled_againPassword.setVisible(false);
            packages_personal_btn_modify.setVisible(true);
            packages_personal_text_again.setVisible(false);
        }
    }

    /*
    * ???????????????????????????
    * */
    private void modifyPersonalButtonSetEditable(boolean editable) {
        packages_personal_textfiled_againPassword.setEditable(editable);
        packages_personal_textfiled_street.setEditable(editable);
        packages_personal_textfiled_account.setEditable(editable);
        packages_personal_textfiled_city.setEditable(editable);
        packages_personal_textfiled_customerName.setEditable(editable);
        packages_personal_textfiled_customerPhone.setEditable(editable);
        packages_personal_textfiled_detailAddress.setEditable(editable);
        packages_personal_textfiled_password.setEditable(editable);
    }

    /*
    * ???????????????????????????
    * */
    private void modifyPersonalButtonStyleChange(String color) {
        packages_personal_textfiled_password.setStyle(color);
        packages_personal_textfiled_account.setStyle(color);
        packages_personal_textfiled_street.setStyle(color);
        packages_personal_textfiled_againPassword.setStyle(color);
        packages_personal_textfiled_city.setStyle(color);
        packages_personal_textfiled_customerName.setStyle(color);
        packages_personal_textfiled_customerPhone.setStyle(color);
        packages_personal_textfiled_detailAddress.setStyle(color);
    }

    @FXML
    private void uploadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images" ,"."),
                new FileChooser.ExtensionFilter("JPG","*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) {
            return;
        }

        if(file.length() > 2*1024*1024) {
            AlertStage.createAlertStage("??????????????????2M").show();
        }
        else if (file.length() <= 2*1024*1024 && file.length() > 0) {
            try {
                UserLoginController.getCustomer().setAvatar(file);
                packages_personal_avatar.setImage(new Image(new FileInputStream(file)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AlertStage.createAlertStage("file length 0" + file).show();
        }
    }

    //????????????????????????
    private void initPersonal() {
        Customer customer = UserLoginController.getCustomer();
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
            if (avatar != null && !avatar.equals("")) {
                // ?????????????????????????????????????????????
                byte[] decode = Base64.getDecoder().decode(avatar);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(decode);
                packages_personal_avatar.setImage(new Image(inputStream));
            } else {
                // ????????????
                InputStream resourceAsStream = PackageController.class.getResourceAsStream("/picture/??????1.jpg");
                if (resourceAsStream == null ) {
                    System.out.println("no user avatar");
                    return;
                }
                packages_personal_avatar.setImage(new Image(resourceAsStream));
                try {
                    // ????????????????????????
                    UserLoginController.getCustomer().setAvatar(resourceAsStream);
                } catch (Exception e) {
                    System.err.println("???????????????????????????");
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * ???????????????
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
        noteVbox.getChildren().clear();
        packages_notes_scrollPane.setVisible(true);
        AtomicInteger color = /*noteVbox.getChildren().size()+*/new AtomicInteger(1);
        System.out.println(color.get());
        List<NoteSimpleRecord> noteSimpleRecord =
                AllHttpComUtils.getNoteSimpleRecord(ChangeService.userLoginController.getCustomerId());
        if (noteSimpleRecord == null) {
            AlertStage.createAlertStage("???????????????????????????????????????").show();
            return;
        }
        noteSimpleRecord
                .forEach(n->noteVbox
                        .getChildren()
                        .add(new NoteSimpleRecordPane(n, color.getAndIncrement())
                        )
                );
    }

    /**
     * ??????????????????????????????
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
     * FXML??????????????????????????????????????????
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

    // ?????????
    @FXML
    private ScrollPane packages_package_scrollPane;

    // ????????????????????????
    @FXML
    private VBox packages_show_vBox;

    // 2
    private void getSendFXML() {
    }

    @FXML
    private RadioButton agree;

    @FXML
    private AnchorPane packages_send_anchorPane;

    @FXML
    private ScrollPane packages_send_scrollPane;

    @FXML
    private Button user_btn_send;
    /*
    ????????????????????????????????????
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
    ??????????????????????????????
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
    ?????????????????????
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
    ??????????????????????????????
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
    ?????????????????????????????????
     */
    @FXML
    private RadioButton packages_send_serviceType_nextDay;

    @FXML
    private RadioButton packages_send_serviceType_nextNextDay;

    @FXML
    private RadioButton packages_send_serviceType_not;

    /*
    ?????????????????????????????????
     */
    @FXML
    private RadioButton packages_send_payment_now;

    @FXML
    private RadioButton packages_send_payment_monthly;

    /*
    ????????????
     */

    @FXML
    private Button packages_send_btn_send;

    /*
    ????????????
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

    // ??????
    @FXML
    private ScrollPane packages_personal_scrollPane;

    @FXML
    private AnchorPane packages_personal_anchorPane;

    @FXML
    private Button user_btn_personal;
    // ??????
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
