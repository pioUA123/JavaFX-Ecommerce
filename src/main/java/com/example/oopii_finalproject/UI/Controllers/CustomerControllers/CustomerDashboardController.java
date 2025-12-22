package com.example.oopii_finalproject.UI.Controllers.CustomerControllers;

import com.example.oopii_finalproject.Objects.CartItem;
import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.CartItemService;
import com.example.oopii_finalproject.Services.FavoriteService;
import com.example.oopii_finalproject.Services.ProductService;
import com.example.oopii_finalproject.UI.Frames.*;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.ReviewsFrame;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.CartFrame;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.CustomerDashboardFrame;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.OrderHistoryFrame;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.ProductDetailsFrame;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDashboardController {

    private final CustomerDashboardFrame customerDashboardFrame;
    private final User loggedInUser;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final FavoriteService favoriteService;

    private ArrayList<Product> allProducts;

    public CustomerDashboardController(CustomerDashboardFrame frame, User loggedInUser) {
        this.customerDashboardFrame = frame;
        this.loggedInUser = loggedInUser;
        this.cartItemService = new CartItemService();
        this.productService = new ProductService();
        this.favoriteService = new FavoriteService();
    }

    public void loadProducts() {
        try {
            allProducts = productService.getAllProducts();
            customerDashboardFrame.displayProducts(allProducts);

            ArrayList<String> categories = getDistinctCategories();
            customerDashboardFrame.populateCategories(categories);

        } catch (SQLException e) {
            customerDashboardFrame.showInfo("Error loading products.");
            e.printStackTrace();
        }
    }

    private ArrayList<String> getDistinctCategories() {
        ArrayList<String> categories = new ArrayList<>();

        if (allProducts == null) return categories;

        for (Product p : allProducts) {
            String c = p.getCategory();
            if (c != null && !c.isBlank() && !categories.contains(c)) {
                categories.add(c);
            }
        }
        return categories;
    }

    public void searchProducts(String query, String category) {
        if (allProducts == null) {
            return;
        }

        boolean hasText = query != null && !query.isBlank();
        boolean hasCategory = category != null && !category.equals("All Categories");

        if (!hasText && !hasCategory) {
            customerDashboardFrame.displayProducts(allProducts);
            return;
        }

        String lower = hasText ? query.toLowerCase() : "";
        ArrayList<Product> filtered = new ArrayList<>();

        for (Product p : allProducts) {
            boolean matchesText = true;
            boolean matchesCategory = true;

            if (hasText) {
                String name = p.getProductName().toLowerCase();
                String desc = p.getProductDescription().toLowerCase();
                matchesText = name.contains(lower) || desc.contains(lower);
            }

            if (hasCategory) {
                String cat = p.getCategory();
                if (cat == null) cat = "";
                matchesCategory = cat.equalsIgnoreCase(category);
            }

            if (matchesText && matchesCategory) {
                filtered.add(p);
            }
        }

        customerDashboardFrame.displayProducts(filtered);
    }

    public String addItemToCart(CartItem item, int quantity) {

        if (item == null) {
            return "Cannot add this product to cart.";
        }

        if (!isUserAuthenticated()) {
            customerDashboardFrame.redirectToLogin();
            return "Please log in to add items to cart.";
        }

        String msg = cartItemService.addCartItem(item, quantity);
        if ("Item added Successfully".equals(msg)) {
            customerDashboardFrame.showViewCartButton();
        }
        return msg;
    }

    public CartItem getCartItemFor(Product product) {
        for (CartItem item : cartItemService.getCartItems()) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                return item;
            }
        }
        return null;
    }

    public boolean isFavorite(Product product) {
        if (product == null) return false;
        if (!isUserAuthenticated()) return false;

        try {
            return favoriteService.isFavorite(loggedInUser.getUserId(), product.getProductId());
        } catch (Exception e) {
            customerDashboardFrame.showInfo("Error checking favorites.");
            e.printStackTrace();
            return false;
        }
    }

    public void toggleFavorite(Product product) {
        if (product == null) return;

        if (!isUserAuthenticated()) {
            customerDashboardFrame.redirectToLogin();
            return;
        }

        try {
            boolean currentlyFavorite = favoriteService.isFavorite(loggedInUser.getUserId(), product.getProductId());
            String msg;

            if (currentlyFavorite) {
                msg = favoriteService.removeFavorite(loggedInUser.getUserId(), product.getProductId());
            }
            else {
                msg = favoriteService.addFavorite(loggedInUser.getUserId(), product.getProductId());
            }

        } catch (Exception e) {
            customerDashboardFrame.showInfo("Error updating favorites.");
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getFavoriteProducts() {
        ArrayList<Product> list = new ArrayList<>();

        if (!isUserAuthenticated()) {
            return list;
        }

        try {
            list = favoriteService.getFavoriteProductsByUser(loggedInUser.getUserId());
        } catch (Exception e) {
            customerDashboardFrame.showInfo("Error loading favorites.");
            e.printStackTrace();
        }

        return list;
    }

    public void openFavorites() {
        customerDashboardFrame.openFavoritesFrame();
    }


    public void openOrderHistory() {
        if (!isUserAuthenticated()) {
            customerDashboardFrame.redirectToLogin();
            return;
        }

        OrderHistoryFrame frame = new OrderHistoryFrame(customerDashboardFrame.getStage(), loggedInUser.getUserId());
        frame.show();
    }

    public void openProfileSettings() {

        if (!isUserAuthenticated()) {
            customerDashboardFrame.redirectToLogin();
            return;
        }

        new UserProfileFrame(customerDashboardFrame.getStage(), loggedInUser).show();
    }

    public void openCartFrame() {

        if (!isUserAuthenticated()) {
            customerDashboardFrame.redirectToLogin();
            return;
        }

        CartFrame cartFrame = new CartFrame(customerDashboardFrame.getStage(), loggedInUser, cartItemService, customerDashboardFrame);
        cartFrame.show();
    }

    public void refreshProductCardFor(CartItem item) {
        customerDashboardFrame.refreshProductCardFor(item);
    }

    public void logout() {
        customerDashboardFrame.redirectToLogin();
    }

    public void openProductDetails(Product product) {
        ProductDetailsFrame popup = new ProductDetailsFrame(customerDashboardFrame.getStage(), product, this);
        popup.show();
    }

    public void openReviewsPopup() {
        ReviewsFrame reviewsFrame = new ReviewsFrame(customerDashboardFrame.getStage());
        reviewsFrame.show();
    }

    public boolean cartContains(CartItem item) {
        return cartItemService.getCartItems().contains(item);
    }

    public boolean isUserAuthenticated() {
        return loggedInUser.isAuthenticated();
    }

    public void requireAuth() {
        if (!loggedInUser.isAuthenticated()) {
            customerDashboardFrame.redirectToLogin();
        }
    }
}
