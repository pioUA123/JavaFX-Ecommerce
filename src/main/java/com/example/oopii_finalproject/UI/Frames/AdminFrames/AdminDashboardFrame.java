package com.example.oopii_finalproject.UI.Frames.AdminFrames;

import com.example.oopii_finalproject.UI.Controllers.AdminControllers.AdminDashboardController;
import com.example.oopii_finalproject.UI.Frames.LoginFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AdminDashboardFrame {

    private final Stage stage;
    private final AdminDashboardController adminDashboardController;

    public AdminDashboardFrame(Stage stage) {
        this.stage = stage;
        this.adminDashboardController = new AdminDashboardController(this);
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Sidebar
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #2c3e50;");

        Label menuTitle = new Label("ADMIN MENU");
        menuTitle.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button profileBtn = sidebarButton("Profile");
        Button usersBtn = sidebarButton("Users");
        Button productsBtn = sidebarButton("Products");
        Button ordersBtn = sidebarButton("Orders");
        Button shipmentsBtn = sidebarButton("Shipments");
        Button reviewsBtn = sidebarButton("Reviews");
        Button promotionsBtn = sidebarButton("Promotions");
        Button reportsBtn = sidebarButton("Reports");
        Button logoutBtn = sidebarButton("Logout");

        profileBtn.setOnAction( e -> {
            try {
                adminDashboardController.openUserProfile();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        usersBtn.setOnAction(e -> adminDashboardController.openUserManagement());
        productsBtn.setOnAction(e -> adminDashboardController.openProductManagement());
        ordersBtn.setOnAction(e -> adminDashboardController.openOrderManagement());
        shipmentsBtn.setOnAction(e -> adminDashboardController.openShipmentManagement());
        reviewsBtn.setOnAction(e -> adminDashboardController.openReviewManagement());
        promotionsBtn.setOnAction(e -> adminDashboardController.openPromotionManagement());
        reportsBtn.setOnAction(e -> adminDashboardController.openReports());
        logoutBtn.setOnAction(e -> adminDashboardController.logout());

        sidebar.getChildren().addAll(menuTitle, profileBtn, usersBtn, productsBtn, ordersBtn, shipmentsBtn, reviewsBtn, promotionsBtn, reportsBtn, logoutBtn);
        sidebar.setAlignment(Pos.TOP_CENTER);

        root.setLeft(sidebar);

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10, 20, 10, 20));
        topBar.setStyle("-fx-background-color: white;");

        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        topBar.getChildren().add(titleLabel);
        root.setTop(topBar);

        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.TOP_LEFT);

        Label welcome = new Label("Welcome to the Admin Panel.");
        welcome.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333;");

        Label hint = new Label("Use the menu on the left to manage users, products, orders, and more.");
        hint.setStyle("-fx-text-fill: #555555;");

        centerBox.getChildren().addAll(welcome, hint);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.setMaximized(true);
        stage.show();
    }

    private Button sidebarButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(180);
        btn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #2c3e50;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 8 0;" +
                        "-fx-cursor: hand;"
        );
        return btn;
    }

    public Stage getStage() {
        return stage;
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    public void redirectToLogin() {
        new LoginFrame(stage).show();
    }
}