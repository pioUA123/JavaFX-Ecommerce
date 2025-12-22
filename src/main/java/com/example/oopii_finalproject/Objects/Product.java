package com.example.oopii_finalproject.Objects;

public class Product {
    private final int productId;
    private final String productName;
    private final String productDescription;
    private final double productPrice;
    private final int productStock;
    private final String category;

    private String image;

    public Product(int productId, String productName, String productDescription, double productPrice, int productStock, String category, String image) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.category = category;
        this.image = image;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}