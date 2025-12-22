package com.example.oopii_finalproject.UI.Controllers.AdminControllers;

import com.example.oopii_finalproject.Objects.OrderItem;
import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.Objects.Review;
import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.Services.OrderItemService;
import com.example.oopii_finalproject.Services.ProductService;
import com.example.oopii_finalproject.Services.ReviewService;
import com.example.oopii_finalproject.Services.UserService;
import com.example.oopii_finalproject.UI.Frames.AdminFrames.ManageReviewsFrame;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageReviewsController {

    private final ManageReviewsFrame frame;

    private final ReviewService reviewService = new ReviewService();
    private final UserService userService = new UserService();
    private final OrderItemService orderItemService = new OrderItemService();
    private final ProductService productService = new ProductService();

    private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<OrderItem> allOrderItems = new ArrayList<>();
    private ArrayList<Product> allProducts = new ArrayList<>();

    public ManageReviewsController(ManageReviewsFrame frame) {
        this.frame = frame;
    }

    public void loadReviews() {
        try {
            ArrayList<Review> reviews = reviewService.getAllReviews();
            allUsers = userService.getAllCustomers();
            allOrderItems = orderItemService.getAllOrderItems();
            allProducts = productService.getAllProducts();

            frame.displayReviews(reviews);

        } catch (SQLException e) {
            e.printStackTrace();
            frame.showInfo("Error loading reviews.");
        }
    }

    public String getUserName(int userId) {
        for (User user : allUsers) {
            if (user.getUserId() == userId) {
                return user.getUsername();
            }
        }
        return "User #" + userId;
    }

    public ArrayList<String> getProductNamesForOrder(int orderId) {
        ArrayList<String> names = new ArrayList<>();

        for (OrderItem item : allOrderItems) {
            if (item.getOrderId() == orderId) {
                int productId = item.getProductId();
                String productName = findProductName(productId);
                names.add(productName);
            }
        }

        return names;
    }

    private String findProductName(int productId) {
        for (Product p : allProducts) {
            if (p.getProductId() == productId) {
                return p.getProductName();
            }
        }
        return "Product #" + productId;
    }
}