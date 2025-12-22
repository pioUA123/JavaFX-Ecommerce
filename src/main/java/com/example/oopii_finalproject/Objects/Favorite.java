package com.example.oopii_finalproject.Objects;

public class Favorite {
    private final int favoriteId;
    private final int userId;
    private final int productId;

    public Favorite(int favoriteId, int userId, int productId) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.productId = productId;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }
}
