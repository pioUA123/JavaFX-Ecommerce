package com.example.oopii_finalproject.Objects;

public class Order {
    private final int orderId;
    private final int userId;
    private final double totalAmount;
    private final String orderStatus;

    public Order(int orderId, int userId, double totalAmount, String orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
