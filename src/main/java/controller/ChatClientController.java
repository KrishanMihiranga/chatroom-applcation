package controller;

import client.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChatClientController {
    private Client client;
    @FXML
    private  Text txtChatRoom;

    @FXML
    private JFXButton btnsend;
    @FXML
    private AnchorPane Pane;
    @FXML
    private JFXTextField txtField;

    @FXML
    private JFXTextArea txtArea;

    public void btnsendOnAction(ActionEvent actionEvent) {
        try {
            String message = txtField.getText();
            if (message != null) {
                txtArea.appendText(message+"\n");
                client.sendMessage(message);
            } else {
                ButtonType ok = new ButtonType("Ok");
                ButtonType cancel = new ButtonType("Cancel");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Empty message. Is it ok?", ok, cancel);
                alert.showAndWait();
                ButtonType result = alert.getResult();
                if (result.equals(ok)) {
                    client.sendMessage(null);
                }
                txtField.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public void txtFieldOnAction(ActionEvent actionEvent) {
        btnsendOnAction(actionEvent);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void writeMessage(String message) {
       txtArea.appendText(message+"\n");
    }

}
