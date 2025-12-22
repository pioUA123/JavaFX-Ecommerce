package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Managers.ShipmentManager;
import com.example.oopii_finalproject.Objects.Shipment;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderProcessingThread extends Thread {

    private final int orderId;
    private final OrderService orderService = new OrderService();
    private final ShipmentManager shipmentManager = new ShipmentManager();

    public OrderProcessingThread(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public void run() {
        int shipmentId;

        try {
            shipmentId = findShipmentIdForOrder(orderId);

            Thread.sleep(5000);
            orderService.updateOrderStatus(orderId, "SHIPPED");
            shipmentManager.updateShipmentStatus(shipmentId, "IN_TRANSIT");

            Thread.sleep(5000);
            orderService.updateOrderStatus(orderId, "DELIVERED");
            shipmentManager.updateShipmentStatus(shipmentId, "DELIVERED");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                orderService.closeConnection();
                shipmentManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Integer findShipmentIdForOrder(int orderId) throws SQLException {
        ArrayList<Shipment> shipments = shipmentManager.getAllShipments();
        for (Shipment shipment : shipments) {
            if (shipment.getOrderId() == orderId) {
                return shipment.getShipmentId();
            }
        }
        return null;
    }
}