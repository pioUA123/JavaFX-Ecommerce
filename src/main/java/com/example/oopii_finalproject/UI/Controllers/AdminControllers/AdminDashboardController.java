package com.example.oopii_finalproject.UI.Controllers.AdminControllers;

import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.UserService;
import com.example.oopii_finalproject.UI.Frames.*;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.*;

import java.sql.SQLException;

public class AdminDashboardController {

    private final AdminDashboardFrame frame;
    private final UserService userService = new UserService();

    public AdminDashboardController(AdminDashboardFrame frame) {
        this.frame = frame;
    }

    public void openUserProfile() throws SQLException {
        new UserProfileFrame(frame.getStage(), getAdmin(1)).show();
    }

    public User getAdmin(int id) throws SQLException {
        return userService.getUserById(id);
    }

    public void openUserManagement() {
        new ManageUsersFrame(frame.getStage()).show();
    }

    public void openProductManagement() {
        new ManageProductsFrame(frame.getStage()).show();
    }

    public void openOrderManagement() {
        new ManageOrdersFrame(frame.getStage()).show();
    }

    public void openShipmentManagement() {
        new ManageShipmentsFrame(frame.getStage()).show();
    }

    public void openReviewManagement() {
        new ManageReviewsFrame(frame.getStage()).show();
    }

    public void openPromotionManagement() {
        new ManagePromotionsFrame(frame.getStage()).show();
    }

    public void openReports() {
        ReportsFrame reportsFrame = new ReportsFrame(frame.getStage());
        reportsFrame.show();
    }

    public void logout() {
        frame.redirectToLogin();
    }
}