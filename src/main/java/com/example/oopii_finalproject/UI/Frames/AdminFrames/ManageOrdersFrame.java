package com.example.oopii_finalproject.UI.Frames.AdminFrames;

import com.example.oopii_finalproject.Objects.Order;
import com.example.oopii_finalproject.Objects.OrderItem;
import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.UI.Controllers.AdminControllers.ManageOrdersController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ManageOrdersFrame {

    private final Stage ownerStage;
    private final ManageOrdersController controller;

    private Stage manageStage;
    private VBox ordersBox;

    public ManageOrdersFrame(Stage ownerStage) {
        this.ownerStage = ownerStage;
        this.controller = new ManageOrdersController(this);
    }

    public void show() {
        manageStage = new Stage();
        manageStage.initOwner(ownerStage);
        manageStage.initModality(Modality.WINDOW_MODAL);
        manageStage.setTitle("Order Management");

        Label titleLabel = new Label("Order Management");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 12;" +
                        "-fx-cursor: hand;"
        );

        Label hintLabel = new Label("Click 'Refresh' to see updated statuses after processing.");
        hintLabel.setStyle("-fx-text-fill: #555555;");

        HBox topRow = new HBox(10, refreshBtn, hintLabel);
        topRow.setAlignment(Pos.CENTER_LEFT);

        ordersBox = new VBox(10);
        ordersBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(ordersBox);
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

        Scene scene = new Scene(root, 800, 520);
        manageStage.setScene(scene);
        manageStage.setResizable(false);

        refreshBtn.setOnAction(e -> controller.loadOrders());

        controller.loadOrders();

        manageStage.showAndWait();
    }

    public void displayOrders(ArrayList<Order> orders) {
        ordersBox.getChildren().clear();

        if (orders.isEmpty()) {
            Label empty = new Label("No orders found.");
            empty.setStyle("-fx-text-fill: #555555;");
            ordersBox.getChildren().add(empty);
            return;
        }

        for (Order order : orders) {
            ordersBox.getChildren().add(createOrderRow(order));
        }
    }

    private HBox createOrderRow(Order order) {
        String customerName = controller.getCustomerName(order.getUserId());

        Label orderLabel = new Label("Order #" + order.getOrderId());
        orderLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label customerLabel = new Label("Customer: " + customerName);
        customerLabel.setStyle("-fx-text-fill: #555555;");

        Label totalLabel = new Label("Total: $" + order.getTotalAmount());
        totalLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        Label statusLabel = new Label("Status: " + order.getOrderStatus());
        statusLabel.setStyle("-fx-text-fill: #2c3e50;");

        VBox infoBox = new VBox(3, orderLabel, customerLabel, totalLabel, statusLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        Button detailsBtn = new Button("Details");
        detailsBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        Button processBtn = new Button("Process");
        processBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #2c3e50;" +
                        "-fx-border-color: #2c3e50;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #c0392b;" +
                        "-fx-border-width: 0;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        String status = order.getOrderStatus();
        if (!"PENDING".equalsIgnoreCase(status)) {
            processBtn.setDisable(true);
            cancelBtn.setDisable(true);
        }

        HBox buttonsBox = new HBox(8, detailsBtn, processBtn, cancelBtn);
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

        detailsBtn.setOnAction(e -> controller.openOrderDetails(order));

        processBtn.setOnAction(e -> {
            String msg = controller.processOrder(order);
            showInfo(msg);
            controller.loadOrders();
        });

        cancelBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Cancel this order?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(btn -> {
                if (btn == ButtonType.YES) {
                    String msg = controller.cancelOrder(order);
                    showInfo(msg);
                    controller.loadOrders();
                }
            });
        });

        return row;
    }

    public void showOrderDetails(Order order, ArrayList<OrderItem> items, ArrayList<Product> products) {
        Stage popup = new Stage();
        popup.initOwner(manageStage);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Order Details");

        Label titleLabel = new Label("Order #" + order.getOrderId() + " Details");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label customerLabel = new Label("Customer ID: " + order.getUserId());
        customerLabel.setStyle("-fx-text-fill: #555555;");

        Label statusLabel = new Label("Status: " + order.getOrderStatus());
        statusLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        Label totalLabel = new Label("Total: $" + order.getTotalAmount());
        totalLabel.setStyle("-fx-text-fill: #2c3e50;");

        VBox itemsBox = new VBox(5);
        itemsBox.setPadding(new Insets(5));

        if (items.isEmpty()) {
            Label noItems = new Label("No items for this order.");
            noItems.setStyle("-fx-text-fill: #555555;");
            itemsBox.getChildren().add(noItems);
        } else {
            for (OrderItem item : items) {
                String productName = findProductName(item.getProductId(), products);
                Label itemLabel = new Label(
                        productName + " (x" + item.getQuantity() + ") - $" + item.getSubtotal()
                );
                itemLabel.setStyle("-fx-text-fill: #333333;");
                itemsBox.getChildren().add(itemLabel);
            }
        }

        ScrollPane scrollPane = new ScrollPane(itemsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox card = new VBox(10,
                titleLabel,
                customerLabel,
                statusLabel,
                totalLabel,
                new Label("Items:"),
                scrollPane
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

        Scene scene = new Scene(root, 450, 350);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }

    private String findProductName(int productId, ArrayList<Product> products) {
        for (Product p : products) {
            if (p.getProductId() == productId) {
                return p.getProductName();
            }
        }
        return "Product #" + productId;
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}