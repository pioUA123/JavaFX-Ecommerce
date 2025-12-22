package com.example.oopii_finalproject.UI.Controllers.AdminControllers;

import com.example.oopii_finalproject.Objects.Shipment;
import com.example.oopii_finalproject.Services.ShipmentService;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.ManageShipmentsFrame;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageShipmentsController {

    private final ManageShipmentsFrame frame;
    private final ShipmentService shipmentService = new ShipmentService();

    public ManageShipmentsController(ManageShipmentsFrame frame) {
        this.frame = frame;
    }

    public void loadShipments() {
        try {
            ArrayList<Shipment> shipments = shipmentService.getAllShipments();
            frame.displayShipments(shipments);
        } catch (SQLException e) {
            e.printStackTrace();
            frame.showInfo("Error loading shipments.");
        }
    }

    public String updateShipment(
            int shipmentId,
            int orderId,
            String provider,
            String tracking,
            Date estimatedDelivery,
            String status
    ) {
        try {
            return shipmentService.updateShipment(shipmentId, orderId, provider, tracking, estimatedDelivery, status);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating shipment.";
        }
    }

    public String updateStatus(int shipmentId, String status) {
        try {
            return shipmentService.updateShipmentStatus(shipmentId, status);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating shipment status.";
        }
    }
}