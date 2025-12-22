package com.example.oopii_finalproject.UI.Frames.CustomerFrames;

import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.UI.Components.ProductCard;
import com.example.oopii_finalproject.UI.Controllers.CustomerControllers.CustomerDashboardController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FavoritesFrame {

    private final Stage ownerStage;
    private final User loggedInUser;
    private final CustomerDashboardController customerDashboardController;

    private Stage favoritesStage;
    private FlowPane productGrid;

    public FavoritesFrame(Stage ownerStage, User loggedInUser, CustomerDashboardController controller) {
        this.ownerStage = ownerStage;
        this.loggedInUser = loggedInUser;
        this.customerDashboardController = controller;
    }

    public void show() {
        favoritesStage = new Stage();
        favoritesStage.initOwner(ownerStage);
        favoritesStage.initModality(Modality.WINDOW_MODAL);
        favoritesStage.setTitle("My Favorites");

        Label titleLabel = new Label("Your Favorite Products");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        productGrid = new FlowPane();
        productGrid.setPadding(new Insets(20));
        productGrid.setHgap(20);
        productGrid.setVgap(20);
        productGrid.setPrefWrapLength(600);
        productGrid.setStyle("-fx-background-color: #f5f5f5;");

        ScrollPane scrollPane = new ScrollPane(productGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox layout = new VBox(15, titleLabel, scrollPane);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 12;"
        );

        StackPane root = new StackPane(layout);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #00987922;");

        Scene scene = new Scene(root, 660, 500);
        favoritesStage.setScene(scene);
        favoritesStage.setResizable(false);

        loadFavorites();

        favoritesStage.showAndWait();
    }

    private void loadFavorites() {
        productGrid.getChildren().clear();

        ArrayList<Product> favorites = customerDashboardController.getFavoriteProducts();

        if (favorites.isEmpty()) {
            Label emptyLabel = new Label("You have no favorites yet.");
            emptyLabel.setStyle("-fx-text-fill: #555555;");
            productGrid.getChildren().add(emptyLabel);
            return;
        }

        for (Product p : favorites) {
            productGrid.getChildren().add(new ProductCard(p, customerDashboardController));
        }
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}