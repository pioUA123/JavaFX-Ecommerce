package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.DBConnection;
import com.example.oopii_finalproject.Objects.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderManager {

    DBConnection dbConnection = new DBConnection();

    public ArrayList<Order> getAllOrders() throws SQLException {

        Connection connection = dbConnection.connect();

        ArrayList<Order> orders = new ArrayList<>();

        String query = "SELECT * FROM orders";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("orders_id");
                int userId = resultSet.getInt("user_id");
                double total = resultSet.getDouble("total");
                String status = resultSet.getString("order_status");
                orders.add(new Order(id, userId, total, status));
                System.out.println("Order ID: " + id + ", User ID: " + userId + ", Total: " + total + ", Status: " + status);
            }
        }
        return orders;
    }

    public Order getOrderById(int orderId) throws SQLException {

        Connection connection = dbConnection.connect();

        Order order = null;

        String query = "SELECT * FROM orders WHERE orders_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("orders_id");
                int userId = resultSet.getInt("user_id");
                double total = resultSet.getDouble("total");
                String status = resultSet.getString("order_status");
                order = new Order(id, userId, total, status);
                System.out.println("Order ID: " + id + ", User ID: " + userId + ", Total: " + total + ", Status: " + status);
            }
        }
        return order;
    }

    public void addOrder(int userId, double total, String orderStatus) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "INSERT INTO orders (user_id, total, order_status) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setDouble(2, total);
            preparedStatement.setString(3, orderStatus);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteOrder(int orderId) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "DELETE FROM orders WHERE orders_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateOrder(int orderId, int userId, double total, String orderStatus) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE orders SET user_id = ?, total = ?, order_status = ? WHERE orders_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setDouble(2, total);
            preparedStatement.setString(3, orderStatus);
            preparedStatement.setInt(4, orderId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateOrderStatus(int orderId, String orderStatus) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE orders SET order_status = ? WHERE orders_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, orderStatus);
            preparedStatement.setInt(2, orderId);

            preparedStatement.executeUpdate();
        }
    }

    // NEW: add order and return generated id, with shipping_location
    public int addOrderAndReturnId(int userId, double total, String orderStatus) throws SQLException {
        return addOrderAndReturnId(userId, total, orderStatus, null);
    }

    public int addOrderAndReturnId(int userId, double total, String orderStatus, String shippingLocation) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "INSERT INTO orders (user_id, total, order_status, shipping_location) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setDouble(2, total);
            preparedStatement.setString(3, orderStatus);
            preparedStatement.setString(4, shippingLocation);

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public void closeConnection() throws SQLException {
        dbConnection.disconnect();
    }
}