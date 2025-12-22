package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.*;
import com.example.oopii_finalproject.Managers.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class HelperClass {

    private final UserManager userManager = new UserManager();

    private final ProductManager productManager = new ProductManager();

    private final OrderManager orderManager = new OrderManager();

    private final OrderItemManager orderItemManager = new OrderItemManager();

    private final PromotionManager promotionManager = new PromotionManager();

    private final ReviewManager reviewManager = new ReviewManager();

    private final ShipmentManager shipmentManager = new ShipmentManager();

    private final PaymentManager paymentManager = new PaymentManager();

    public boolean isValidUserId(int userId) throws SQLException {
        ArrayList<User> users = userManager.getAllCustomers();
        for (User user : users) {
            if (user.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidProductId(int productId) throws SQLException {
        ArrayList<Product> products = productManager.getAllProducts();
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidOrderId(int orderId) throws SQLException {
        ArrayList<Order> orders = orderManager.getAllOrders();
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidOrderItemId(int orderItemId) throws SQLException {
        ArrayList<OrderItem> orderItems = orderItemManager.getAllOrderItems();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrderItemId() == orderItemId) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidPromotionId(int promotionId) throws SQLException {
        ArrayList<Promotion> promotions = promotionManager.getAllPromotions();
        for (Promotion promotion : promotions) {
            if (promotion.getPromoId() == promotionId) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidReviewId(int reviewId) throws SQLException {
        ArrayList<Review> reviews = reviewManager.getAllReviews();
        for (Review review : reviews) {
            if (review.getReviewId() == reviewId) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidShipmentId(int shipmentId) throws SQLException {
        ArrayList<Shipment> shipments = shipmentManager.getAllShipments();
        for (Shipment shipment : shipments) {
            if (shipment.getShipmentId() == shipmentId) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidPaymentId(int paymentId) throws SQLException {
        ArrayList<Payment> payments = paymentManager.getAllPayments();
        for (Payment payment : payments) {
            if (payment.getPaymentId() == paymentId) {
                return true;
            }
        }
        return false;
    }
}
