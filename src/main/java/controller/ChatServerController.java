package controller;

import Server.ClientHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatServerController{
    @FXML
    private JFXButton btnsend;

    @FXML
    private JFXTextField txtField;

    @FXML
    private JFXTextArea txtArea;

    @FXML
    private JFXButton btnAddClient;

    @FXML
    void btnAddClientOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addClient.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add New Client");
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
    }

    @FXML
    void btnsendOnAction(ActionEvent event) throws IOException {
        String message = txtField.getText();
        if (!message.isEmpty()|| message.equals(null)){
            sendMessage(message);
        }else{
            ButtonType ok = new ButtonType("Ok");
            ButtonType cancel = new ButtonType("Cancel");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Empty message. Is it ok?", ok, cancel);
            alert.showAndWait();
            ButtonType result = alert.getResult();
            if (result.equals(ok)){
                sendMessage(message);
            }
        }

    }

    private void sendMessage(String message) throws IOException {
        ClientHandler.broadcastMessage(message);
        txtArea.appendText(message+"\n");
        txtField.clear();
    }
    @FXML
    void txtFieldOnAction(ActionEvent event) throws IOException {
        btnsendOnAction(event);
    }




}

