package controller.adminController;

import component.beans.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Sky
 * @Date: 2021/4/22 20:10
 */
public class UserDetailController implements Initializable {



    @FXML
    private Button modify;

    @FXML
    private Label authorization;

    @FXML
    private TextField customerName;

    @FXML
    private Text customerId;

    @FXML
    private TextField city;

    @FXML
    private TextField street;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;
    @FXML
    private void modifyFun(){
        if (modify.getText().equals("允许登录")){
            authorization.setText("允许登录");
            modify.setText("禁止登录");
        }else {
            authorization.setText("禁止登录");
            modify.setText("允许登录");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modify.setText("允许登录");
        authorization.setText("禁止登录");
    }

    public void init(Customer customer){
        customerName.setText(customer.getCustomerName());
        customerId.setText(customer.getCustomerId().toString());
        city.setText(customer.getCity());
        street.setText(customer.getStreet());
        address.setText(customer.getDetailAddress());
        phone.setText(customer.getCustomerId().toString());
    }


}
