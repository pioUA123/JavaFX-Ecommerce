package com.example.oopii_finalproject.UI.Frames;

import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.UI.Controllers.LoginController;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.AdminDashboardFrame;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.CustomerDashboardFrame;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginFrame {

    private final Stage stage;
    private final LoginController loginController;

    public LoginFrame(Stage stage) {
        this.stage = stage;
        this.loginController = new LoginController(this);
    }

    public void show() {

        stage.setMaximized(false);

        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setMaxWidth(260);
        emailField.setStyle("-fx-background-color: white; -fx-border-color: #d9d9d9; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setMaxWidth(260);
        passwordField.setStyle("-fx-background-color: white; -fx-border-color: #d9d9d9; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;");

        Button guestLink = new Button("Continue as guest?");

        guestLink.setOnAction(e -> {
            loginController.loginAsGuest();
        });

        guestLink.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0; -fx-border-width: 0; -fx-text-fill: #007bff; -fx-cursor: hand;");

        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(260);
        loginButton.setStyle("-fx-background-color: #009879; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 0; -fx-background-radius: 8; -fx-cursor: hand;");

        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            String message = loginController.login(email, password);
            User loggedinUser;
            messageLabel.setText(message);
        });

        Label noAccountLabel = new Label("Don't have an account?");
        noAccountLabel.setStyle("-fx-text-fill: #333333;");

        Button signupButton = new Button("Signup");
        signupButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 0; -fx-border-width: 0; -fx-text-fill: #007bff; -fx-cursor: hand;");
        signupButton.setOnAction(e -> loginController.openSignupFrame());

        HBox signupRow = new HBox(5, noAccountLabel, signupButton);
        signupRow.setAlignment(Pos.CENTER);

        messageLabel.setStyle("-fx-text-fill: red;");

        VBox card = new VBox(15, titleLabel, emailField, passwordField, guestLink, loginButton, signupRow, messageLabel);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 12;");

        VBox layout = new VBox(card);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #00987922;");

        Scene scene = new Scene(layout, 400, 400);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("E-Commerce Login");
        stage.show();
    }

    public void displayMessage(String message) {
        new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK).showAndWait();
    }

    public void openCustomerDashboard(User loggedinUser) {
        new CustomerDashboardFrame(stage, loggedinUser).show();
    }

    public void openAdminDashboard() {
        new AdminDashboardFrame(stage).show();
    }

    public void openSignupFrame() {
        new SignupFrame(stage).show();
    }
}
