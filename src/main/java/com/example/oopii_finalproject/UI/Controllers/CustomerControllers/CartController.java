package com.example.oopii_finalproject.UI.Controllers.CustomerControllers;

import com.example.oopii_finalproject.Objects.CartItem;
import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.CartItemService;
import com.example.oopii_finalproject.Services.CheckoutService;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.CartFrame;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.CustomerDashboardFrame;

import java.util.ArrayList;

public class CartController {

    private final CartFrame frame;
    private final User loggedInUser;
    private final CartItemService cartItemService;
    private final CustomerDashboardFrame customerDashboardFrame;
    private final CheckoutService checkoutService;

    public CartController(CartFrame frame, User loggedInUser, CartItemService cartItemService, CustomerDashboardFrame customerDashboardFrame) {
        this.frame = frame;
        this.loggedInUser = loggedInUser;
        this.cartItemService = cartItemService;
        this.customerDashboardFrame = customerDashboardFrame;
        this.checkoutService = new CheckoutService();
    }

    public void loadCart() {
        ArrayList<CartItem> items = cartItemService.getCartItems();
        double total = cartItemService.getTotalPrice();
        frame.displayCartItems(items, total);
    }

    public void increaseQuantity(CartItem item) {
        cartItemService.incrementCartItem(item);
        loadCart();
    }

    public void decreaseQuantity(CartItem item) {
        cartItemService.decrementCartItem(item);
        loadCart();
    }

    public void removeItem(CartItem item) {
        cartItemService.deleteCartItem(item);
        loadCart();
    }

    public boolean placeOrder(String paymentMethod, String shippingLocation) {

        if (!loggedInUser.isAuthenticated()) {
            frame.showInfo("Please log in to place an order.");
            customerDashboardFrame.redirectToLogin();
            return false;
        }

        if (shippingLocation == null || shippingLocation.trim().isEmpty()) {
            frame.showInfo("Please enter a shipping address.");
            return false;
        }

        double total = cartItemService.getTotalPrice();

        if (loggedInUser.getBalance() < total && loggedInUser.getPromotion() == null) {
            frame.showInfo("Insufficient balance.");
            return false;
        }

        if (loggedInUser.getPromotion() != null) {

            if (total >= loggedInUser.getPromotion().getpValue())
                total = total - loggedInUser.getPromotion().getpValue();
            else
                total = 0;

            if (loggedInUser.getBalance() < total) {
                frame.showInfo("Insufficient balance.");
                return false;
            }
            loggedInUser.setPromotion(null);
        }

        ArrayList<CartItem> items = new ArrayList<>(cartItemService.getCartItems());

        if (items.isEmpty()) {
            frame.showInfo("Your cart is empty.");
            return false;
        }

        try {
            int orderId = checkoutService.placeOrder(loggedInUser, items, total, paymentMethod, shippingLocation);

            cartItemService.clearCart();
            loadCart();

            for (CartItem item : items) {
                customerDashboardFrame.refreshProductCardFor(item);
            }

            customerDashboardFrame.reloadProducts();

            String newCustomerBalance = String.valueOf(-total);
            String newAdminBalance = String.valueOf(total);

            checkoutService.updateUserBalance(loggedInUser.getUserId(), newCustomerBalance);
            checkoutService.updateUserBalance(1, newAdminBalance);

            frame.showInfo("Order #" + orderId + " placed successfully.\nStatus: PENDING");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            frame.showInfo("Error while placing order.");
            return false;
        }
    }
}