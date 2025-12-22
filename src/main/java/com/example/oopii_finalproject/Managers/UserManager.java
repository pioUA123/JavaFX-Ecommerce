package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.DBConnection;
import com.example.oopii_finalproject.Objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {

    DBConnection dbConnection = new DBConnection();

    public ArrayList<User> getAllCustomers() throws SQLException {

        Connection connection = dbConnection.connect();

        ArrayList<User> users = new ArrayList<>();

        String query = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String email = resultSet.getString("email");
                String userPassword = resultSet.getString("user_password");
                String userRole = resultSet.getString("user_role");
                double balance = resultSet.getDouble("balance");
                boolean active = resultSet.getBoolean("is_active");
                String profile = resultSet.getString("profile_picture_url");
                users.add(new User(id, userName, email, userPassword, userRole, balance, active, profile));
            }
        }
        return users;
    }

    public User getCustomerById(int userId) throws SQLException {

        Connection connection = dbConnection.connect();
        User user = null;

        String query = "SELECT * FROM users WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String email = resultSet.getString("email");
                String userPassword = resultSet.getString("user_password");
                String userRole = resultSet.getString("user_role");
                double balance = resultSet.getDouble("balance");
                boolean active = resultSet.getBoolean("is_active");
                String profile = resultSet.getString("profile_picture_url");
                user = new User(id, userName, email, userPassword, userRole, balance, active, profile);
            }
        }
        return user;
    }

    public void addCustomer(String username, String email, String password, double balance) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "INSERT INTO users (user_name, email, user_password, user_role, balance, profile_picture_url) VALUES (?, ?, ?, 'CUSTOMER', ?, 'Profile.jpg')";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setDouble(4, balance);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteCustomer(int userId) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "DELETE FROM users WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateCustomer(int userId, String username, String email, String password) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE users SET user_name = ?, email = ?, user_password = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, userId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateCustomerName(int userId, String username) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE users SET user_name = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateCustomerEmail(int userId, String email) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE users SET email = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateCustomerPassword(int userId, String password) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE users SET user_password = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateUserBalance(int userId, double balance) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE users SET balance = balance + ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateUserActive(int userId, boolean newState) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE users SET is_active = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, newState);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();
        }
    }
    public void closeConnection() throws SQLException {
        dbConnection.disconnect();
    }
}
