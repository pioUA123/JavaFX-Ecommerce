package com.example.oopii_finalproject.UI.Frames.AdminFrames;

import com.example.oopii_finalproject.Objects.Shipment;
import com.example.oopii_finalproject.UI.Controllers.AdminControllers.ManageShipmentsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManageShipmentsFrame {

    private final Stage ownerStage;
    private final ManageShipmentsController controller;

    private Stage manageStage;
    private VBox shipmentsBox;

    public ManageShipmentsFrame(Stage ownerStage) {
        this.ownerStage = ownerStage;
        this.controller = new ManageShipmentsController(this);
    }

    public void show() {
        manageStage = new Stage();
        manageStage.initOwner(ownerStage);
        manageStage.initModality(Modality.WINDOW_MODAL);
        manageStage.setTitle("Shipment Management");

        Label titleLabel = new Label("Shipment Management");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        shipmentsBox = new VBox(10);
        shipmentsBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(shipmentsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox card = new VBox(15, titleLabel, scrollPane);
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

        Scene scene = new Scene(root, 800, 520);
        manageStage.setScene(scene);
        manageStage.setResizable(false);

        controller.loadShipments();

        manageStage.showAndWait();
    }

    public void displayShipments(ArrayList<Shipment> shipments) {
        shipmentsBox.getChildren().clear();

        if (shipments.isEmpty()) {
            Label empty = new Label("No shipments found.");
            empty.setStyle("-fx-text-fill: #555555;");
            shipmentsBox.getChildren().add(empty);
            return;
        }

        for (Shipment shipment : shipments) {
            shipmentsBox.getChildren().add(createShipmentRow(shipment));
        }
    }

    private HBox createShipmentRow(Shipment shipment) {
        Label idLabel = new Label("Shipment #" + shipment.getShipmentId());
        idLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label orderLabel = new Label("Order #" + shipment.getOrderId());
        orderLabel.setStyle("-fx-text-fill: #555555;");

        Label providerLabel = new Label("Provider: " + shipment.getShippingProvider());
        providerLabel.setStyle("-fx-text-fill: #555555;");

        Label trackingLabel = new Label("Tracking: " + shipment.getTrackingNumber());
        trackingLabel.setStyle("-fx-text-fill: #555555;");

        Label dateLabel = new Label("Estimated: " + shipment.getEstimatedDelivery());
        dateLabel.setStyle("-fx-text-fill: #555555;");

        Label statusLabel = new Label("Status: " + shipment.getShipmentStatus());
        statusLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        VBox infoBox = new VBox(3,
                idLabel,
                orderLabel,
                providerLabel,
                trackingLabel,
                dateLabel,
                statusLabel
        );
        infoBox.setAlignment(Pos.CENTER_LEFT);

        Button editBtn = new Button("Edit");
        editBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        HBox buttonsBox = new HBox(8, editBtn);
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

        editBtn.setOnAction(e -> showEditShipmentPopup(shipment));

        return row;
    }

    private void showEditShipmentPopup(Shipment shipment) {
        Stage popup = new Stage();
        popup.initOwner(manageStage);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Edit Shipment");

        Label titleLabel = new Label("Edit Shipment #" + shipment.getShipmentId());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #c0392b;");

        TextField orderIdField = new TextField(String.valueOf(shipment.getOrderId()));
        orderIdField.setPromptText("Order ID");

        TextField providerField = new TextField(shipment.getShippingProvider());
        providerField.setPromptText("Shipping provider");

        TextField trackingField = new TextField(shipment.getTrackingNumber());
        trackingField.setPromptText("Tracking number");

        DatePicker datePicker = new DatePicker();
        if (shipment.getEstimatedDelivery() != null) {
            datePicker.setValue(shipment.getEstimatedDelivery().toLocalDate());
        }

        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll("PENDING", "IN_TRANSIT", "DELIVERED", "CANCELLED");
        statusBox.setValue(shipment.getShipmentStatus());

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
            String orderIdText = orderIdField.getText();
            String provider = providerField.getText();
            String tracking = trackingField.getText();
            String status = statusBox.getValue();

            int orderId;
            try {
                orderId = Integer.parseInt(orderIdText);
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid order ID.");
                return;
            }

            LocalDate localDate = datePicker.getValue();
            Date sqlDate = null;
            if (localDate != null) {
                sqlDate = Date.valueOf(localDate);
            }

            String msg = controller.updateShipment(shipment.getShipmentId(), orderId, provider, tracking, sqlDate, status);
            messageLabel.setText(msg);

            if ("Shipment updated successfully.".equals(msg)) {
                controller.loadShipments();
                popup.close();
            }
        });

        VBox card = new VBox(10,
                titleLabel,
                orderIdField,
                providerField,
                trackingField,
                datePicker,
                statusBox,
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

        Scene scene = new Scene(root, 450, 380);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}