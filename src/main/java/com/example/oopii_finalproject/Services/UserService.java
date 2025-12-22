package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Managers.UserManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class UserService {

    private final UserManager userManager = new UserManager();

    private final HelperClass helperClass = new HelperClass();

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public ArrayList<User> getAllCustomers() throws SQLException {
        return userManager.getAllCustomers();
    }

    public String updateUser(int userId, String username, String email, String password) throws SQLException {
        if (!helperClass.isValidUserId(userId)) return "Invalid user ID.";

        if (email != null && !EMAIL_PATTERN.matcher(email).matches()) {
            return "Invalid email format.";
        }

        userManager.updateCustomer(userId, username, email, password);
        return "User updated successfully.";
    }

    public String deleteUser(int userId) throws SQLException {
        if (!helperClass.isValidUserId(userId)) return "Invalid user ID.";
        userManager.deleteCustomer(userId);
        return "User deleted successfully.";
    }

    public boolean isEmailUnique(String email) throws SQLException {
        ArrayList<User> users = userManager.getAllCustomers();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }

    public String updateUserName(int userId, String newName) throws SQLException {
        if (!helperClass.isValidUserId(userId)) {
            return "Invalid user ID.";
        }

        if (newName == null || newName.isBlank()) {
            return "Name cannot be empty.";
        }
        userManager.updateCustomerName(userId, newName);
        return "Username updated successfully.";
    }

    public String updateUserEmail(int userId, String newEmail) throws SQLException {
        if (!helperClass.isValidUserId(userId)) {
            return "Invalid user ID.";
        }

        if (!EMAIL_PATTERN.matcher(newEmail).matches()) {
            return "Invalid email format.";
        }
        if (!isEmailUnique(newEmail)) {
            return "Email already exists.";
        }
        userManager.updateCustomerEmail(userId, newEmail);
        return "Email updated successfully.";
    }

    public String updateUserPassword(int userId, String newPassword) throws SQLException {
        if (!helperClass.isValidUserId(userId)) {
            return "Invalid user ID.";
        }

        if (newPassword == null || newPassword.length() < 4) {
            return "Password too short.";
        }
        userManager.updateCustomerPassword(userId, newPassword);
        return "Password updated successfully.";
    }

    public String updateUserBalance(int userId, String newBalance) throws SQLException {
        if (!helperClass.isValidUserId(userId)) {
            return "Invalid user ID.";
        }

        double parsedBalance;
        try {
            parsedBalance = Double.parseDouble(newBalance);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        userManager.updateUserBalance(userId, parsedBalance);
        return "Balance updated successfully.";
    }

    public String toggleUserActive(int userId, boolean newState) throws SQLException {
        if (!helperClass.isValidUserId(userId)) return "Invalid user ID.";
        userManager.updateUserActive(userId, newState);
        return newState ? "User activated successfully." : "User deactivated successfully.";
    }

    public User getUserById(int userId) throws SQLException {
        if (!helperClass.isValidUserId(userId)) return null;
        return userManager.getCustomerById(userId);
    }

    public void closeConnection() throws SQLException {
        userManager.closeConnection();
    }
}

