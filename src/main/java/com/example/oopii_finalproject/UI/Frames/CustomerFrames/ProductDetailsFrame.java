package com.example.oopii_finalproject.UI.Frames.CustomerFrames;

import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.UI.Controllers.CustomerControllers.CustomerDashboardController;
import com.example.oopii_finalproject.UI.Controllers.CustomerControllers.ProductDetailsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductDetailsFrame {

    private final Stage ownerStage;
    private final Product product;
    private final ProductDetailsController controller;

    private Spinner<Integer> quantitySpinner;

    private Stage detailsStage;

    private final CustomerDashboardController dashboardController;

    public ProductDetailsFrame(Stage ownerStage, Product product, CustomerDashboardController dashboardController) {
        this.ownerStage = ownerStage;
        this.product = product;
        this.dashboardController = dashboardController;
        this.controller = new ProductDetailsController(this, product, dashboardController);
    }

    public void show() {
        detailsStage = new Stage();
        detailsStage.initOwner(ownerStage);
        detailsStage.initModality(Modality.WINDOW_MODAL);
        detailsStage.setTitle("Product Details");

        Label titleLabel = new Label(product.getProductName());
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        ImageView productImage = new ImageView();

        try {
            if (product.getImage() != null && !product.getImage().isBlank()) {
                productImage.setImage(new Image("file:src/main/java/com/example/oopii_finalproject/Images/" + product.getImage()));
            }
        } catch (Exception e) {
            productImage.setImage(new Image("file:src/main/java/com/example/oopii_finalproject/Images/Placeholder.jpg"));
        }

        productImage.setFitWidth(260);
        productImage.setFitHeight(140);
        productImage.setPreserveRatio(true);
        productImage.setSmooth(true);
        productImage.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8;");

        Label descLabel = new Label(product.getProductDescription());
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-text-fill: #555555;");

        Label priceLabel = new Label("$" + product.getProductPrice());
        priceLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #009879;");

        Label quantityLabel = new Label("Quantity:");
        quantityLabel.setStyle("-fx-text-fill: #333333;");

        quantitySpinner = new Spinner<>(1, product.getProductStock(), 1);
        quantitySpinner.setEditable(false);

        HBox quantityRow = new HBox(10, quantityLabel, quantitySpinner);
        quantityRow.setAlignment(Pos.CENTER_LEFT);

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-background-color: #009879; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");

        HBox buttonRow = new HBox(15, addToCartButton);
        buttonRow.setAlignment(Pos.CENTER_RIGHT);

        VBox details = new VBox(15, titleLabel, productImage, descLabel, priceLabel, quantityRow, buttonRow);
        details.setAlignment(Pos.TOP_LEFT);
        details.setPadding(new Insets(20));
        details.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #d9d9d9; -fx-border-radius: 12;");

        StackPane root = new StackPane(details);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #00987922;");

        Scene scene = new Scene(root, 500, 480);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                controller.handleClose();
            }
        });

        addToCartButton.setOnAction(e -> {
            controller.handleAddToCart(quantitySpinner.getValue());
        });

        detailsStage.setScene(scene);
        detailsStage.setResizable(false);
        detailsStage.centerOnScreen();
        detailsStage.showAndWait();
    }

    public void close() {
        if (detailsStage != null) {
            detailsStage.close();
        }
    }

    public void showInfo(String message) {
        new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK).showAndWait();
    }

    public int getQuantitySpinner() {
        return quantitySpinner.getValue();
    }
}
