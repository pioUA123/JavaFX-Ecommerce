package com.example.oopii_finalproject.Objects;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 0;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem that)) return false;
        return this.getProduct().getProductId() == that.getProduct().getProductId();
    }

    @Override
    public int hashCode() {
        return product.hashCode();
    }

}
