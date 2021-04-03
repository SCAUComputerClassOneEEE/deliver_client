package controller.userController;

import component.SimpleOrderMessagePane;
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

import java.net.URL;
import java.util.ResourceBundle;

public class PackageController implements Initializable {
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
        setVisibleFalse();
        packages_package_scrollPane.setVisible(true);
        packages_show_vBox.getChildren().add(new SimpleOrderMessagePane());
        /*
        这里需要提供描述订单的类的数据数组，然后循环添加
        packages_show_vBox.getChildren().clear();
        for (int i=0;i<5;i++){
            packages_show_vBox.getChildren().add(e);
        }*/
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

    }

    @FXML
    private void systemNotification(){
        setVisibleFalse();;
        packages_notes_anchorPane.setVisible(true);
        packages_notes_anchorPane.setVisible(true);

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
        this.packages_package_anchorPane.getChildren().add(packages_show_vBox);
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
