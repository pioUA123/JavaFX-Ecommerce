package com.example.oopii_finalproject.UI.Frames.CustomerFrames;

import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.CartItemService;
import com.example.oopii_finalproject.UI.Controllers.CustomerControllers.CartController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CheckoutFrame {

    private final Stage ownerStage;
    private final User loggedInUser;
    private final CartItemService cartItemService;
    private final CartController cartController;

    private Stage checkoutStage;

    public CheckoutFrame(Stage ownerStage,
                         User loggedInUser,
                         CartItemService cartItemService,
                         CartController cartController) {
        this.ownerStage = ownerStage;
        this.loggedInUser = loggedInUser;
        this.cartItemService = cartItemService;
        this.cartController = cartController;
    }

    public void show() {
        checkoutStage = new Stage();
        checkoutStage.initOwner(ownerStage);
        checkoutStage.initModality(Modality.WINDOW_MODAL);
        checkoutStage.setTitle("Checkout");

        Label titleLabel = new Label("Checkout");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        double total = cartItemService.getTotalPrice();
        Label totalLabel = new Label("Order Total: $" + total);
        totalLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333;");

        // Payment method
        Label methodLabel = new Label("Payment Method:");
        RadioButton cardRadio = new RadioButton("Card");
        RadioButton paypalRadio = new RadioButton("PayPal");

        ToggleGroup group = new ToggleGroup();
        cardRadio.setToggleGroup(group);
        paypalRadio.setToggleGroup(group);
        cardRadio.setSelected(true);

        HBox methodBox = new HBox(10, cardRadio, paypalRadio);
        methodBox.setAlignment(Pos.CENTER_LEFT);

        // Shipping address
        Label addressLabel = new Label("Shipping Address:");
        TextArea addressArea = new TextArea();
        addressArea.setPromptText("Enter your shipping address...");
        addressArea.setWrapText(true);
        addressArea.setPrefRowCount(3);

        Button placeOrderBtn = new Button("Place Order");
        placeOrderBtn.setStyle(
                "-fx-background-color: #009879;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 16;"
        );

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: #009879;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 16;"
        );
        cancelBtn.setOnAction(e -> checkoutStage.close());

        placeOrderBtn.setOnAction(e -> {
            String address = addressArea.getText();
            if (address == null || address.trim().isEmpty()) {
                showInfo("Please enter your shipping address.");
                return;
            }

            String method;
            if (cardRadio.isSelected()) {
                method = "CARD";
            } else if (paypalRadio.isSelected()) {
                method = "PAYPAL";
            } else {
                showInfo("Please choose a payment method.");
                return;
            }

            boolean success = cartController.placeOrder(method, address);
            if (success) {
                checkoutStage.close();
            }
        });

        HBox buttons = new HBox(10, cancelBtn, placeOrderBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        VBox content = new VBox(15,
                titleLabel,
                totalLabel,
                methodLabel,
                methodBox,
                addressLabel,
                addressArea,
                buttons
        );

        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_LEFT);
        content.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #d9d9d9;" +
                        "-fx-border-radius: 12;"
        );

        StackPane root = new StackPane(content);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #00987922;");

        Scene scene = new Scene(root, 450, 380);
        checkoutStage.setScene(scene);
        checkoutStage.setResizable(false);
        checkoutStage.showAndWait();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}
