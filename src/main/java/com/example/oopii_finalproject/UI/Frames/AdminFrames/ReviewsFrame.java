package com.example.oopii_finalproject.UI.Frames.AdminFrames;

import com.example.oopii_finalproject.Objects.Review;
import com.example.oopii_finalproject.UI.Controllers.AdminControllers.ReviewsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewsFrame {

    private final Stage ownerStage;
    private final ReviewsController controller;

    private Stage reviewsStage;
    private VBox reviewsBox;

    public ReviewsFrame(Stage ownerStage) {
        this.ownerStage = ownerStage;
        this.controller = new ReviewsController(this);
    }

    public void show() {
        reviewsStage = new Stage();
        reviewsStage.initOwner(ownerStage);
        reviewsStage.initModality(Modality.WINDOW_MODAL);
        reviewsStage.setTitle("All Reviews");

        Label titleLabel = new Label("Customer Reviews");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        reviewsBox = new VBox(12);
        reviewsBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(reviewsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox card = new VBox(15, titleLabel, scrollPane);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 12;"
        );

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #e8f5e9;");

        Scene scene = new Scene(root, 650, 500);
        reviewsStage.setScene(scene);
        reviewsStage.setResizable(false);

        try {
            controller.loadAllData();
            ArrayList<Review> reviews = controller.getAllReviews();
            displayReviews(reviews);
        } catch (SQLException e) {
            showInfo("Error loading reviews.");
            e.printStackTrace();
        }

        reviewsStage.showAndWait();
    }

    private void displayReviews(ArrayList<Review> reviews) {
        reviewsBox.getChildren().clear();

        if (reviews.isEmpty()) {
            Label none = new Label("No reviews available.");
            none.setStyle("-fx-text-fill: #555555;");
            reviewsBox.getChildren().add(none);
            return;
        }

        for (Review review : reviews) {
            reviewsBox.getChildren().add(createReviewCard(review));
        }
    }

    private VBox createReviewCard(Review review) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 10;"
        );

        String customerName = controller.getUserNameById(review.getUserId());
        Label nameLabel = new Label("Customer: " + customerName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        Label ratingLabel = new Label("Rating: " + review.getRating() + "/5");
        ratingLabel.setStyle("-fx-text-fill: #009879; -fx-font-weight: bold;");

        Label textLabel = new Label(review.getReviewText());
        textLabel.setWrapText(true);
        textLabel.setStyle("-fx-text-fill: #555555;");

        Label productsTitle = new Label("Products:");
        productsTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        VBox productList = new VBox(3);
        ArrayList<String> productNames = controller.getProductsForOrder(review.getOrderId());
        for (String name : productNames) {
            Label pLabel = new Label("- " + name);
            pLabel.setStyle("-fx-text-fill: #444444;");
            productList.getChildren().add(pLabel);
        }

        card.getChildren().addAll(nameLabel, ratingLabel, textLabel, productsTitle, productList);

        return card;
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }
}
