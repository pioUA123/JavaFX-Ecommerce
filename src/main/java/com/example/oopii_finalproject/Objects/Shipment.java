package com.example.oopii_finalproject.Objects;

import java.sql.Date;
import java.sql.Timestamp;

public class Shipment {
    private final int shipmentId;
    private final int orderId;
    private final String shippingProvider;
    private final String trackingNumber;
    private final Date estimatedDelivery;
    private final String shipmentStatus;
    private final Timestamp createdAt;

    public Shipment(int shipmentId, int orderId, String shippingProvider, String trackingNumber, Date estimatedDelivery, String shipmentStatus, Timestamp createdAt) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.shipmentStatus = shipmentStatus;
        this.trackingNumber = trackingNumber;
        this.shippingProvider = shippingProvider;
        this.estimatedDelivery = estimatedDelivery;
        this.createdAt = createdAt;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getShippingProvider() {
        return shippingProvider;
    }

    public Date getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
