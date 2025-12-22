package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.DBConnection;
import com.example.oopii_finalproject.Objects.Shipment;

import java.sql.*;
import java.util.ArrayList;

public class ShipmentManager {

    DBConnection dbConnection = new DBConnection();

    public ArrayList<Shipment> getAllShipments() throws SQLException {

        Connection connection = dbConnection.connect();

        ArrayList<Shipment> shipments = new ArrayList<>();

        String query = "SELECT * FROM shipments";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("shipments_id");
                int orderId = resultSet.getInt("order_id");
                String provider = resultSet.getString("shipping_provider");
                String tracking = resultSet.getString("tracking_number");
                Date est = resultSet.getDate("estimated_delivery");
                String status = resultSet.getString("shipping_status");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                shipments.add(new Shipment(id, orderId, provider, tracking, est, status, createdAt));
            }
        }
        return shipments;
    }

    public Shipment getShipmentById(int shipmentId) throws SQLException {

        Connection connection = dbConnection.connect();

        Shipment shipment = null;

        String query = "SELECT * FROM shipments WHERE shipments_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, shipmentId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("shipments_id");
                int orderId = resultSet.getInt("order_id");
                String provider = resultSet.getString("shipping_provider");
                String tracking = resultSet.getString("tracking_number");
                Date est = resultSet.getDate("estimated_delivery");
                String status = resultSet.getString("shipping_status");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                shipment = new Shipment(id, orderId, provider, tracking, est, status, createdAt);
            }
        }
        return shipment;
    }

    public void addShipment(int orderId, String provider, String trackingNumber, Date estimatedDelivery, String shippingStatus) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "INSERT INTO shipments (order_id, shipping_provider, tracking_number, estimated_delivery, shipping_status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, provider);
            preparedStatement.setString(3, trackingNumber);
            preparedStatement.setDate(4, estimatedDelivery);
            preparedStatement.setString(5, shippingStatus);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteShipment(int shipmentId) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "DELETE FROM shipments WHERE shipments_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, shipmentId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateShipment(int shipmentId, int orderId, String provider, String trackingNumber, Date estimatedDelivery, String shippingStatus) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE shipments SET order_id = ?, shipping_provider = ?, tracking_number = ?, estimated_delivery = ?, shipping_status = ? WHERE shipments_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, provider);
            preparedStatement.setString(3, trackingNumber);
            preparedStatement.setDate(4, estimatedDelivery);
            preparedStatement.setString(5, shippingStatus);
            preparedStatement.setInt(6, shipmentId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateShipmentStatus(int shipmentId, String shippingStatus) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE shipments SET shipping_status = ? WHERE shipments_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, shippingStatus);
            preparedStatement.setInt(2, shipmentId);

            preparedStatement.executeUpdate();
        }
    }

    public void closeConnection() throws SQLException {
        dbConnection.disconnect();
    }
}
