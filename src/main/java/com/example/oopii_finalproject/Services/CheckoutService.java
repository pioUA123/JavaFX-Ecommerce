package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Managers.*;
import com.example.oopii_finalproject.Objects.CartItem;
import com.example.oopii_finalproject.Objects.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckoutService {

    private final OrderManager orderManager = new OrderManager();
    private final OrderItemManager orderItemManager = new OrderItemManager();
    private final ProductManager productManager = new ProductManager();
    private final ShipmentManager shipmentManager = new ShipmentManager();
    private final PaymentManager paymentManager = new PaymentManager();
    private final HelperClass helperClass = new HelperClass();
    private final UserManager userManager = new UserManager();

    public int placeOrder(User user, ArrayList<CartItem> cartItems, double total, String paymentMethod, String shippingLocation) throws SQLException {
        if (user == null) {
            throw new IllegalArgumentException("User not logged in.");
        }
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty.");
        }
        if (total < 0) {
            throw new IllegalArgumentException("Total must be positive.");
        }

        int orderId = orderManager.addOrderAndReturnId(user.getUserId(), total, "PENDING", shippingLocation);
        paymentManager.addPayment(orderId, paymentMethod, "CAPTURED", "-" + orderId);
        shipmentManager.addShipment(orderId, "DHL", "TN" + orderId,
                new Date(System.currentTimeMillis() + 5 * 86400000L), "PENDING");

        for (CartItem item : cartItems) {
            int productId = item.getProduct().getProductId();
            int qty = item.getQuantity();
            double subtotal = item.getProduct().getProductPrice() * qty;

            orderItemManager.addOrderItem(orderId, productId, qty, subtotal);

            int newStock = item.getProduct().getProductStock() - qty;
            productManager.updateProductStock(productId, newStock);
        }

        return orderId;
    }

    public String updateUserBalance(int userId, String newBalance) throws SQLException {
        if (!helperClass.isValidUserId(userId)) {
            return "Invalid user ID.";
        }

        double parsedBalance;
        try {
            parsedBalance = Double.parseDouble(newBalance);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        userManager.updateUserBalance(userId, parsedBalance);
        return "Password updated successfully.";
    }

    public void closeConnection() throws SQLException {
        orderManager.closeConnection();
        orderItemManager.closeConnection();
        productManager.closeConnection();
    }
}