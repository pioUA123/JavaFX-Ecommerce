package com.example.oopii_finalproject.UI.Frames;

import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.UI.Controllers.UserProfileController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserProfileFrame {

    private final Stage ownerStage;
    private final User loggedInUser;
    private final UserProfileController userProfileController;

    private Stage profileStage;
    private Label balanceLabel;

    public UserProfileFrame(Stage ownerStage, User loggedInUser) {
        this.ownerStage = ownerStage;
        this.loggedInUser = loggedInUser;
        this.userProfileController = new UserProfileController(this, loggedInUser);
    }

    public void show() {
        profileStage = new Stage();
        profileStage.initOwner(ownerStage);
        profileStage.initModality(Modality.WINDOW_MODAL);
        profileStage.setTitle("User Profile");

        Label titleLabel = new Label("Profile");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextField nameField = new TextField(loggedInUser.getUsername());
        TextField emailField = new TextField(loggedInUser.getEmail());
        PasswordField passwordField = new PasswordField();
        passwordField.setText(loggedInUser.getPassword());

        balanceLabel = new Label("Balance: $" + loggedInUser.getBalance());
        balanceLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #009879;");

        TextField balanceField = new TextField();
        balanceField.setPromptText("Add Amount");

        TextField promoField = new TextField();
        promoField.setPromptText("Promo code");

        Button addBalanceBtn = new Button("Add Balance");
        addBalanceBtn.setStyle("-fx-background-color: #009879; -fx-text-fill: white; -fx-background-radius: 8;");
        addBalanceBtn.setOnAction(e ->
                userProfileController.addBalance(balanceField.getText())
        );

        Button applyPromoBtn = new Button("Apply Promo");
        applyPromoBtn.setStyle("-fx-background-color: #009879; -fx-text-fill: white; -fx-background-radius: 8;");
        applyPromoBtn.setOnAction(e ->
                userProfileController.applyPromo(promoField.getText())
        );

        Button saveBtn = new Button("Save Changes");
        saveBtn.setStyle("-fx-background-color: #009879; -fx-text-fill: white; -fx-background-radius: 8;");
        saveBtn.setOnAction(e ->
                userProfileController.updateUser(nameField.getText(), emailField.getText(), passwordField.getText())
        );

        VBox box = new VBox(15, titleLabel, nameField, emailField, passwordField, saveBtn, balanceLabel, balanceField, addBalanceBtn, promoField, applyPromoBtn);

        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle("-fx-background-color: white; -fx-background-radius: 12;");

        StackPane root = new StackPane(box);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #00987922;");

        Scene scene = new Scene(root, 400, 500);
        profileStage.setScene(scene);
        profileStage.setResizable(false);
        profileStage.showAndWait();
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    public void updateBalanceLabel(double newBalance) {
        balanceLabel.setText("Balance: $" + newBalance);
    }
}
