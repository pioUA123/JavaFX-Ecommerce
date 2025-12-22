package com.example.oopii_finalproject.UI.Frames.AdminFrames;

import com.example.oopii_finalproject.UI.Controllers.AdminControllers.ReportsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReportsFrame {

    private final Stage ownerStage;
    private final ReportsController controller;

    private Stage reportsStage;

    public ReportsFrame(Stage ownerStage) {
        this.ownerStage = ownerStage;
        this.controller = new ReportsController(this);
    }

    public void show() {
        reportsStage = new Stage();
        reportsStage.initOwner(ownerStage);
        reportsStage.initModality(Modality.WINDOW_MODAL);
        reportsStage.setTitle("Reports");

        Label titleLabel = new Label("Generate Reports");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label infoLabel = new Label("Reports will be saved to hardcoded files in the application directory.");
        infoLabel.setStyle("-fx-text-fill: #555555;");

        Button salesBtn = new Button("Generate Sales Report");
        Button inventoryBtn = new Button("Generate Inventory Report");
        Button ordersBtn = new Button("Generate Orders Report");

        salesBtn.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 16;");
        inventoryBtn.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 16;");
        ordersBtn.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 16;");

        salesBtn.setOnAction(e -> controller.generateSalesReport());
        inventoryBtn.setOnAction(e -> controller.generateInventoryReport());
        ordersBtn.setOnAction(e -> controller.generateOrderReport());

        VBox buttonsBox = new VBox(10, salesBtn, inventoryBtn, ordersBtn);
        buttonsBox.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(15, titleLabel, infoLabel, buttonsBox);
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
        root.setStyle("-fx-background-color: #2c3e5022;");

        Scene scene = new Scene(root, 450, 250);
        reportsStage.setScene(scene);
        reportsStage.setResizable(false);
        reportsStage.showAndWait();
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}