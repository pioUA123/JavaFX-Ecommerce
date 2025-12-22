package com.example.oopii_finalproject.UI.Controllers;

import com.example.oopii_finalproject.Objects.GuestUser;
import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.AuthService;
import com.example.oopii_finalproject.UI.Frames.LoginFrame;

public class LoginController {

    private final LoginFrame loginFrame;
    private final AuthService authService;

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.authService = new AuthService();
    }

    public String login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return "Please fill all fields.";
        }

        try {
            User user = authService.login(email, password);
            if (user == null) {
                return "Invalid credentials.";
            }

            if (authService.isAdmin(user)) {
                loginFrame.openAdminDashboard();
            }
            else if (authService.isCustomer(user)) {
                loginFrame.openCustomerDashboard(user);
            }
            else {
                loginFrame.displayMessage("Unknown role: " + user.getUserRole());
            }

            return "Login successful.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public void loginAsGuest() {
        User guest = new GuestUser();
        loginFrame.openCustomerDashboard(guest);
    }

    public void openSignupFrame() {
        loginFrame.openSignupFrame();
    }
}
