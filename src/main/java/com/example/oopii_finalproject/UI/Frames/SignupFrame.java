package com.example.oopii_finalproject.UI.Frames;

import com.example.oopii_finalproject.UI.Controllers.SignupController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupFrame {

    private final Stage stage;
    private final SignupController signupController;

    public SignupFrame(Stage stage) {
        this.stage = stage;
        this.signupController = new SignupController(this);
    }

    public void show() {
        Label titleLabel = new Label("Create an Account");
        titleLabel.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #333333;"
        );

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(260);
        usernameField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 10;"
        );

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(260);
        emailField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 10;"
        );

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(260);
        passwordField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 10;"
        );

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(260);
        confirmPasswordField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 10;"
        );

        Button signupButton = new Button("Sign Up");
        signupButton.setMaxWidth(260);
        signupButton.setStyle(
                "-fx-background-color: #009879;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 0;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"
        );

        signupButton.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            String msg = signupController.handleSignup(username, email, password, confirmPassword);
            messageLabel.setText(msg);
        });

        Label alreadyLabel = new Label("Already have an account?");
        alreadyLabel.setStyle("-fx-text-fill: #333333;");

        Button backToLoginButton = new Button("Login");
        backToLoginButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-width: 0;" +
                        "-fx-text-fill: #007bff;" +
                        "-fx-underline: true;" +
                        "-fx-cursor: hand;"
        );
        backToLoginButton.setOnAction(e -> signupController.openLoginFrame());

        HBox loginRow = new HBox(5, alreadyLabel, backToLoginButton);
        loginRow.setAlignment(Pos.CENTER);

        VBox card = new VBox(15,
                titleLabel,
                usernameField,
                emailField,
                passwordField,
                confirmPasswordField,
                signupButton,
                loginRow,
                messageLabel
        );
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-padding: 30;" +
                        "-fx-background-radius: 12;"
        );

        VBox layout = new VBox(card);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #00987922;");

        Scene scene = new Scene(layout, 450, 420);

        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }

    public void goToLogin() {
        new LoginFrame(stage).show();
    }
}
