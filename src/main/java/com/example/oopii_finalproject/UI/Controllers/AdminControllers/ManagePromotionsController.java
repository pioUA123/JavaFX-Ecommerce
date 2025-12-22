package com.example.oopii_finalproject.UI.Controllers.AdminControllers;

import com.example.oopii_finalproject.Objects.Promotion;
import com.example.oopii_finalproject.Services.PromotionService;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.ManagePromotionsFrame;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManagePromotionsController {

    private final ManagePromotionsFrame frame;
    private final PromotionService promotionService = new PromotionService();

    public ManagePromotionsController(ManagePromotionsFrame frame) {
        this.frame = frame;
    }

    public void loadPromotions() {
        try {
            ArrayList<Promotion> promotions = promotionService.getAllPromotions();
            frame.displayPromotions(promotions);
        } catch (SQLException e) {
            e.printStackTrace();
            frame.showInfo("Error loading promotions.");
        }
    }

    public String addPromotion(String code, String valueText, boolean active) {
        int value;

        try {
            value = Integer.parseInt(valueText);
        } catch (NumberFormatException e) {
            return "Invalid value.";
        }

        try {
            return promotionService.addPromotion(code, value, active);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error adding promotion.";
        }
    }

    public String updatePromotion(int promoId, String code, String valueText, boolean active) {
        int value;

        try {
            value = Integer.parseInt(valueText);
        } catch (NumberFormatException e) {
            return "Invalid value.";
        }

        try {
            return promotionService.updatePromotion(promoId, code, value, active);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating promotion.";
        }
    }

    public String deletePromotion(int promoId) {
        try {
            return promotionService.deletePromotion(promoId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting promotion.";
        }
    }

    public String updateActive(int promoId, boolean active) {
        try {
            return promotionService.updatePromotionActive(promoId, active);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating promotion status.";
        }
    }
}