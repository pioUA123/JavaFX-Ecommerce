package com.example.oopii_finalproject.UI.Controllers.CustomerControllers;

import com.example.oopii_finalproject.Objects.Order;
import com.example.oopii_finalproject.Objects.OrderItem;
import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.Objects.Review;
import com.example.oopii_finalproject.Services.OrderItemService;
import com.example.oopii_finalproject.Services.OrderService;
import com.example.oopii_finalproject.Services.ProductService;
import com.example.oopii_finalproject.Services.ReviewService;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.OrderHistoryFrame;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderHistoryController {

    private final OrderHistoryFrame frame;
    private final int userId;

    private final OrderService orderService = new OrderService();
    private final OrderItemService orderItemService = new OrderItemService();
    private final ProductService productService = new ProductService();
    private final ReviewService reviewService = new ReviewService();

    public OrderHistoryController(OrderHistoryFrame frame, int userId) {
        this.frame = frame;
        this.userId = userId;
    }

    public ArrayList<Order> getOrdersForUser() throws SQLException {
        ArrayList<Order> allOrders = orderService.getAllOrders();
        ArrayList<Order> myOrders = new ArrayList<>();

        for (Order o : allOrders) {
            if (o.getUserId() == userId) {
                myOrders.add(o);
            }
        }

        return myOrders;
    }

    public ArrayList<String> getOrderItemLines(int orderId) throws SQLException {
        ArrayList<OrderItem> allItems = orderItemService.getAllOrderItems();
        ArrayList<Product> allProducts = productService.getAllProducts();
        ArrayList<String> lines = new ArrayList<>();

        for (OrderItem item : allItems) {
            if (item.getOrderId() == orderId) {
                Product product = null;
                for (Product p : allProducts) {
                    if (p.getProductId() == item.getProductId()) {
                        product = p;
                        break;
                    }
                }
                if (product != null) {
                    String line = product.getProductName() + " x" + item.getQuantity();
                    lines.add(line);
                }
            }
        }

        return lines;
    }

    public boolean hasReviewForOrder(int orderId) throws SQLException {
        ArrayList<Review> allReviews = reviewService.getAllReviews();
        for (Review r : allReviews) {
            if (r.getOrderId() == orderId && r.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }

    public void cancelOrder(Order order) {
        try {
            String msg = orderService.cancelOrderByCustomer(order.getOrderId(), userId);
            frame.showInfo(msg);
        } catch (Exception e) {
            e.printStackTrace();
            frame.showInfo("Error cancelling order.");
        }
    }
}
