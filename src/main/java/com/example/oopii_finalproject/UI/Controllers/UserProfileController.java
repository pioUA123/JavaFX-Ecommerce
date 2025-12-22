package com.example.oopii_finalproject.UI.Controllers;

import com.example.oopii_finalproject.Objects.Promotion;
import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.PromotionService;
import com.example.oopii_finalproject.Services.UserService;
import com.example.oopii_finalproject.UI.Frames.UserProfileFrame;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserProfileController {

    private final UserProfileFrame frame;
    private final User loggedInUser;

    private final UserService userService = new UserService();
    private final PromotionService promotionService = new PromotionService();

    public UserProfileController(UserProfileFrame frame, User user) {
        this.frame = frame;
        this.loggedInUser = user;
    }

    public void updateUser(String name, String email, String password) {
        try {
            String message = userService.updateUser(loggedInUser.getUserId(), name, email, password);
            frame.showInfo(message);
        } catch (Exception e) {
            frame.showInfo("Error updating user.");
            e.printStackTrace();
        }
    }

    public void addBalance(String amountText) {
        try {
            double amount = Double.parseDouble(amountText);

            // UserManager adds to the existing balance
            String msg = userService.updateUserBalance(loggedInUser.getUserId(), amountText);
            frame.showInfo(msg);

            if (msg.equals("Balance updated successfully.")) {
                double newBalance = loggedInUser.getBalance() + amount;
                loggedInUser.setBalance(newBalance);
                frame.updateBalanceLabel(newBalance);
            }

        } catch (NumberFormatException e) {
            frame.showInfo("Please enter a valid number.");
        } catch (Exception e) {
            frame.showInfo("Error updating balance.");
            e.printStackTrace();
        }
    }

    public void applyPromo(String code) {
        if (code == null || code.isBlank()) {
            frame.showInfo("Enter a promo code.");
            return;
        }

        for (String used : loggedInUser.getUsedPromos()) {
            if (used.equalsIgnoreCase(code)) {
                frame.showInfo("You already used this promo code.");
                return;
            }
        }

        try {
            ArrayList<Promotion> list = promotionService.getAllPromotions();
            Promotion found = null;

            for (Promotion p : list) {
                if (p.getPromoCode().equalsIgnoreCase(code)) {
                    found = p;
                    break;
                }
            }

            if (found == null || !found.isActive()) {
                frame.showInfo("Invalid or inactive promo code.");
                return;
            }

            loggedInUser.setPromotion(found);
            frame.showInfo("Promo Code Applied Successfully!");

        } catch (SQLException e) {
            frame.showInfo("Error applying promo.");
            e.printStackTrace();
        }
    }
}
