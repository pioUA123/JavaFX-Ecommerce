package com.example.oopii_finalproject.UI.Frames.CustomerFrames;

import com.example.oopii_finalproject.Objects.Order;
import com.example.oopii_finalproject.UI.Controllers.CustomerControllers.OrderHistoryController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderHistoryFrame {

    private final Stage ownerStage;
    private final int userId;
    private final OrderHistoryController controller;

    private Stage historyStage;
    private VBox ordersBox;

    public OrderHistoryFrame(Stage ownerStage, int userId) {
        this.ownerStage = ownerStage;
        this.userId = userId;
        this.controller = new OrderHistoryController(this, userId);
    }

    public void show() {
        historyStage = new Stage();
        historyStage.initOwner(ownerStage);
        historyStage.initModality(Modality.WINDOW_MODAL);
        historyStage.setTitle("Order History");

        Label titleLabel = new Label("Your Orders");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        ordersBox = new VBox(15);
        ordersBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(ordersBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox content = new VBox(15, titleLabel, scrollPane);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_LEFT);

        StackPane root = new StackPane(content);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #eef8f0;");

        Scene scene = new Scene(root, 650, 500);
        historyStage.setScene(scene);
        historyStage.setResizable(false);

        try {
            ArrayList<Order> orders = controller.getOrdersForUser();
            displayOrders(orders);
        } catch (SQLException e) {
            showInfo("Error loading orders.");
            e.printStackTrace();
        }

        historyStage.showAndWait();
    }

    private void displayOrders(ArrayList<Order> orders) {
        ordersBox.getChildren().clear();

        if (orders.isEmpty()) {
            Label none = new Label("You have no orders yet.");
            none.setStyle("-fx-text-fill: #555555;");
            ordersBox.getChildren().add(none);
            return;
        }

        for (Order order : orders) {
            ordersBox.getChildren().add(createOrderCard(order));
        }
    }

    private VBox createOrderCard(Order order) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, #b5b5b5, 6, 0.1, 0, 2);"
        );

        Button cancelBtn = new Button("Cancel Order");
        cancelBtn.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #c0392b;" +
                        "-fx-border-color: #c0392b;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        if (!"PENDING".equalsIgnoreCase(order.getOrderStatus())) {
            cancelBtn.setVisible(false);
            cancelBtn.setManaged(false);
        }

        cancelBtn.setOnAction(e -> controller.cancelOrder(order));

        Label idLabel = new Label("Order #" + order.getOrderId());
        idLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        Label totalLabel = new Label("Total: $" + order.getTotalAmount());
        totalLabel.setStyle("-fx-text-fill: #333333;");

        Label statusLabel = new Label("Status: " + order.getOrderStatus());
        statusLabel.setStyle("-fx-text-fill: #555555;");

        VBox itemsBox = new VBox(4);
        itemsBox.setPadding(new Insets(4, 0, 0, 10));

        try {
            ArrayList<String> itemLines = controller.getOrderItemLines(order.getOrderId());
            for (String line : itemLines) {
                Label itemLabel = new Label("- " + line);
                itemLabel.setStyle("-fx-text-fill: #444444;");
                itemsBox.getChildren().add(itemLabel);
            }
        } catch (SQLException e) {
            showInfo("Error loading items for order #" + order.getOrderId());
            e.printStackTrace();
        }

        card.getChildren().addAll(idLabel, totalLabel, statusLabel, itemsBox, cancelBtn);

        if ("DELIVERED".equalsIgnoreCase(order.getOrderStatus())) {
            try {
                boolean hasReview = controller.hasReviewForOrder(order.getOrderId());
                if (!hasReview) {
                    Button reviewBtn = new Button("Write Review");
                    reviewBtn.setOnAction(e ->
                            new ReviewPopupFrame(this, order.getOrderId(), userId).show()
                    );
                    card.getChildren().add(reviewBtn);
                }
            } catch (SQLException e) {
                showInfo("Error checking reviews for order #" + order.getOrderId());
                e.printStackTrace();
            }
        }

        return card;
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }
}
