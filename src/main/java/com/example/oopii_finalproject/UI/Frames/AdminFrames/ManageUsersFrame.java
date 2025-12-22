package com.example.oopii_finalproject.UI.Frames.AdminFrames;

import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.UI.Controllers.AdminControllers.ManageUsersController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ManageUsersFrame {

    private final Stage ownerStage;
    private final ManageUsersController manageUsersController;

    private Stage manageStage;
    private VBox usersBox;

    public ManageUsersFrame(Stage ownerStage) {
        this.ownerStage = ownerStage;
        this.manageUsersController = new ManageUsersController(this);
    }

    public void show() {
        manageStage = new Stage();
        manageStage.initOwner(ownerStage);
        manageStage.initModality(Modality.WINDOW_MODAL);
        manageStage.setTitle("User Management");

        Label titleLabel = new Label("User Management");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Button addUserBtn = new Button("Add User");
        addUserBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 12;" +
                        "-fx-cursor: hand;"
        );

        HBox topRow = new HBox(10, addUserBtn);
        topRow.setAlignment(Pos.CENTER_LEFT);

        usersBox = new VBox(10);
        usersBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(usersBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox card = new VBox(15, titleLabel, topRow, scrollPane);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 12;"
        );

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #2c3e5088;");

        Scene scene = new Scene(root, 700, 500);
        manageStage.setScene(scene);
        manageStage.setResizable(false);

        addUserBtn.setOnAction(e -> showAddUserPopup());

        manageUsersController.loadUsers();

        manageStage.showAndWait();
    }

    public void displayUsers(ArrayList<User> users) {
        usersBox.getChildren().clear();

        if (users.isEmpty()) {
            Label empty = new Label("No users found.");
            empty.setStyle("-fx-text-fill: #555555;");
            usersBox.getChildren().add(empty);
            return;
        }

        for (User user : users) {
            if(user.getUserId() == 1)
                continue;
            usersBox.getChildren().add(createUserRow(user));
        }
    }

    private HBox createUserRow(User user) {
        Label nameLabel = new Label(user.getUsername());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label emailLabel = new Label(user.getEmail());
        emailLabel.setStyle("-fx-text-fill: #555555;");

        Label roleLabel = new Label(user.getUserRole());
        roleLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        Label statusLabel = new Label(user.isActive() ? "Active" : "Inactive");
        statusLabel.setStyle(
                user.isActive()
                        ? "-fx-text-fill: #27ae60; -fx-font-weight: bold;"
                        : "-fx-text-fill: #c0392b; -fx-font-weight: bold;"
        );

        VBox infoBox = new VBox(3, nameLabel, emailLabel, roleLabel, statusLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        Button editBtn = new Button("Edit");
        editBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        Button activeBtn = new Button(user.isActive() ? "Deactivate" : "Activate");
        activeBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #2c3e50;" +
                        "-fx-border-color: #2c3e50;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        Button deleteBtn = new Button("Delete");
        deleteBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #c0392b;" +
                        "-fx-border-width: 0;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        HBox buttonsBox = new HBox(8, editBtn, activeBtn, deleteBtn);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        HBox row = new HBox(20, infoBox, buttonsBox);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10));
        row.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 10;"
        );

        HBox.setHgrow(infoBox, Priority.ALWAYS);

        editBtn.setOnAction(e -> showEditUserPopup(user));

        activeBtn.setOnAction(e -> {
            String msg = manageUsersController.toggleActive(user);
            showInfo(msg);
            // update status label and button text
            statusLabel.setText(user.isActive() ? "Active" : "Inactive");
            statusLabel.setStyle(
                    user.isActive()
                            ? "-fx-text-fill: #27ae60; -fx-font-weight: bold;"
                            : "-fx-text-fill: #c0392b; -fx-font-weight: bold;"
            );
            activeBtn.setText(user.isActive() ? "Deactivate" : "Activate");
        });

        deleteBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this user?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(btn -> {
                if (btn == ButtonType.YES) {
                    String msg = manageUsersController.deleteUser(user.getUserId());
                    showInfo(msg);
                    manageUsersController.loadUsers();
                }
            });
        });

        return row;
    }

    private void showAddUserPopup() {
        Stage popup = new Stage();
        popup.initOwner(manageStage);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Add User");

        Label titleLabel = new Label("Add New User");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #c0392b;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Confirm Password");

        Button saveBtn = new Button("Save");
        saveBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 12;" +
                        "-fx-cursor: hand;"
        );

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> popup.close());

        HBox buttons = new HBox(10, saveBtn, cancelBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        saveBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirm = confirmField.getText();

            String msg = manageUsersController.addUser(username, email, password, confirm);
            messageLabel.setText(msg);

            if ("User registered successfully.".equals(msg)) {
                manageUsersController.loadUsers();
                popup.close();
            }
        });

        VBox card = new VBox(10,
                titleLabel,
                usernameField,
                emailField,
                passwordField,
                confirmField,
                buttons,
                messageLabel
        );
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 12;"
        );

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #2c3e5088;");

        Scene scene = new Scene(root, 400, 320);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }

    private void showEditUserPopup(User user) {
        Stage popup = new Stage();
        popup.initOwner(manageStage);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Edit User");

        Label titleLabel = new Label("Edit User");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #c0392b;");

        TextField usernameField = new TextField(user.getUsername());
        TextField emailField = new TextField(user.getEmail());
        PasswordField passwordField = new PasswordField();
        passwordField.setText(user.getPassword());

        Button saveBtn = new Button("Save");
        saveBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 12;" +
                        "-fx-cursor: hand;"
        );

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> popup.close());

        HBox buttons = new HBox(10, saveBtn, cancelBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        saveBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            String msg = manageUsersController.updateUser(user.getUserId(), username, email, password);
            messageLabel.setText(msg);

            if ("User updated successfully.".equals(msg)) {
                manageUsersController.loadUsers();
                popup.close();
            }
        });

        VBox card = new VBox(10,
                titleLabel,
                usernameField,
                emailField,
                passwordField,
                buttons,
                messageLabel
        );
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 12;"
        );

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #2c3e5088;");

        Scene scene = new Scene(root, 400, 280);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}