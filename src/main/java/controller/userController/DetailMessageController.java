package controller.userController;

import component.SimpleOrderMessagePane;
import component.beans.PackOrderBillInsertInfo;
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
import utils.http.AllHttpComUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimpleOrderMessagePane.class.getResource("/user/OrderDetail.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            transDetailStage.setScene(scene);

            /*
             * 这里需要发送获取数据的请求 获取之后生成界面 --sky
             */
            OrderDetailController odc = loader.getController();
            odc.init(order_id,packOrderBillInsertInfo.getDeparture(),packOrderBillInsertInfo.getAddress());

            AllHttpComUtils
                    .getTransportsOfOrder(order_id)
                    .forEach(odc::addNewRecord);

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
        dangerousCheckBox.setDisable(true);
        interCheckBox.setDisable(true);
    }

    public void setOrder_id(long order_id){
        this.order_id = order_id;
    }

    public void fillData(PackOrderBillInsertInfo packOrderBillInsertInfo){
        this.line.getStrokeDashArray().addAll(4d);
        this.packOrderBillInsertInfo = packOrderBillInsertInfo;
        System.out.println("闯进来了");
        this.senderField.setText(packOrderBillInsertInfo.getsName());
        this.senderAddressField.setText(packOrderBillInsertInfo.getDeparture());
        this.senderPhoneField.setText(packOrderBillInsertInfo.getsPhoneNumber());
        this.receiverField.setText(packOrderBillInsertInfo.getcName());
        this.receiverAddressField.setText(packOrderBillInsertInfo.getAddress());
        this.receiverPhoneField.setText(packOrderBillInsertInfo.getcPhoneNumber());
        this.orderIdField.setText(""+order_id);
        this.packageTypeField.setText(packOrderBillInsertInfo.getPackType());
        this.packageWeightField.setText(""+packOrderBillInsertInfo.getPackWeight());
        this.packageChargeField.setText(""+packOrderBillInsertInfo.getCharge());
        this.packageMessageField.setText(packOrderBillInsertInfo.getDetailMess());
        this.dangerousCheckBox.setSelected(packOrderBillInsertInfo.isDangerous());
        this.interCheckBox.setSelected(packOrderBillInsertInfo.isInter());
    }
}
