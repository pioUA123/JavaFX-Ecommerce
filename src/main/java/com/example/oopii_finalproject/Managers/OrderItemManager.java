package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.DBConnection;
import com.example.oopii_finalproject.Objects.OrderItem;

import java.sql.*;
import java.util.ArrayList;

public class OrderItemManager {

    DBConnection dbConnection = new DBConnection();

    public ArrayList<OrderItem> getAllOrderItems() throws SQLException {

        Connection connection = dbConnection.connect();

        ArrayList<OrderItem> items = new ArrayList<>();

        String query = "SELECT * FROM order_items";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("order_items_id");
                int orderId = resultSet.getInt("order_id");
                int productId = resultSet.getInt("product_id");
                int qty = resultSet.getInt("qty");
                double subtotal = resultSet.getDouble("subtotal");
                items.add(new OrderItem(id, orderId, productId, qty, subtotal));
            }
        }
        return items;
    }

    public OrderItem getOrderItemById(int orderItemId) throws SQLException {

        Connection connection = dbConnection.connect();

        OrderItem item = null;

        String query = "SELECT * FROM order_items WHERE order_items_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderItemId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("order_items_id");
                int orderId = resultSet.getInt("order_id");
                int productId = resultSet.getInt("product_id");
                int qty = resultSet.getInt("qty");
                double subtotal = resultSet.getDouble("subtotal");
                item = new OrderItem(id, orderId, productId, qty, subtotal);
            }
        }
        return item;
    }

    public void addOrderItem(int orderId, int productId, int qty, double subtotal) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "INSERT INTO order_items (order_id, product_id, qty, subtotal) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, qty);
            preparedStatement.setDouble(4, subtotal);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteOrderItem(int orderItemId) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "DELETE FROM order_items WHERE order_items_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderItemId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateOrderItem(int orderItemId, int orderId, int productId, int qty, double subtotal) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE order_items SET order_id = ?, product_id = ?, qty = ?, subtotal = ? WHERE order_items_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, qty);
            preparedStatement.setDouble(4, subtotal);
            preparedStatement.setInt(5, orderItemId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateOrderItemQty(int orderItemId, int qty) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE order_items SET qty = ? WHERE order_items_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, qty);
            preparedStatement.setInt(2, orderItemId);

            preparedStatement.executeUpdate();
        }
    }

    public void closeConnection() throws SQLException {
        dbConnection.disconnect();
    }
}
