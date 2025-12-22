package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.OrderItem;
import com.example.oopii_finalproject.Managers.OrderItemManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemService {

    private final OrderItemManager orderItemManager = new OrderItemManager();

    private final HelperClass helperClass = new HelperClass();

    public ArrayList<OrderItem> getAllOrderItems() throws SQLException {
        return orderItemManager.getAllOrderItems();
    }

    public String addOrderItem(int orderId, int productId, int qty, double subtotal) throws SQLException {
        if (!helperClass.isValidOrderId(orderId)) return "Invalid order ID.";
        if (!helperClass.isValidProductId(productId)) return "Invalid product ID.";
        if (qty <= 0 || subtotal <= 0) return "Invalid values.";
        orderItemManager.addOrderItem(orderId, productId, qty, subtotal);
        return "Order item added successfully.";
    }

    public String updateOrderItem(int orderItemId, int orderId, int productId, int qty, double subtotal) throws SQLException {
        if (!helperClass.isValidOrderItemId(orderItemId)) return "Invalid order item ID.";
        if (!helperClass.isValidOrderId(orderId)) return "Invalid order ID.";
        if (!helperClass.isValidProductId(productId)) return "Invalid product ID.";
        orderItemManager.updateOrderItem(orderItemId, orderId, productId, qty, subtotal);
        return "Order item updated successfully.";
    }

    public String deleteOrderItem(int orderItemId) throws SQLException {
        if (!helperClass.isValidOrderItemId(orderItemId)) return "Invalid order item ID.";
        orderItemManager.deleteOrderItem(orderItemId);
        return "Order item deleted successfully.";
    }

    public String updateOrderItemQty(int orderItemId, int qty) throws SQLException {
        if (!helperClass.isValidOrderItemId(orderItemId)) return "Invalid order item ID.";
        if (qty <= 0) return "Invalid quantity.";
        orderItemManager.updateOrderItemQty(orderItemId, qty);
        return "Quantity updated successfully.";
    }

    public void closeConnection() throws SQLException {
        orderItemManager.closeConnection();
    }
}
