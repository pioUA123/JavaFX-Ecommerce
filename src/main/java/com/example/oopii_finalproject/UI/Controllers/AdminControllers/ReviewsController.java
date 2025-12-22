package com.example.oopii_finalproject.UI.Controllers.AdminControllers;

import com.example.oopii_finalproject.Objects.OrderItem;
import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.Objects.Review;
import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.OrderItemService;
import com.example.oopii_finalproject.Services.ProductService;
import com.example.oopii_finalproject.Services.ReviewService;
import com.example.oopii_finalproject.Services.UserService;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.ReviewsFrame;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewsController {

    private final ReviewsFrame frame;

    private final ReviewService reviewService = new ReviewService();
    private final UserService userService = new UserService();
    private final OrderItemService orderItemService = new OrderItemService();
    private final ProductService productService = new ProductService();

    private ArrayList<Review> allReviews;
    private ArrayList<User> allUsers;
    private ArrayList<OrderItem> allOrderItems;
    private ArrayList<Product> allProducts;

    public ReviewsController(ReviewsFrame frame) {
        this.frame = frame;
    }

    public void loadAllData() throws SQLException {
        allReviews = reviewService.getAllReviews();
        allUsers = userService.getAllCustomers();
        allOrderItems = orderItemService.getAllOrderItems();
        allProducts = productService.getAllProducts();
    }

    public ArrayList<Review> getAllReviews() {
        return allReviews;
    }

    public String getUserNameById(int userId) {
        if (allUsers == null) return "Unknown";

        for (User u : allUsers) {
            if (u.getUserId() == userId) {
                return u.getUsername();
            }
        }
        return "Unknown";
    }

    public ArrayList<String> getProductsForOrder(int orderId) {
        ArrayList<String> names = new ArrayList<>();

        if (allOrderItems == null || allProducts == null) {
            return names;
        }

        for (OrderItem item : allOrderItems) {
            if (item.getOrderId() == orderId) {
                int productId = item.getProductId();

                Product product = null;
                for (Product p : allProducts) {
                    if (p.getProductId() == productId) {
                        product = p;
                        break;
                    }
                }

                if (product != null) {
                    names.add(product.getProductName());
                }
            }
        }

        return names;
    }
}