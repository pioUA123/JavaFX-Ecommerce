package com.example.oopii_finalproject.UI.Controllers.AdminControllers;

import com.example.oopii_finalproject.Services.ReportService;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.ReportsFrame;

import java.sql.SQLException;
import java.io.IOException;

public class ReportsController {

    private final ReportsFrame frame;
    private final ReportService reportService = new ReportService();

    private static final String SALES_REPORT_PATH = "src/main/java/com/example/oopii_finalproject/salesReports";
    private static final String INVENTORY_REPORT_PATH = "src/main/java/com/example/oopii_finalproject/inventoryReport";
    private static final String ORDERS_REPORT_PATH = "src/main/java/com/example/oopii_finalproject/ordersReport";

    public ReportsController(ReportsFrame frame) {
        this.frame = frame;
    }

    public void generateSalesReport() {
        try {
            String msg = reportService.generateSalesReport(SALES_REPORT_PATH);
            frame.showInfo(msg + "\nSaved to: " + SALES_REPORT_PATH);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            frame.showInfo("Error generating sales report.");
        }
    }

    public void generateInventoryReport() {
        try {
            String msg = reportService.generateInventoryReport(INVENTORY_REPORT_PATH);
            frame.showInfo(msg + "\nSaved to: " + INVENTORY_REPORT_PATH);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            frame.showInfo("Error generating inventory report.");
        }
    }

    public void generateOrderReport() {
        try {
            String msg = reportService.generateOrderReport(ORDERS_REPORT_PATH);
            frame.showInfo(msg + "\nSaved to: " + ORDERS_REPORT_PATH);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            frame.showInfo("Error generating order report.");
        }
    }
}
