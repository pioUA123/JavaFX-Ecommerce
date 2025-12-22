package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.Objects.CartItem;

import java.util.ArrayList;

public class CartItemManager {

    private final ArrayList<CartItem> cartItems = new ArrayList<>();

    public boolean isItemInCart(CartItem item) {
        for (CartItem cartItem : cartItems)
            if (item.equals(cartItem))
                return true;
        return false;
    }

    public void addCartItem(CartItem item, int quantity) {
        if (isItemInCart(item))
            updateCartItem(item, quantity);
        else {
            item.setQuantity(quantity);
            cartItems.add(item);
        }
    }

    public void deleteCartItem(CartItem item) {
        cartItems.remove(item);
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public CartItem getItem(CartItem item) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.equals(item)) {
                return cartItem;
            }
        }
        return null;
    }

    public void updateCartItem(CartItem item, int quantity) {
        CartItem existing = getItem(item);
        if (existing != null)
            existing.setQuantity(existing.getQuantity() + quantity);
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getProduct().getProductPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
