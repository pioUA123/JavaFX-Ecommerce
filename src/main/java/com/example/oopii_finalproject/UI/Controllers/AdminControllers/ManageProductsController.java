package com.example.oopii_finalproject.UI.Controllers.AdminControllers;

import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.Services.ProductService;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.ManageProductsFrame;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ManageProductsController {

    private final ManageProductsFrame frame;
    private final ProductService productService = new ProductService();

    private ArrayList<Product> allProducts = new ArrayList<>();

    public ManageProductsController(ManageProductsFrame frame) {
        this.frame = frame;
    }

    public void loadProducts() {
        try {
            allProducts = productService.getAllProducts();
            frame.displayProducts(allProducts);
        } catch (SQLException e) {
            e.printStackTrace();
            frame.showInfo("Error loading products.");
        }
    }

    public void searchProducts(String query) {
        if (query == null || query.isBlank()) {
            frame.displayProducts(allProducts);
            return;
        }

        String lower = query.toLowerCase();

        ArrayList<Product> filtered = allProducts.stream()
                .filter(p ->
                        p.getProductName().toLowerCase().contains(lower) ||
                                p.getProductDescription().toLowerCase().contains(lower)
                )
                .collect(Collectors.toCollection(ArrayList::new));

        frame.displayProducts(filtered);
    }

    public String addProduct(String name, String description, String priceText, String stockText, String category, String image) {
        int price;
        int stock;

        try {
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            return "Invalid price.";
        }

        try {
            stock = Integer.parseInt(stockText);
        } catch (NumberFormatException e) {
            return "Invalid stock.";
        }

        try {
            return productService.addProduct(name, description, price, stock, category, image);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error adding product.";
        }
    }

    public String updateProduct(int productId, String name, String description, String priceText, String stockText, String category, String image) {
        int price;
        int stock;

        try {
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            return "Invalid price.";
        }

        try {
            stock = Integer.parseInt(stockText);
        } catch (NumberFormatException e) {
            return "Invalid stock.";
        }

        try {
            return productService.updateProduct(productId, name, description, price, stock, category, image);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating product.";
        }
    }

    public String deleteProduct(int productId) {
        try {
            return productService.deleteProduct(productId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting product.";
        }
    }
}