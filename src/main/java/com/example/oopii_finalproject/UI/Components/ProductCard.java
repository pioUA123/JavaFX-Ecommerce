package com.example.oopii_finalproject.UI.Components;

import com.example.oopii_finalproject.Objects.CartItem;
import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.UI.Controllers.CustomerControllers.CustomerDashboardController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductCard extends VBox {

    private final Product product;
    private final CustomerDashboardController controller;
    private CartItem cartItem;

    private final Label qtyBadge;
    private final Label outOfStockLabel;
    private final Button favoriteBtn;

    public ProductCard(Product product, CustomerDashboardController customerDashboardController) {
        this.product = product;
        this.controller = customerDashboardController;
        this.cartItem = customerDashboardController.getCartItemFor(product);

        setSpacing(10);
        setPadding(new Insets(15));
        setPrefSize(250, 200);
        setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 12;" +
                        "-fx-cursor: hand;"
        );

        Label titleLabel = new Label(product.getProductName());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label descLabel = new Label(product.getProductDescription());
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-text-fill: #555555;");

        Label priceLabel = new Label("$" + product.getProductPrice());
        priceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #009879; -fx-font-weight: bold;");

        Button addBtn = new Button("Add to Cart");
        addBtn.setStyle(
                "-fx-background-color: #009879;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 12;" +
                        "-fx-cursor: hand;"
        );

        qtyBadge = new Label();
        qtyBadge.setPrefSize(24, 24);
        qtyBadge.setStyle(
                "-fx-background-color: #009879;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 50;" +
                        "-fx-alignment: center;"
        );
        qtyBadge.setAlignment(Pos.CENTER);
        qtyBadge.setVisible(false);
        qtyBadge.setManaged(false);

        outOfStockLabel = new Label();
        outOfStockLabel.setStyle("-fx-text-fill: red;");
        outOfStockLabel.setAlignment(Pos.CENTER_LEFT);

        favoriteBtn = new Button();
        favoriteBtn.setPrefWidth(40);
        favoriteBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #e53935;" +
                        "-fx-font-size: 14px;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 0;"
        );

        boolean isFav = customerDashboardController.isFavorite(product);
        updateFavoriteButton(isFav);

        HBox bottomRow = new HBox(10, addBtn, qtyBadge, favoriteBtn);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        addBtn.setOnAction(e -> {
            if (!controller.isUserAuthenticated()) {
                controller.requireAuth();
                return;
            }

            if (cartItem == null) {
                cartItem = new CartItem(product);
            }

            String msg = controller.addItemToCart(cartItem, 1);
            if ("Item added Successfully".equals(msg)) {
                refreshQuantityBadge();
                outOfStockLabel.setText("");
            } else {
                outOfStockLabel.setText(msg);
            }
        });

        favoriteBtn.setOnAction(e -> {
            boolean before = customerDashboardController.isFavorite(product);
            customerDashboardController.toggleFavorite(product);
            boolean after = !before;
            updateFavoriteButton(after);
        });

        setOnMouseClicked(e -> customerDashboardController.openProductDetails(product));

        getChildren().addAll(titleLabel, descLabel, priceLabel, bottomRow, outOfStockLabel);

    }

    public Product getProduct() {
        return product;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void refreshQuantityBadge() {
        if (cartItem == null) {
            qtyBadge.setVisible(false);
            qtyBadge.setManaged(false);
            return;
        }

        int qty = cartItem.getQuantity();
        if (qty > 0) {
            qtyBadge.setText(String.valueOf(qty));
            qtyBadge.setVisible(true);
            qtyBadge.setManaged(true);
        } else {
            qtyBadge.setVisible(false);
            qtyBadge.setManaged(false);
        }
    }

    private void updateFavoriteButton(boolean isFavorite) {
        if (isFavorite) {
            favoriteBtn.setText("♥");
        } else {
            favoriteBtn.setText("♡");
        }
    }

    public void clearCartItem() {
        this.cartItem = null;
        refreshQuantityBadge();
    }

    public void setCartItem(CartItem item) {
        this.cartItem = item;
    }
}