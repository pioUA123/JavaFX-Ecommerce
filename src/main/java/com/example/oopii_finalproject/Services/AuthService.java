package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Managers.UserManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AuthService {

    private final UserManager userManager = new UserManager();

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public User login(String email, String password) throws SQLException {
        ArrayList<User> users = userManager.getAllCustomers();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                if (!user.isActive())
                    return null;
                return user;
            }
        }
        return null;
    }

    public String signUp(String username, String email, String password) throws SQLException {
        if (username == null || username.isBlank()) return "Username required.";
        if (email == null || email.isBlank()) return "Email required.";
        if (password == null || password.isBlank()) return "Password required.";
        if (!EMAIL_PATTERN.matcher(email).matches()) return "Invalid email format.";

        ArrayList<User> users = userManager.getAllCustomers();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return "Email already exists.";
            }
        }

        userManager.addCustomer(username, email, password, 0);
        return "User registered successfully.";
    }

    public boolean isAdmin(User user) {
        if (user == null) return false;
        return user.getUserRole().equalsIgnoreCase("ADMIN");
    }

    public boolean isCustomer(User user) {
        if (user == null) return false;
        return user.getUserRole().equalsIgnoreCase("CUSTOMER");
    }

    public void closeConnection() throws SQLException {
        userManager.closeConnection();
    }
}
