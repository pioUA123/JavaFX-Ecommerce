package com.example.oopii_finalproject.Objects;

public class Review {
    private final int reviewId;
    private final int orderId;
    private final int userId;
    private final int rating;
    private final String reviewText;

    public Review(int reviewId, int orderId, int userId, int rating, String comment) {
        this.reviewId = reviewId;
        this.orderId = orderId;
        this.userId = userId;
        this.rating = rating;
        this.reviewText = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }
}
