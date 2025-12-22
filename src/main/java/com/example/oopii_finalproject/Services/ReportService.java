package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.Order;
import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.Objects.Payment;
import com.example.oopii_finalproject.Managers.OrderManager;
import com.example.oopii_finalproject.Managers.ProductManager;
import com.example.oopii_finalproject.Managers.PaymentManager;

import java.sql.SQLException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReportService {

    private final OrderManager orderManager = new OrderManager();
    private final ProductManager productManager = new ProductManager();
    private final PaymentManager paymentManager = new PaymentManager();

    public String generateSalesReport(String filePath) throws SQLException, IOException {
        ArrayList<Order> orders = orderManager.getAllOrders();
        ArrayList<Payment> payments = paymentManager.getAllPayments();

        double totalSales = 0;
        for (Payment p : payments) {
            Order order = orders.stream().filter(o -> o.getOrderId() == p.getOrderId()).findFirst().orElse(null);
            if (order != null) totalSales += order.getTotalAmount();
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("=== Sales Report ===\n");
            writer.write("Total Payments: " + payments.size() + "\n");
            writer.write("Total Sales Amount: $" + totalSales + "\n");
        }

        return "Sales report generated.";
    }

    public String generateInventoryReport(String filePath) throws SQLException, IOException {
        ArrayList<Product> products = productManager.getAllProducts();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("=== Inventory Report ===\n");
            for (Product product : products) {
                writer.write(product.getProductName() + " | Stock: " + product.getProductStock() + " | Price: $" + product.getProductPrice() + "\n");
            }
        }
        return "Inventory report generated.";
    }

    public String generateOrderReport(String filePath) throws SQLException, IOException {
        ArrayList<Order> orders = orderManager.getAllOrders();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("=== Order Report ===\n");
            for (Order order : orders) {
                writer.write("Order ID: " + order.getOrderId() + " | User ID: " + order.getUserId() + " | Total: $" + order.getTotalAmount() + " | Status: " + order.getOrderStatus() + "\n");
            }
        }
        return "Order report generated.";
    }

    public void closeConnection() throws SQLException {
        orderManager.closeConnection();
        productManager.closeConnection();
        paymentManager.closeConnection();
    }
}
