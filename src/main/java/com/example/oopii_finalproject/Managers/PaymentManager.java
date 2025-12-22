package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.DBConnection;
import com.example.oopii_finalproject.Objects.Payment;

import java.sql.*;
import java.util.ArrayList;

public class PaymentManager {

    DBConnection dbConnection = new DBConnection();

    public ArrayList<Payment> getAllPayments() throws SQLException {

        Connection connection = dbConnection.connect();

        ArrayList<Payment> payments = new ArrayList<>();

        String query = "SELECT * FROM payments";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("payments_id");
                int orderId = resultSet.getInt("order_id");
                String method = resultSet.getString("method");
                String status = resultSet.getString("payment_status");
                String txnRef = resultSet.getString("txn_ref");
                payments.add(new Payment(id, orderId, method, status, txnRef));
            }
        }
        return payments;
    }

    public Payment getPaymentById(int paymentId) throws SQLException {

        Connection connection = dbConnection.connect();

        Payment payment = null;

        String query = "SELECT * FROM payments WHERE payments_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, paymentId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("payments_id");
                int orderId = resultSet.getInt("order_id");
                String method = resultSet.getString("method");
                String status = resultSet.getString("payment_status");
                String txnRef = resultSet.getString("txn_ref");
                payment = new Payment(id, orderId, method, status, txnRef);
            }
        }
        return payment;
    }

    public void addPayment(int orderId, String method, String paymentStatus, String txnRef) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "INSERT INTO payments (order_id, method, payment_status, txn_ref) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, method);
            preparedStatement.setString(3, paymentStatus);
            preparedStatement.setString(4, txnRef);

            preparedStatement.executeUpdate();
        }
    }

    public void deletePayment(int paymentId) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "DELETE FROM payments WHERE payments_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, paymentId);

            preparedStatement.executeUpdate();
        }
    }

    public void updatePayment(int paymentId, int orderId, String method, String paymentStatus, String txnRef) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE payments SET order_id = ?, method = ?, payment_status = ?, txn_ref = ? WHERE payments_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, method);
            preparedStatement.setString(3, paymentStatus);
            preparedStatement.setString(4, txnRef);
            preparedStatement.setInt(5, paymentId);

            preparedStatement.executeUpdate();
        }
    }

    public void updatePaymentStatus(int paymentId, String paymentStatus) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE payments SET payment_status = ? WHERE payments_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, paymentStatus);
            preparedStatement.setInt(2, paymentId);

            preparedStatement.executeUpdate();
        }
    }

    public void closeConnection() throws SQLException {
        dbConnection.disconnect();
    }
}
