package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.Review;
import com.example.oopii_finalproject.Managers.ReviewManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewService {

    private final ReviewManager reviewManager = new ReviewManager();

    private final HelperClass helperClass = new HelperClass();

    public ArrayList<Review> getAllReviews() throws SQLException {
        return reviewManager.getAllReviews();
    }

    public String addOrderReview(int orderId, int userId, int rating, String text) throws SQLException {
        if (!helperClass.isValidOrderId(orderId)) return "Invalid product ID.";
        if (!helperClass.isValidUserId(userId)) return "Invalid user ID.";
        if (rating < 1 || rating > 5) return "Rating must be 1–5.";
        reviewManager.addReview(orderId, userId, rating, text);
        return "Review added successfully.";
    }

    public String deleteReview(int reviewId) throws SQLException {
        if (!helperClass.isValidReviewId(reviewId)) return "Invalid review ID.";
        reviewManager.deleteReview(reviewId);
        return "Review deleted successfully.";
    }

    public String updateReviewRating(int reviewId, int rating) throws SQLException {
        if (!helperClass.isValidReviewId(reviewId)) return "Invalid review ID.";
        if (rating < 1 || rating > 5) return "Rating must be 1–5.";
        reviewManager.updateReviewRating(reviewId, rating);
        return "Rating updated successfully.";
    }

    public void closeConnection() throws SQLException {
        reviewManager.closeConnection();
    }
}
