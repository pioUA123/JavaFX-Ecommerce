package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Managers.ShipmentManager;
import com.example.oopii_finalproject.Objects.Order;
import com.example.oopii_finalproject.Managers.OrderManager;
import com.example.oopii_finalproject.Objects.Shipment;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderService {

    private final OrderManager orderManager = new OrderManager();

    private final HelperClass helperClass = new HelperClass();

    public ArrayList<Order> getAllOrders() throws SQLException {
        return orderManager.getAllOrders();
    }

    public String addOrder(int userId, double total, String status) throws SQLException {
        if (!helperClass.isValidUserId(userId)) return "Invalid user ID.";
        if (total <= 0) return "Invalid total.";
        orderManager.addOrder(userId, total, status);
        return "Order added successfully.";
    }

    public String updateOrder(int orderId, int userId, double total, String status) throws SQLException {
        if (!helperClass.isValidUserId(userId)) return "Invalid user ID.";
        if (!helperClass.isValidOrderId(orderId)) return "Invalid order ID.";
        orderManager.updateOrder(orderId, userId, total, status);
        return "Order updated successfully.";
    }

    public String deleteOrder(int orderId) throws SQLException {
        if (!helperClass.isValidOrderId(orderId)) return "Invalid order ID.";
        orderManager.deleteOrder(orderId);
        return "Order deleted successfully.";
    }

    public String updateOrderStatus(int orderId, String status) throws SQLException {
        if (!helperClass.isValidOrderId(orderId)) return "Invalid order ID.";
        orderManager.updateOrderStatus(orderId, status);
        return "Order status updated successfully.";
    }

    public String cancelOrderByCustomer(int orderId, int userId) throws SQLException {
        Order order = orderManager.getOrderById(orderId);
        if (order == null) {
            return "Order not found.";
        }
        if (order.getUserId() != userId) {
            return "You cannot cancel this order.";
        }
        if (!"PENDING".equalsIgnoreCase(order.getOrderStatus())) {
            return "This order cannot be cancelled now.";
        }
        orderManager.updateOrderStatus(orderId, "CANCELLED");
        ShipmentManager shipmentManager = new ShipmentManager();
        ArrayList<Shipment> shipments = shipmentManager.getAllShipments();
        for (Shipment shipment : shipments) {
            if (shipment.getOrderId() == orderId) {
                shipmentManager.updateShipmentStatus(shipment.getShipmentId(), "CANCELLED");
            }
        }
        shipmentManager.closeConnection();

        return "Order cancelled successfully.";
    }


    public void closeConnection() throws SQLException {
        orderManager.closeConnection();
    }
}
