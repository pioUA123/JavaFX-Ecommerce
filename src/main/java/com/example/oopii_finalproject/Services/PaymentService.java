package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.Payment;
import com.example.oopii_finalproject.Managers.PaymentManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentService {

    private final PaymentManager paymentManager = new PaymentManager();

    private final HelperClass helperClass = new HelperClass();

    public ArrayList<Payment> getAllPayments() throws SQLException {
        return paymentManager.getAllPayments();
    }

    public String addPayment(int orderId, String method, String status, String txnRef) throws SQLException {
        if (!helperClass.isValidOrderId(orderId)) return "Invalid order ID.";
        if (method == null || method.isBlank()) return "Payment method required.";
        paymentManager.addPayment(orderId, method, status, txnRef);
        return "Payment added successfully.";
    }

    public String updatePayment(int paymentId, int orderId, String method, String status, String txnRef) throws SQLException {
        if (!helperClass.isValidPaymentId(paymentId)) return "Invalid payment ID.";
        if (!helperClass.isValidOrderId(orderId)) return "Invalid order ID.";
        paymentManager.updatePayment(paymentId, orderId, method, status, txnRef);
        return "Payment updated successfully.";
    }

    public String deletePayment(int paymentId) throws SQLException {
        if (!helperClass.isValidPaymentId(paymentId)) return "Invalid payment ID.";
        paymentManager.deletePayment(paymentId);
        return "Payment deleted successfully.";
    }

    public String updatePaymentStatus(int paymentId, String status) throws SQLException {
        if (!helperClass.isValidPaymentId(paymentId)) return "Invalid payment ID.";
        paymentManager.updatePaymentStatus(paymentId, status);
        return "Payment status updated successfully.";
    }

    public void closeConnection() throws SQLException {
        paymentManager.closeConnection();
    }
}

