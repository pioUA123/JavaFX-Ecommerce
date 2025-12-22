package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.Shipment;
import com.example.oopii_finalproject.Managers.ShipmentManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShipmentService {

    private final ShipmentManager shipmentManager = new ShipmentManager();

    private final HelperClass helperClass = new HelperClass();

    public ArrayList<Shipment> getAllShipments() throws SQLException {
        return shipmentManager.getAllShipments();
    }

    public String addShipment(int orderId, String provider, String tracking, Date estimatedDelivery, String status) throws SQLException {
        if (!helperClass.isValidOrderId(orderId)) return "Invalid order ID.";
        if (provider == null || provider.isBlank()) return "Provider required.";
        shipmentManager.addShipment(orderId, provider, tracking, estimatedDelivery, status);
        return "Shipment added successfully.";
    }

    public String updateShipment(int shipmentId, int orderId, String provider, String tracking, Date estimatedDelivery, String status) throws SQLException {
        if (!helperClass.isValidShipmentId(shipmentId)) return "Invalid shipment ID.";
        if (!helperClass.isValidOrderId(orderId)) return "Invalid order ID.";
        shipmentManager.updateShipment(shipmentId, orderId, provider, tracking, estimatedDelivery, status);
        return "Shipment updated successfully.";
    }

    public String deleteShipment(int shipmentId) throws SQLException {
        if (!helperClass.isValidShipmentId(shipmentId)) return "Invalid shipment ID.";
        shipmentManager.deleteShipment(shipmentId);
        return "Shipment deleted successfully.";
    }

    public String updateShipmentStatus(int shipmentId, String status) throws SQLException {
        if (!helperClass.isValidShipmentId(shipmentId)) return "Invalid shipment ID.";
        shipmentManager.updateShipmentStatus(shipmentId, status);
        return "Shipment status updated successfully.";
    }

    public void closeConnection() throws SQLException {
        shipmentManager.closeConnection();
    }
}
