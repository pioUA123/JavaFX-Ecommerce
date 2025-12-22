package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Managers.FavoriteManager;
import com.example.oopii_finalproject.Managers.ProductManager;
import com.example.oopii_finalproject.Objects.Favorite;
import com.example.oopii_finalproject.Objects.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class FavoriteService {

    private final FavoriteManager favoriteManager = new FavoriteManager();
    private final ProductManager productManager = new ProductManager();
    private final HelperClass helperClass = new HelperClass();

    public ArrayList<Product> getFavoriteProductsByUser(int userId) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();

        if (!helperClass.isValidUserId(userId)) {
            return products;
        }

        ArrayList<Favorite> favorites = favoriteManager.getFavoritesByUser(userId);
        for (Favorite f : favorites) {
            Product p = productManager.getProductById(f.getProductId());
            if (p != null) {
                products.add(p);
            }
        }

        return products;
    }

    public String addFavorite(int userId, int productId) throws SQLException {
        if (!helperClass.isValidUserId(userId)) return "Invalid user ID.";
        if (!helperClass.isValidProductId(productId)) return "Invalid product ID.";

        if (favoriteManager.isFavorite(userId, productId)) {
            return "Already in favorites.";
        }

        favoriteManager.addFavorite(userId, productId);
        return "Added to favorites.";
    }

    public String removeFavorite(int userId, int productId) throws SQLException {
        if (!helperClass.isValidUserId(userId)) return "Invalid user ID.";
        if (!helperClass.isValidProductId(productId)) return "Invalid product ID.";

        if (!favoriteManager.isFavorite(userId, productId)) {
            return "Not in favorites.";
        }

        favoriteManager.deleteFavorite(userId, productId);
        return "Removed from favorites.";
    }

    public boolean isFavorite(int userId, int productId) throws SQLException {
        if (!helperClass.isValidUserId(userId)) return false;
        if (!helperClass.isValidProductId(productId)) return false;

        return favoriteManager.isFavorite(userId, productId);
    }

    public void closeConnection() throws SQLException {
        favoriteManager.closeConnection();
        productManager.closeConnection();
    }
}