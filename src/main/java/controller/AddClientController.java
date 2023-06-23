package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddClientController implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private JFXTextField txtField;

    @FXML
    private JFXButton btnJoin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), image);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.play();
    }
    @FXML
    void btnJoinOnAction(ActionEvent event) {

    }

    @FXML
    void txtFieldOnAction(ActionEvent event) {

    }


}
