package com.example.oopii_finalproject.UI.Controllers.AdminControllers;

import com.example.oopii_finalproject.Objects.*;
import com.example.oopii_finalproject.Services.*;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.ManageOrdersFrame;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrdersController {

    private final ManageOrdersFrame frame;
    private final OrderService orderService = new OrderService();
    private final UserService userService = new UserService();
    private final OrderItemService orderItemService = new OrderItemService();
    private final ProductService productService = new ProductService();
    private final ShipmentService shipmentService = new ShipmentService();

    private ArrayList<User> allUsers = new ArrayList<>();

    public ManageOrdersController(ManageOrdersFrame frame) {
        this.frame = frame;
    }

    public void loadOrders() {
        try {
            ArrayList<Order> orders = orderService.getAllOrders();
            allUsers = userService.getAllCustomers();
            frame.displayOrders(orders);
        } catch (SQLException e) {
            e.printStackTrace();
            frame.showInfo("Error loading orders.");
        }
    }

    public String getCustomerName(int userId) {
        for (User user : allUsers) {
            if (user.getUserId() == userId) {
                return user.getUsername();
            }
        }
        return "User #" + userId;
    }

    public String processOrder(Order order) {
        String status = order.getOrderStatus();
        if (!"PENDING".equalsIgnoreCase(status)) {
            return "Only pending orders can be processed.";
        }

        try {
            // Set to PROCESSING immediately
            orderService.updateOrderStatus(order.getOrderId(), "PROCESSING");

            // Start thread to go to SHIPPED and DELIVERED
            OrderProcessingThread thread = new OrderProcessingThread(order.getOrderId());
            thread.start();

            return "Order processing started.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error processing order.";
        }
    }

    public String cancelOrder(Order order) {
        String status = order.getOrderStatus();
        if (!"PENDING".equalsIgnoreCase(status)) {
            return "Only pending orders can be cancelled.";
        }

        try {
            orderService.updateOrderStatus(order.getOrderId(), "CANCELLED");

            Integer shipmentId = findShipmentIdForOrder(order.getOrderId());
            if (shipmentId != null) {
                shipmentService.updateShipmentStatus(shipmentId, "CANCELLED");
            }

            return "Order cancelled.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error cancelling order.";
        }
    }

    public void openOrderDetails(Order order) {
        try {
            ArrayList<OrderItem> allItems = orderItemService.getAllOrderItems();
            ArrayList<OrderItem> itemsForThisOrder = new ArrayList<>();
            for (OrderItem item : allItems) {
                if (item.getOrderId() == order.getOrderId()) {
                    itemsForThisOrder.add(item);
                }
            }

            ArrayList<Product> products = productService.getAllProducts();

            frame.showOrderDetails(order, itemsForThisOrder, products);

        } catch (SQLException e) {
            e.printStackTrace();
            frame.showInfo("Error loading order details.");
        }
    }

    private Integer findShipmentIdForOrder(int orderId) throws SQLException {
        ArrayList<Shipment> shipments = shipmentService.getAllShipments();
        for (Shipment shipment : shipments) {
            if (shipment.getOrderId() == orderId) {
                return shipment.getShipmentId();
            }
        }
        return null;
    }

}
