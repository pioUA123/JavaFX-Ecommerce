package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.Promotion;
import com.example.oopii_finalproject.Managers.PromotionManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class PromotionService {

    private final PromotionManager promotionManager = new PromotionManager();

    private final HelperClass helperClass = new HelperClass();

    public ArrayList<Promotion> getAllPromotions() throws SQLException {
        return promotionManager.getAllPromotions();
    }

    public String addPromotion(String code, int value, boolean active) throws SQLException {
        if (code == null || code.isBlank()) return "Code required.";
        if (value < 0) return "Invalid value.";
        promotionManager.addPromotion(code, value, active);
        return "Promotion added successfully.";
    }

    public String updatePromotion(int promotionId, String code, int value, boolean active) throws SQLException {
        if (!helperClass.isValidPromotionId(promotionId)) return "Invalid promotion ID.";
        promotionManager.updatePromotion(promotionId, code, value, active);
        return "Promotion updated successfully.";
    }

    public String deletePromotion(int promotionId) throws SQLException {
        if (!helperClass.isValidPromotionId(promotionId)) return "Invalid promotion ID.";
        promotionManager.deletePromotion(promotionId);
        return "Promotion deleted successfully.";
    }

    public String updatePromotionActive(int promotionId, boolean active) throws SQLException {
        if (!helperClass.isValidPromotionId(promotionId)) return "Invalid promotion ID.";
        promotionManager.updatePromotionActive(promotionId, active);
        return "Promotion status updated successfully.";
    }

    public void closeConnection() throws SQLException {
        promotionManager.closeConnection();
    }
}
