package com.example.oopii_finalproject.Services;

import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.Managers.ProductManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {

    private final ProductManager productManager = new ProductManager();

    private final HelperClass helperClass = new HelperClass();

    public ArrayList<Product> getAllProducts() throws SQLException {
        return productManager.getAllProducts();
    }

    public String addProduct(String name, String description, int price, int stock) throws SQLException {
        return addProduct(name, description, price, stock, "GENERAL");
    }

    public String addProduct(String name, String description, int price, int stock, String category) throws SQLException {
        if (name == null || name.isBlank()) return "Product name is required.";
        if (price <= 0) return "Invalid price.";
        if (stock < 0) return "Invalid stock.";
        if (category == null || category.isBlank()) category = "GENERAL";

        productManager.addProduct(name, description, price, stock, category);
        return "Product added successfully.";
    }

    public String updateProduct(int productId, String name, String description, int price, int stock) throws SQLException {
        return updateProduct(productId, name, description, price, stock, "GENERAL");
    }

    // new overload with category
    public String updateProduct(int productId, String name, String description, int price, int stock, String category) throws SQLException {
        if (!helperClass.isValidProductId(productId)) return "Invalid product ID.";
        if (category == null || category.isBlank()) category = "GENERAL";

        productManager.updateProduct(productId, name, description, price, stock, category);
        return "Product updated successfully.";
    }

    public String deleteProduct(int productId) throws SQLException {
        if (!helperClass.isValidProductId(productId)) return "Invalid product ID.";
        productManager.deleteProduct(productId);
        return "Product deleted successfully.";
    }

    public String updateStock(int productId, int stock) throws SQLException {
        if (!helperClass.isValidProductId(productId)) return "Invalid product ID.";
        if (stock < 0) return "Invalid stock.";
        productManager.updateProductStock(productId, stock);
        return "Stock updated successfully.";
    }

    public String addProduct(String name, String description, int price, int stock, String category, String image) throws SQLException {
        if (image == null) image = "default.png";
        productManager.addProduct(name, description, price, stock, category, image);
        return "Product added successfully.";
    }

    public String updateProduct(int productId, String name, String description, int price, int stock, String category, String image) throws SQLException {
        if (!helperClass.isValidProductId(productId)) return "Invalid product ID.";
        if (image == null) image = "default.png";
        productManager.updateProduct(productId, name, description, price, stock, category, image);
        return "Product updated successfully.";
    }


    public void closeConnection() throws SQLException {
        productManager.closeConnection();
    }
}