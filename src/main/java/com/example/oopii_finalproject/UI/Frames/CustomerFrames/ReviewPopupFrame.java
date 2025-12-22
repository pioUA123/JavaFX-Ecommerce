package com.example.oopii_finalproject.UI.Frames.CustomerFrames;

import com.example.oopii_finalproject.UI.Controllers.CustomerControllers.ReviewPopupController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReviewPopupFrame {

    private final OrderHistoryFrame ownerFrame;
    private final int orderId;
    private final int userId;
    private final ReviewPopupController reviewPopupController;

    public ReviewPopupFrame(OrderHistoryFrame ownerFrame, int orderId, int userId) {
        this.ownerFrame = ownerFrame;
        this.orderId = orderId;
        this.userId = userId;
        this.reviewPopupController = new ReviewPopupController(this, orderId, userId);
    }

    public void show() {
        Stage stage = new Stage();
        stage.initOwner(ownerFrame.getOwnerStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Write Review");

        TextField ratingField = new TextField();
        ratingField.setPromptText("Rating (1–5)");

        TextArea commentField = new TextArea();
        commentField.setPromptText("Write your review...");

        Button submit = new Button("Submit");
        submit.setOnAction(e ->
                reviewPopupController.submitReview(ratingField.getText(), commentField.getText())
        );

        VBox layout = new VBox(15, ratingField, commentField, submit);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    public Stage getOwnerStage() {
        return ownerFrame.getOwnerStage();
    }
}
