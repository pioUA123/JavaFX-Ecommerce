package com.example.oopii_finalproject.UI.Frames.AdminFrames;

import com.example.oopii_finalproject.Objects.Review;
import com.example.oopii_finalproject.UI.Controllers.AdminControllers.ManageReviewsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ManageReviewsFrame {

    private final Stage ownerStage;
    private final ManageReviewsController controller;

    private Stage manageStage;
    private VBox reviewsBox;

    public ManageReviewsFrame(Stage ownerStage) {
        this.ownerStage = ownerStage;
        this.controller = new ManageReviewsController(this);
    }

    public void show() {
        manageStage = new Stage();
        manageStage.initOwner(ownerStage);
        manageStage.initModality(Modality.WINDOW_MODAL);
        manageStage.setTitle("Reviews");

        Label titleLabel = new Label("Customer Reviews");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        reviewsBox = new VBox(10);
        reviewsBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(reviewsBox);
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

        Scene scene = new Scene(root, 750, 520);
        manageStage.setScene(scene);
        manageStage.setResizable(false);

        controller.loadReviews();

        manageStage.showAndWait();
    }

    public void displayReviews(ArrayList<Review> reviews) {
        reviewsBox.getChildren().clear();

        if (reviews.isEmpty()) {
            Label empty = new Label("No reviews found.");
            empty.setStyle("-fx-text-fill: #555555;");
            reviewsBox.getChildren().add(empty);
            return;
        }

        for (Review review : reviews) {
            reviewsBox.getChildren().add(createReviewCard(review));
        }
    }

    private VBox createReviewCard(Review review) {
        String customerName = controller.getUserName(review.getUserId());
        ArrayList<String> productNames = controller.getProductNamesForOrder(review.getOrderId());

        Label customerLabel = new Label(customerName);
        customerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label ratingLabel = new Label("Rating: " + buildStars(review.getRating()));
        ratingLabel.setStyle("-fx-text-fill: #2c3e50;");

        Label textLabel = new Label(review.getReviewText());
        textLabel.setWrapText(true);
        textLabel.setStyle("-fx-text-fill: #555555;");

        Label productsLabel;
        if (productNames.isEmpty()) {
            productsLabel = new Label("Products: (no items found for this order)");
        } else {
            StringBuilder sb = new StringBuilder("Products: ");
            for (int i = 0; i < productNames.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(productNames.get(i));
            }
            productsLabel = new Label(sb.toString());
        }
        productsLabel.setStyle("-fx-text-fill: #555555;");

        Label orderLabel = new Label("Order #" + review.getOrderId());
        orderLabel.setStyle("-fx-text-fill: #888888;");

        VBox card = new VBox(5,
                customerLabel,
                ratingLabel,
                textLabel,
                productsLabel,
                orderLabel
        );
        card.setPadding(new Insets(10));
        card.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-border-radius: 10;"
        );

        return card;
    }

    private String buildStars(int rating) {
        StringBuilder sb = new StringBuilder();
        int r = rating;
        if (r < 0) r = 0;
        if (r > 5) r = 5;

        for (int i = 0; i < r; i++) {
            sb.append("★");
        }
        for (int i = r; i < 5; i++) {
            sb.append("☆");
        }
        return sb.toString();
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}