package com.example.oopii_finalproject.UI.Controllers.AdminControllers;

import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.AuthService;
import com.example.oopii_finalproject.Services.UserService;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.ManageUsersFrame;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageUsersController {

    private final ManageUsersFrame frame;
    private final UserService userService = new UserService();
    private final AuthService authService = new AuthService();

    public ManageUsersController(ManageUsersFrame frame) {
        this.frame = frame;
    }

    public void loadUsers() {
        try {
            ArrayList<User> users = userService.getAllCustomers();
            frame.displayUsers(users);
        } catch (Exception e) {
            e.printStackTrace();
            frame.showInfo("Error loading users.");
        }
    }

    public String addUser(String username, String email, String password, String confirmPassword) {
        if (username == null || username.isBlank()) return "Username required.";
        if (email == null || email.isBlank()) return "Email required.";
        if (password == null || password.isBlank()) return "Password required.";
        if (!password.equals(confirmPassword)) return "Passwords do not match.";

        try {
            return authService.signUp(username, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error adding user.";
        }
    }

    public String updateUser(int userId, String username, String email, String password) {
        try {
            return userService.updateUser(userId, username, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating user.";
        }
    }

    public String toggleActive(User user) {
        boolean newState = !user.isActive();
        try {
            String msg = userService.toggleUserActive(user.getUserId(), newState);
            user.setActive(newState);
            return msg;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating user status.";
        }
    }

    public String deleteUser(int userId) {
        try {
            return userService.deleteUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting user.";
        }
    }
}