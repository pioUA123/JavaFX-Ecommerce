package com.example.oopii_finalproject.UI.Controllers.CustomerControllers;

import com.example.oopii_finalproject.Objects.CartItem;
import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.UI.Frames.CustomerFrames.ProductDetailsFrame;

public class ProductDetailsController {

    private final ProductDetailsFrame popup;
    private final Product product;
    private final CustomerDashboardController dashboardController;

    public ProductDetailsController(ProductDetailsFrame popup, Product product, CustomerDashboardController dashboardController) {
        this.popup = popup;
        this.product = product;
        this.dashboardController = dashboardController;
    }

    public void handleAddToCart(int quantity) {

        if (!dashboardController.isUserAuthenticated()) {
            popup.showInfo("Please log in to add items to cart.");
            popup.close();
            dashboardController.requireAuth();
            return;
        }

        CartItem item = dashboardController.getCartItemFor(product);
        if (item == null) {
            item = new CartItem(product);
        }
        String msg = dashboardController.addItemToCart(item, quantity);
        popup.showInfo(msg);
        if (msg.equals("Item added Successfully")) {
            dashboardController.refreshProductCardFor(item);
        }
    }


    public void handleClose() {
        popup.close();
    }
}
