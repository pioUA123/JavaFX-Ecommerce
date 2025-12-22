package com.example.oopii_finalproject.UI.Controllers.CustomerControllers;

import com.example.oopii_finalproject.Objects.Review;
import com.example.oopii_finalproject.Services.ReviewService;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.ReviewPopupFrame;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewPopupController {

    private final ReviewPopupFrame frame;
    private final int orderId;
    private final int userId;

    private final ReviewService reviewService = new ReviewService();

    public ReviewPopupController(ReviewPopupFrame frame, int orderId, int userId) {
        this.frame = frame;
        this.orderId = orderId;
        this.userId = userId;
    }

    public void submitReview(String ratingText, String comment) {
        try {
            int rating = Integer.parseInt(ratingText);

            ArrayList<Review> all = reviewService.getAllReviews();
            for (Review r : all) {
                if (r.getOrderId() == orderId && r.getUserId() == userId) {
                    frame.showInfo("You already reviewed this order.");
                    return;
                }
            }

            if(rating < 1 || rating > 5){
                frame.showInfo("Rating should be between 1 and 5.");
                return;
            }

            String msg = reviewService.addOrderReview(orderId, userId, rating, comment);
            frame.showInfo(msg);

        } catch (NumberFormatException e) {
            frame.showInfo("Invalid rating.");
        } catch (SQLException e) {
            frame.showInfo("Error submitting review.");
        }
    }
}
