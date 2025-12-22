package com.example.oopii_finalproject.Managers;

import com.example.oopii_finalproject.DBConnection;
import com.example.oopii_finalproject.Objects.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductManager {

    DBConnection dbConnection = new DBConnection();

    public ArrayList<Product> getAllProducts() throws SQLException {

        Connection connection = dbConnection.connect();

        ArrayList<Product> products = new ArrayList<>();

        String query = "SELECT * FROM products";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("products_id");
                String name = resultSet.getString("product_name");
                String desc = resultSet.getString("product_description");
                int price = resultSet.getInt("price");
                int stock = resultSet.getInt("stock");
                String category = resultSet.getString("category");
                String image = resultSet.getString("picture_url");
                products.add(new Product(id, name, desc, price, stock, category, image));
            }
        }
        return products;
    }

    public Product getProductById(int productId) throws SQLException {

        Connection connection = dbConnection.connect();

        Product product = null;

        String query = "SELECT * FROM products WHERE products_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("products_id");
                String name = resultSet.getString("product_name");
                String desc = resultSet.getString("product_description");
                int price = resultSet.getInt("price");
                int stock = resultSet.getInt("stock");
                String category = resultSet.getString("category");
                String image = resultSet.getString("picture_url");
                product = new Product(id, name, desc, price, stock, category, image);
            }
        }
        return product;
    }

    // keep the old method for compatibility (uses default category)
    public void addProduct(String name, String description, int price, int stock, String image) throws SQLException {
        addProduct(name, description, price, stock, "GENERAL", image);
    }

    public void addProduct(String name, String description, int price, int stock, String category, String image) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "INSERT INTO products (product_name, product_description, price, stock, category, picture_url) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, stock);
            preparedStatement.setString(5, category);
            preparedStatement.setString(6, image);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteProduct(int productId) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "DELETE FROM products WHERE products_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);

            preparedStatement.executeUpdate();
        }
    }

    // keep old signature, forward to new one with default category
    public void updateProduct(int productId, String name, String description, int price, int stock, String image) throws SQLException {
        updateProduct(productId, name, description, price, stock, "GENERAL", image);
    }

    // new overload with category
    public void updateProduct(int productId, String name, String description, int price, int stock, String category, String image) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE products SET product_name = ?, product_description = ?, price = ?, stock = ?, category = ?, picture_url = ? WHERE products_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, stock);
            preparedStatement.setString(5, category);
            preparedStatement.setString(6, image);
            preparedStatement.setInt(7, productId);

            preparedStatement.executeUpdate();
        }
    }

    public void updateProductStock(int productId, int stock) throws SQLException {

        Connection connection = dbConnection.connect();

        String query = "UPDATE products SET stock = ? WHERE products_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, stock);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();
        }
    }

    public void closeConnection() throws SQLException {
        dbConnection.disconnect();
    }
}