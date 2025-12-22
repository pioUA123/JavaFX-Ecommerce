package com.example.oopii_finalproject.UI.Controllers;

import com.example.oopii_finalproject.Services.AuthService;
import com.example.oopii_finalproject.UI.Frames.SignupFrame;

import java.sql.SQLException;

public class SignupController {

    private final SignupFrame signupFrame;
    private final AuthService authService;

    public SignupController(SignupFrame signupFrame) {
        this.signupFrame = signupFrame;
        this.authService = new AuthService();
    }

    public String handleSignup(String username, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }

        try {
            String result = authService.signUp(username, email, password);

            if (result.equals("User registered successfully.")) {
                signupFrame.goToLogin();
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error processing signup.";
        }
    }

    public void openLoginFrame() {
        signupFrame.goToLogin();
    }
}

