package com.example.oopii_finalproject.UI.Frames.CustomerFrames;

import com.example.oopii_finalproject.Objects.CartItem;
import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.CartItemService;
import com.example.oopii_finalproject.UI.Controllers.CustomerControllers.CartController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CartFrame {

    private final Stage ownerStage;
    private final User loggedInUser;
    private final CartItemService cartItemService;
    private final CartController controller;
    private final CustomerDashboardFrame customerDashboardFrame;

    private Stage cartStage;
    private VBox itemsBox;
    private Label totalLabel;

    public CartFrame(Stage ownerStage, User loggedInUser, CartItemService cartItemService, CustomerDashboardFrame customerDashboardFrame) {
        this.ownerStage = ownerStage;
        this.loggedInUser = loggedInUser;
        this.cartItemService = cartItemService;
        this.customerDashboardFrame = customerDashboardFrame;
        this.controller = new CartController(this, loggedInUser, cartItemService, customerDashboardFrame);
    }

    public void show() {
        cartStage = new Stage();
        cartStage.initOwner(ownerStage);
        cartStage.initModality(Modality.WINDOW_MODAL);
        cartStage.setTitle("Your Cart");

        Label titleLabel = new Label("Your Cart");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        itemsBox = new VBox(10);
        itemsBox.setPadding(new Insets(10, 10, 10, 10));
        itemsBox.setStyle("-fx-border-radius: 8;");

        ScrollPane scrollPane = new ScrollPane(itemsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-radius: 8;");

        totalLabel = new Label("Total: $0.0");
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #009879;");

        Button checkoutBtn = new Button("Go to Checkout");
        checkoutBtn.setStyle(
                "-fx-background-color: #009879;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 16;" +
                        "-fx-cursor: hand;"
        );

        checkoutBtn.setOnAction(e -> {
            CheckoutFrame checkoutFrame = new CheckoutFrame(
                    cartStage,
                    loggedInUser,
                    cartItemService,
                    controller
            );
            checkoutFrame.show();
        });

        HBox bottomRow = new HBox(20, totalLabel, checkoutBtn);
        bottomRow.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(15, titleLabel, scrollPane, bottomRow);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-padding: 30;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 12;"
        );

        VBox layout = new VBox(card);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #00987922;");

        Scene scene = new Scene(layout, 700, 500);
        cartStage.setScene(scene);
        cartStage.setResizable(false);

        controller.loadCart();

        cartStage.showAndWait();
    }

    public void displayCartItems(ArrayList<CartItem> items, double totalPrice) {
        itemsBox.getChildren().clear();

        if (items.isEmpty()) {
            Label emptyLabel = new Label("Your cart is empty.");
            emptyLabel.setStyle("-fx-text-fill: #555555;");
            itemsBox.getChildren().add(emptyLabel);
        } else {
            for (CartItem item : items) {
                itemsBox.getChildren().add(createCartRow(item));
            }
        }

        totalLabel.setText("Total: $" + totalPrice);
    }

    private HBox createCartRow(CartItem item) {

        ImageView productImage = new ImageView();

        try {
            if (item.getProduct().getImage() != null && !item.getProduct().getImage().isBlank()) {
                productImage.setImage(new Image("file:src/main/java/com/example/oopii_finalproject/Images/" + item.getProduct().getImage()));
            }
        } catch (Exception e) {
            productImage.setImage(new Image("file:src/main/java/com/example/oopii_finalproject/Images/Placeholder.jpg"));
        }

        productImage.setFitWidth(100);
        productImage.setFitHeight(100);
        productImage.setPreserveRatio(true);
        productImage.setSmooth(true);
        productImage.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 8;");

        Label nameLabel = new Label(item.getProduct().getProductName());
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label unitPriceLabel = new Label("Unit: $" + item.getProduct().getProductPrice());
        unitPriceLabel.setStyle("-fx-text-fill: #333333;");

        VBox infoBox = new VBox(5, nameLabel, unitPriceLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        Button minusBtn = new Button("-");
        minusBtn.setPrefWidth(30);
        minusBtn.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-cursor: hand;"
        );

        Label qtyLabel = new Label(String.valueOf(item.getQuantity()));
        qtyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button plusBtn = new Button("+");
        plusBtn.setPrefWidth(30);
        plusBtn.setStyle(
                "-fx-background-color: #009879;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-cursor: hand;"
        );

        HBox qtyBox = new HBox(10, minusBtn, qtyLabel, plusBtn);
        qtyBox.setAlignment(Pos.CENTER);

        double subtotal = item.getProduct().getProductPrice() * item.getQuantity();
        Label subtotalLabel = new Label("$" + subtotal);
        subtotalLabel.setStyle("-fx-text-fill: #009879; -fx-font-weight: bold;");

        Button removeBtn = new Button("Delete");
        removeBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #ff0000;" +
                        "-fx-font-size: 12px;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-width: 0;"
        );

        VBox rightBox = new VBox(10, subtotalLabel, removeBtn);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        HBox row = new HBox(15, productImage, infoBox, qtyBox, rightBox);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(5, 0, 5, 0));

        plusBtn.setOnAction(e -> {
            controller.increaseQuantity(item);
            customerDashboardFrame.refreshProductCardFor(item);
        });

        minusBtn.setOnAction(e -> {
            controller.decreaseQuantity(item);
            customerDashboardFrame.refreshProductCardFor(item);
        });

        removeBtn.setOnAction(e -> {
            controller.removeItem(item);
            customerDashboardFrame.refreshProductCardFor(item);
        });

        return row;
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}