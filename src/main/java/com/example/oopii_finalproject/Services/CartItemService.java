package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Managers.CartItemManager;
import com.example.oopii_finalproject.Objects.CartItem;

import java.util.ArrayList;

public class CartItemService {

    private final CartItemManager cartItemManager = new CartItemManager();

    public String addCartItem(CartItem item, int quantity) {
        if (item.getQuantity() >= item.getProduct().getProductStock())
            return "Stock exceeded";
        if (item.getProduct().getProductStock() <= 0)
            return "Item out of Stock";
        cartItemManager.addCartItem(item, quantity);
        return "Item added Successfully";
    }

    public String deleteCartItem(CartItem item) {
        cartItemManager.deleteCartItem(item);
        return "Item deleted Successfully";
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItemManager.getCartItems();
    }

    public double getTotalPrice() {
        return cartItemManager.getTotalPrice();
    }

    public void clearCart() {
        cartItemManager.clearCart();
    }

    public String incrementCartItem(CartItem item) {
        return addCartItem(item, 1);
    }

    public void decrementCartItem(CartItem item) {
        if (item.getQuantity() > 1)
            cartItemManager.updateCartItem(item, -1);
        else
            cartItemManager.deleteCartItem(item);
    }

}
