package com.example.oopii_finalproject.UI.Frames.AdminFrames;

import com.example.oopii_finalproject.Objects.Promotion;
import com.example.oopii_finalproject.UI.Controllers.AdminControllers.ManagePromotionsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ManagePromotionsFrame {

    private final Stage ownerStage;
    private final ManagePromotionsController controller;

    private Stage manageStage;
    private VBox promotionsBox;

    public ManagePromotionsFrame(Stage ownerStage) {
        this.ownerStage = ownerStage;
        this.controller = new ManagePromotionsController(this);
    }

    public void show() {
        manageStage = new Stage();
        manageStage.initOwner(ownerStage);
        manageStage.initModality(Modality.WINDOW_MODAL);
        manageStage.setTitle("Promotion Management");

        Label titleLabel = new Label("Promotion Management");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Button addPromoBtn = new Button("Add Promotion");
        addPromoBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 12;" +
                        "-fx-cursor: hand;"
        );

        HBox topRow = new HBox(10, addPromoBtn);
        topRow.setAlignment(Pos.CENTER_LEFT);

        promotionsBox = new VBox(10);
        promotionsBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(promotionsBox);
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

        Scene scene = new Scene(root, 650, 500);
        manageStage.setScene(scene);
        manageStage.setResizable(false);

        addPromoBtn.setOnAction(e -> showAddPromotionPopup());

        controller.loadPromotions();

        manageStage.showAndWait();
    }

    public void displayPromotions(ArrayList<Promotion> promotions) {
        promotionsBox.getChildren().clear();

        if (promotions.isEmpty()) {
            Label empty = new Label("No promotions found.");
            empty.setStyle("-fx-text-fill: #555555;");
            promotionsBox.getChildren().add(empty);
            return;
        }

        for (Promotion promotion : promotions) {
            promotionsBox.getChildren().add(createPromotionRow(promotion));
        }
    }

    private HBox createPromotionRow(Promotion promotion) {
        Label codeLabel = new Label("Code: " + promotion.getPromoCode());
        codeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label valueLabel = new Label("Value: " + promotion.getpValue());
        valueLabel.setStyle("-fx-text-fill: #555555;");

        String statusText = promotion.isActive() ? "Active" : "Inactive";
        Label statusLabel = new Label("Status: " + statusText);
        statusLabel.setStyle("-fx-text-fill: #2c3e50;");

        VBox infoBox = new VBox(3, codeLabel, valueLabel, statusLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        Button editBtn = new Button("Edit");
        editBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 4 10;" +
                        "-fx-cursor: hand;"
        );

        Button toggleBtn = new Button(promotion.isActive() ? "Deactivate" : "Activate");
        toggleBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #2c3e50;" +
                        "-fx-border-color: #2c3e50;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-radius: 6;" +
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

        HBox buttonsBox = new HBox(8, editBtn, toggleBtn, deleteBtn);
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

        editBtn.setOnAction(e -> showEditPromotionPopup(promotion));

        toggleBtn.setOnAction(e -> {
            boolean newActive = !promotion.isActive();
            String msg = controller.updateActive(promotion.getPromoId(), newActive);
            showInfo(msg);
            controller.loadPromotions();
        });

        deleteBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this promotion?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(btn -> {
                if (btn == ButtonType.YES) {
                    String msg = controller.deletePromotion(promotion.getPromoId());
                    showInfo(msg);
                    controller.loadPromotions();
                }
            });
        });

        return row;
    }

    private void showAddPromotionPopup() {
        Stage popup = new Stage();
        popup.initOwner(manageStage);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Add Promotion");

        Label titleLabel = new Label("Add New Promotion");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #c0392b;");

        TextField codeField = new TextField();
        codeField.setPromptText("Promo code");

        TextField valueField = new TextField();
        valueField.setPromptText("Value (number)");

        CheckBox activeCheck = new CheckBox("Active");
        activeCheck.setSelected(true);

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
            String code = codeField.getText();
            String valueText = valueField.getText();
            boolean active = activeCheck.isSelected();

            String msg = controller.addPromotion(code, valueText, active);
            messageLabel.setText(msg);

            if ("Promotion added successfully.".equals(msg)) {
                controller.loadPromotions();
                popup.close();
            }
        });

        VBox card = new VBox(10,
                titleLabel,
                codeField,
                valueField,
                activeCheck,
                buttons,
                messageLabel
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

        Scene scene = new Scene(root, 400, 280);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }

    private void showEditPromotionPopup(Promotion promotion) {
        Stage popup = new Stage();
        popup.initOwner(manageStage);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.setTitle("Edit Promotion");

        Label titleLabel = new Label("Edit Promotion");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #c0392b;");

        TextField codeField = new TextField(promotion.getPromoCode());
        TextField valueField = new TextField(String.valueOf((int) promotion.getpValue()));

        CheckBox activeCheck = new CheckBox("Active");
        activeCheck.setSelected(promotion.isActive());

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
            String code = codeField.getText();
            String valueText = valueField.getText();
            boolean active = activeCheck.isSelected();

            String msg = controller.updatePromotion(promotion.getPromoId(), code, valueText, active);
            messageLabel.setText(msg);

            if ("Promotion updated successfully.".equals(msg)) {
                controller.loadPromotions();
                popup.close();
            }
        });

        VBox card = new VBox(10,
                titleLabel,
                codeField,
                valueField,
                activeCheck,
                buttons,
                messageLabel
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

        Scene scene = new Scene(root, 400, 280);
        popup.setScene(scene);
        popup.setResizable(false);
        popup.showAndWait();
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}