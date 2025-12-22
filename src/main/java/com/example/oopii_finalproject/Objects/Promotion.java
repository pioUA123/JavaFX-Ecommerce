package com.example.oopii_finalproject.Objects;

public class Promotion {
    private final int promoId;
    private final String promoCode;
    private final double pValue;
    private final boolean isActive;

    public Promotion(int promoId, String promoCode, double discount, boolean isActive) {
        this.promoId = promoId;
        this.promoCode = promoCode;
        this.pValue = discount;
        this.isActive = isActive;
    }

    public int getPromoId() {
        return promoId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public double getpValue() {
        return pValue;
    }

    public boolean isActive() {
        return isActive;
    }
}
