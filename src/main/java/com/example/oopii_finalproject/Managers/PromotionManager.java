package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.DBConnection;
import com.example.oopii_finalproject.Objects.Promotion;

import java.sql.*;
import java.util.ArrayList;

public class PromotionManager {

    DBConnection dbConnection = new DBConnection();

    public ArrayList<Promotion> getAllPromotions() throws SQLException {

        Connection connection = dbConnection.connect();

        ArrayList<Promotion> promotions = new ArrayList<>();

        String query = "SELECT * FROM promotions";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("promotions_id");
                String code = resultSet.getString("p_code");
                int value = resultSet.getInt("p_value");
                boolean active = resultSet.getBoolean("p_active");
                promotions.add(new Promotion(id, code, value, active));
            }
        }
        return promotions;
    }

    public Promotion getPromotionById(int promotionId) throws SQLException {

        Connection connection = dbConnection.connect();

        Promotion promotion = null;

        String query = "SELECT * FROM promotions WHERE promotions_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, promotionId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("promotions_id");
                String code = resultSet.getString("p_code");
                int value = resultSet.getInt("p_value");
                boolean active = resultSet.getBoolean("p_active");
                promotion = new Promotion(id, code, value, active);
            }
        }
        return promotion;
    }

    public void addPromotion(String code, int value, boolean active) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "INSERT INTO promotions (p_code, p_value, p_active) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, code);
            preparedStatement.setInt(2, value);
            preparedStatement.setBoolean(3, active);

            preparedStatement.executeUpdate();
        }
    }

    public void deletePromotion(int promotionId) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "DELETE FROM promotions WHERE promotions_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, promotionId);

            preparedStatement.executeUpdate();
        }
    }

    public void updatePromotion(int promotionId, String code, int value, boolean active) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE promotions SET p_code = ?, p_value = ?, p_active = ? WHERE promotions_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, code);
            preparedStatement.setInt(2, value);
            preparedStatement.setBoolean(3, active);
            preparedStatement.setInt(4, promotionId);

            preparedStatement.executeUpdate();
        }
    }

    public void updatePromotionActive(int promotionId, boolean active) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE promotions SET p_active = ? WHERE promotions_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, active);
            preparedStatement.setInt(2, promotionId);

            preparedStatement.executeUpdate();
        }
    }

    public void closeConnection() throws SQLException {
        dbConnection.disconnect();
    }
}
