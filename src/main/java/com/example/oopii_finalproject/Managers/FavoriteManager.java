package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.DBConnection;
import com.example.oopii_finalproject.Objects.Favorite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FavoriteManager {

    DBConnection dbConnection = new DBConnection();

    public ArrayList<Favorite> getFavoritesByUser(int userId) throws SQLException {
        Connection connection = dbConnection.connect();

        ArrayList<Favorite> favorites = new ArrayList<>();

        String query = "SELECT * FROM favorites WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("favorite_id");
                int uid = resultSet.getInt("user_id");
                int pid = resultSet.getInt("product_id");
                favorites.add(new Favorite(id, uid, pid));
            }
        }

        return favorites;
    }

    public boolean isFavorite(int userId, int productId) throws SQLException {
        Connection connection = dbConnection.connect();

        String query = "SELECT COUNT(*) AS cnt FROM favorites WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("cnt");
                return count > 0;
            }
        }

        return false;
    }

    public void addFavorite(int userId, int productId) throws SQLException {
        Connection connection = dbConnection.connect();

        String query = "INSERT INTO favorites (user_id, product_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteFavorite(int userId, int productId) throws SQLException {
        Connection connection = dbConnection.connect();

        String query = "DELETE FROM favorites WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();
        }
    }

    public void closeConnection() throws SQLException {
        dbConnection.disconnect();
    }
}