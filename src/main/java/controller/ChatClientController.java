package controller;

import client.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import emoji.EmojiPicker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class ChatClientController {
    private Client client;
    @FXML
    private  Text txtChatRoom;

    @FXML
    private JFXButton btnsend;
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField txtField;
    @FXML
    private JFXButton btnSendImage;
    @FXML
    private VBox vBox;
    @FXML
    private JFXButton emojiBtn;

    private static VBox staticVbox;

    public void initialize(){
        staticVbox = vBox;
    }
    public void btnsendOnAction(ActionEvent actionEvent) {
        try {
            String message = txtField.getText();
            if (message != null) {
                appendText(message);
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

    public void appendText(String message) {
//        me
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:  #55efc4;-fx-background-radius:20px 0 20px 20px;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        vBox.getChildren().add(hBox);
    }

    public void txtFieldOnAction(ActionEvent actionEvent) {
        btnsendOnAction(actionEvent);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void writeMessage(String message) {
        if (message.startsWith("SERVER")){
            HBox hBox = new HBox();
            hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
            Label messageLbl = new Label(message);
            messageLbl.setStyle("-fx-background-color:  #D980FA;-fx-background-radius:0 20px 20px 20px;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
            hBox.getChildren().add(messageLbl);
            Platform.runLater(() -> vBox.getChildren().add(hBox));

        }else{
            HBox hBox = new HBox();
            hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
            Label messageLbl = new Label(message);
            messageLbl.setStyle("-fx-background-color:  #74b9ff;-fx-background-radius:0 20px 20px 20px;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
            hBox.getChildren().add(messageLbl);
            Platform.runLater(() -> vBox.getChildren().add(hBox));
        }

    }

    public void setImage(byte[] bytes, String sender) {
            HBox hBoxName = new HBox();
            Label messageLbl = new Label(sender);
            messageLbl.setStyle("-fx-background-color: #74b9ff; -fx-background-radius: 15; -fx-font-size: 18; -fx-font-weight: normal; -fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10; -fx-max-width: 350;");
             hBoxName.setAlignment(Pos.CENTER_LEFT);
            hBoxName.getChildren().add(messageLbl);
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5,5,5,10));
            hBox.getChildren().add(imageView);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hBoxName);
                    vBox.getChildren().add(hBox);
                }
            });

    }

    public void btnSendImageOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                HBox hBox = new HBox();
                hBox.setStyle("-fx-fill-height: true;  -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520;-fx-padding: 10; -fx-alignment: center-right;");


                ImageView imageView = new ImageView(new Image(new FileInputStream(selectedFile)));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(200);
                imageView.setFitWidth(200);

                hBox.getChildren().addAll(imageView);
                vBox.getChildren().add(hBox);

                client.sendImage(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void emoji() {
        // Create the EmojiPicker
        EmojiPicker emojiPicker = new EmojiPicker();

        VBox vBox = new VBox(emojiPicker);
        vBox.setPrefSize(150, 300);
        vBox.setLayoutX(275);
        vBox.setLayoutY(345);
        vBox.setStyle("-fx-font-size: 30");

        pane.getChildren().add(vBox);

        // Set the emoji picker as hidden initially
        emojiPicker.setVisible(false);

        // Show the emoji picker when the button is clicked
        emojiBtn.setOnAction(event -> {
            if (emojiPicker.isVisible()) {
                emojiPicker.setVisible(false);
            } else {
                emojiPicker.setVisible(true);
            }
        });

        emojiPicker.getEmojiListView().setOnMouseClicked(event -> {
            String selectedEmoji = emojiPicker.getEmojiListView().getSelectionModel().getSelectedItem();
            if (selectedEmoji != null) {
                txtField.setText(txtField.getText()+selectedEmoji);
            }
            emojiPicker.setVisible(false);
        });
    }

    public void emojiBtnOnAction(ActionEvent actionEvent) {
        emoji();
    }
}
