package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.DBConnection;
import com.example.oopii_finalproject.Objects.Review;

import java.sql.*;
import java.util.ArrayList;

public class ReviewManager {

    DBConnection dbConnection = new DBConnection();

    public ArrayList<Review> getAllReviews() throws SQLException {

        Connection connection = dbConnection.connect();

        ArrayList<Review> reviews = new ArrayList<>();

        String query = "SELECT * FROM reviews";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("reviews_id");
                int productId = resultSet.getInt("order_id");
                int userId = resultSet.getInt("user_id");
                int rating = resultSet.getInt("rating");
                String text = resultSet.getString("review_text");
                reviews.add(new Review(id, productId, userId, rating, text));
            }
        }
        return reviews;
    }

    public Review getReviewById(int reviewId) throws SQLException {

        Connection connection = dbConnection.connect();

        Review review = null;

        String query = "SELECT * FROM reviews WHERE reviews_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, reviewId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("reviews_id");
                int productId = resultSet.getInt("order_id");
                int userId = resultSet.getInt("user_id");
                int rating = resultSet.getInt("rating");
                String text = resultSet.getString("review_text");
                review = new Review(id, productId, userId, rating, text);
            }
        }
        return review;
    }

    public void addReview(int order_id, int userId, int rating, String text) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "INSERT INTO reviews (order_id, user_id, rating, review_text) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, order_id);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, rating);
            preparedStatement.setString(4, text);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteReview(int reviewId) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "DELETE FROM reviews WHERE reviews_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, reviewId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateReview(int reviewId, int order_id, int userId, int rating, String text) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE reviews SET order_id = ?, user_id = ?, rating = ?, review_text = ? WHERE reviews_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, order_id);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, rating);
            preparedStatement.setString(4, text);
            preparedStatement.setInt(5, reviewId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateReviewRating(int reviewId, int rating) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE reviews SET rating = ? WHERE reviews_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, rating);
            preparedStatement.setInt(2, reviewId);

            preparedStatement.executeUpdate();
        }
    }

    public void closeConnection() throws SQLException {
        dbConnection.disconnect();
    }
}
