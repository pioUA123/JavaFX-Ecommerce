package com.example.oopii_finalproject.UI.Frames.AdminFrames;

import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.UI.Controllers.AdminControllers.ManageProductsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ManageProductsFrame {

    private final Stage ownerStage;
    private final ManageProductsController controller;

    private Stage manageStage;
    private VBox productsBox;

    public ManageProductsFrame(Stage ownerStage) {
        this.ownerStage = ownerStage;
        this.controller = new ManageProductsController(this);
    }

    public void show() {
        manageStage = new Stage();
        manageStage.initOwner(ownerStage);
        manageStage.initModality(Modality.WINDOW_MODAL);
        manageStage.setTitle("Product Management");

        Label titleLabel = new Label("Product Management");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Button addProductBtn = new Button("Add Product");
        addProductBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 12;" +
                        "-fx-cursor: hand;"
        );

        TextField searchField = new TextField();
        searchField.setPromptText("Search products...");
        searchField.setPrefWidth(220);

        Button searchBtn = new Button("Search");
        searchBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 12;" +
                        "-fx-cursor: hand;"
        );

        HBox topRow = new HBox(10, addProductBtn, new Region(), searchField, searchBtn);
        topRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(topRow.getChildren().get(2), Priority.ALWAYS); // the Region as spacer

        productsBox = new VBox(10);
        productsBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(productsBox);
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

        Scene scene = new Scene(root, 750, 520);
        manageStage.setScene(scene);
        manageStage.setResizable(false);

        addProductBtn.setOnAction(e -> showAddProductPopup());
        searchBtn.setOnAction(e -> controller.searchProducts(searchField.getText()));

        controller.loadProducts();

        manageStage.showAndWait();
    }

    public void displayProducts(ArrayList<Product> products) {
        productsBox.getChildren().clear();

        if (products.isEmpty()) {
            Label empty = new Label("No products found.");
            empty.setStyle("-fx-text-fill: #555555;");
            productsBox.getChildren().add(empty);
            return;
        }

        for (Product product : products) {
            productsBox.getChildren().add(createProductRow(product));
        }
    }

    private HBox createProductRow(Product product) {
        Label nameLabel = new Label(product.getProductName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label descLabel = new Label(product.getProductDescription());
        descLabel.setStyle("-fx-text-fill: #555555;");
        descLabel.setMaxWidth(300);
        descLabel.setWrapText(true);

        Label priceLabel = new Label("Price: $" + product.getProductPrice());
        priceLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        Label stockLabel = new Label("Stock: " + product.getProductStock());
        stockLabel.setStyle("-fx-text-fill: #2c3e50;");

        VBox infoBox = new VBox(3, nameLabel, descLabel, priceLabel, stockLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        Button editBtn = new Button("Edit");
        editBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        Button deleteBtn = new Button("Delete");
        deleteBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #c0392b;" +
                        "-fx-border-width: 0;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        HBox buttonsBox = new HBox(8, editBtn, deleteBtn);
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

        editBtn.setOnAction(e -> showEditProductPopup(product));

        deleteBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this product?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(btn -> {
                if (btn == ButtonType.YES) {
                    String msg = controller.deleteProduct(product.getProductId());
                    showInfo(msg);
                    controller.loadProducts();
                }
            });
        });

        return row;
    }

    private void showAddProductPopup() {
        Stage popup = new Stage();
        popup.initOwner(manageStage);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Add Product");

        Label titleLabel = new Label("Add New Product");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #c0392b;");

        TextField nameField = new TextField();
        nameField.setPromptText("Product name");

        TextArea descArea = new TextArea();
        descArea.setPromptText("Description");
        descArea.setPrefRowCount(3);

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        TextField stockField = new TextField();
        stockField.setPromptText("Stock");

        TextField categoryFiled = new TextField();
        categoryFiled.setPromptText("Category");

        Label imageLabel = new Label("No image selected");
        Button selectImageBtn = new Button("Select Image");

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
        cancelBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 12;" +
                        "-fx-cursor: hand;"
        );
        cancelBtn.setOnAction(e -> popup.close());

        HBox buttons = new HBox(10, saveBtn, cancelBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        final String[] selectedImage = {null};

        selectImageBtn.setOnAction(e -> {
            showSelectImagePopup(imageName -> {
                selectedImage[0] = imageName;
                imageLabel.setText("Selected: " + imageName);
            });
        });

        saveBtn.setOnAction(e -> {
            String name = nameField.getText();
            String desc = descArea.getText();
            String price = priceField.getText();
            String stock = stockField.getText();
            String category = categoryFiled.getText();

            String msg = controller.addProduct(name, desc, price, stock, category, selectedImage[0]);
            messageLabel.setText(msg);

            if ("Product added successfully.".equals(msg)) {
                controller.loadProducts();
                popup.close();
            }
        });

        VBox card = new VBox(10, titleLabel, nameField, descArea, priceField, stockField, categoryFiled, selectImageBtn, imageLabel, buttons, messageLabel);
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

        Scene scene = new Scene(root, 450, 360);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }

    private void showEditProductPopup(Product product) {
        Stage popup = new Stage();
        popup.initOwner(manageStage);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Edit Product");

        Label titleLabel = new Label("Edit Product");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #c0392b;");

        TextField nameField = new TextField(product.getProductName());
        TextArea descArea = new TextArea(product.getProductDescription());
        descArea.setPrefRowCount(3);

        TextField priceField = new TextField(String.valueOf((int) product.getProductPrice()));
        TextField stockField = new TextField(String.valueOf(product.getProductStock()));

        TextField categoryField = new TextField(product.getCategory());
        categoryField.setPromptText("Category");

        Label imageLabel = new Label("Selected: " + product.getImage());
        Button selectImageBtn = new Button("Change Image");

        final String[] selectedImage = { product.getImage() };

        selectImageBtn.setOnAction(e -> {
            showSelectImagePopup(imageName -> {
                selectedImage[0] = imageName;
                imageLabel.setText("Selected: " + imageName);
            });
        });

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
            String name = nameField.getText();
            String desc = descArea.getText();
            String price = priceField.getText();
            String stock = stockField.getText();
            String category = categoryField.getText();

            String msg = controller.updateProduct(product.getProductId(), name, desc, price, stock, category, selectedImage[0]);
            messageLabel.setText(msg);

            if ("Product updated successfully.".equals(msg)) {
                controller.loadProducts();
                popup.close();
            }
        });

        VBox card = new VBox(10, titleLabel, nameField, descArea, priceField, stockField, selectImageBtn, imageLabel, categoryField, buttons, messageLabel);
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

        Scene scene = new Scene(root, 450, 360);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }

    private void showSelectImagePopup(Consumer<String> onSelect) {
        Stage popup = new Stage();
        popup.initOwner(manageStage);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Select Product Image");

        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        File folder = new File("src/main/java/com/example/oopii_finalproject/Images");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    Button btn = new Button(f.getName());
                    btn.setOnAction(e -> {
                        onSelect.accept(f.getName()); // return filename only
                        popup.close();
                    });
                    box.getChildren().add(btn);
                }
            }
        }

        ScrollPane scroll = new ScrollPane(box);
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 300, 300);
        popup.setScene(scene);
        popup.showAndWait();
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}