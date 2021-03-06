package controller.userController;

import component.SimpleOrderMessagePane;
import component.beans.PackOrderBillInsertInfo;
import component.beans.Transport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.alert.AlertStage;
import utils.http.AllHttpComUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/25 9:46
 */
public class DetailMessageController implements Initializable {

    private PackOrderBillInsertInfo packOrderBillInsertInfo;

    private Stage transDetailStage;

    private long order_id;

    @FXML
    private TextField senderField;

    @FXML
    private TextField receiverField;

    @FXML
    private TextField senderPhoneField;

    @FXML
    private TextField receiverPhoneField;

    @FXML
    private TextField senderAddressField;

    @FXML
    private TextField receiverAddressField;

    @FXML
    private TextField orderIdField;

    @FXML
    private TextField packageTypeField;

    @FXML
    private TextField packageWeightField;

    @FXML
    private TextField packageChargeField;

    @FXML
    private TextField packageMessageField;

    @FXML
    private CheckBox dangerousCheckBox;

    @FXML
    private CheckBox interCheckBox;

    @FXML
    private Line line;
    @FXML
    private void transDetail(){
        try{
            transDetailStage = new Stage();
            transDetailStage.setResizable(false);
            transDetailStage.sizeToScene();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimpleOrderMessagePane.class.getResource("/user/OrderDetail.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            transDetailStage.setScene(scene);

            /*
             * ??????????????????????????????????????? ???????????????????????? --sky
             */
            OrderDetailController odc = loader.getController();
            odc.init(order_id,packOrderBillInsertInfo.getDeparture(),packOrderBillInsertInfo.getAddress());

            List<Transport> transportsOfOrder = AllHttpComUtils.getTransportsOfOrder(order_id);


            if (transportsOfOrder==null||transportsOfOrder.size()==0){
                AlertStage.createAlertStage("?????????????????????").show();
                return;
            }
            transportsOfOrder.forEach(odc::addNewRecord);

            if (transDetailStage.isShowing()){
                transDetailStage.close();
            }
            transDetailStage.initModality(Modality.APPLICATION_MODAL);
            transDetailStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<TextField> textFields = new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Collections.addAll(textFields,senderField,receiverField,senderPhoneField,receiverPhoneField,senderAddressField,receiverAddressField,orderIdField,packageTypeField, packageWeightField, packageChargeField, packageMessageField);
        textFields.forEach(o-> o.setEditable(false));
        textFields.forEach(o-> o.setFocusTraversable(false));
        dangerousCheckBox.setDisable(true);
        interCheckBox.setDisable(true);
    }

    public void setOrder_id(long order_id){
        this.order_id = order_id;
    }

    public void fillData(PackOrderBillInsertInfo packOrderBillInsertInfo){
        this.line.getStrokeDashArray().addAll(4d);
        this.packOrderBillInsertInfo = packOrderBillInsertInfo;
        // System.out.println("?????????");
        this.senderField.setText(packOrderBillInsertInfo.getShipperName());

        String[] adds = packOrderBillInsertInfo.getDeparture().split(";");
        StringBuilder add = new StringBuilder();
        for (String s : adds) add.append(s);
        this.senderAddressField.setText(add.toString());

        this.senderPhoneField.setText(packOrderBillInsertInfo.getShipperPhoneNumber());
        this.receiverField.setText(packOrderBillInsertInfo.getConsiggeeName());

        adds = packOrderBillInsertInfo.getAddress().split(";");
        add = new StringBuilder();
        for (String s : adds) add.append(s);
        this.receiverAddressField.setText(add.toString());

        this.receiverPhoneField.setText(packOrderBillInsertInfo.getConsiggeePhoneNumber());
        this.orderIdField.setText(""+order_id);
        this.packageTypeField.setText(packOrderBillInsertInfo.getPackType());
        this.packageWeightField.setText(""+packOrderBillInsertInfo.getPackWeight());
        this.packageChargeField.setText(""+packOrderBillInsertInfo.getCharge());
        this.packageMessageField.setText(packOrderBillInsertInfo.getDetailMess());
        this.dangerousCheckBox.setSelected(packOrderBillInsertInfo.getDangerous());
        this.interCheckBox.setSelected(packOrderBillInsertInfo.getInter());
    }
}
