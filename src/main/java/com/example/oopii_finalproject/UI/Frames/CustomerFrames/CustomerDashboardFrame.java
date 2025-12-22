package com.example.oopii_finalproject.UI.Frames.CustomerFrames;

import com.example.oopii_finalproject.Objects.CartItem;
import com.example.oopii_finalproject.Objects.Customer;
import com.example.oopii_finalproject.Objects.Product;
import com.example.oopii_finalproject.Objects.User;
import com.example.oopii_finalproject.UI.Components.ProductCard;
import com.example.oopii_finalproject.UI.Controllers.CustomerControllers.CustomerDashboardController;
import com.example.oopii_finalproject.UI.Frames.LoginFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CustomerDashboardFrame {

    private final Stage stage;
    private final CustomerDashboardController customerDashboardController;
    private final User loggedInUser;
    private Button viewCartBtn;

    private FlowPane productGrid;
    private ComboBox<String> categoryCombo;

    public CustomerDashboardFrame(Stage stage, User loggedInUser) {
        this.stage = stage;
        this.loggedInUser = loggedInUser;
        this.customerDashboardController = new CustomerDashboardController(this, loggedInUser);
    }

    public void show() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #009879;");

        Label menuTitle = new Label("MENU");
        menuTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button homeBtn = sidebarButton("Home");
        Button ordersBtn = sidebarButton("My Orders");
        Button profileBtn = sidebarButton("Profile");
        Button reviewsBtn = sidebarButton("Check Reviews");
        Button favoritesBtn = sidebarButton("Favorites");
        Button logoutBtn = sidebarButton("Logout");

        ordersBtn.setOnAction(e -> customerDashboardController.openOrderHistory());
        profileBtn.setOnAction(e -> customerDashboardController.openProfileSettings());
        reviewsBtn.setOnAction(e -> customerDashboardController.openReviewsPopup());
        favoritesBtn.setOnAction(e -> customerDashboardController.openFavorites());
        logoutBtn.setOnAction(e -> customerDashboardController.logout());

        sidebar.getChildren().addAll(menuTitle, homeBtn, ordersBtn, profileBtn, reviewsBtn, favoritesBtn, logoutBtn);
        sidebar.setAlignment(Pos.TOP_CENTER);

        root.setLeft(sidebar);

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10, 20, 10, 20));
        topBar.setSpacing(20);
        topBar.setStyle("-fx-background-color: white;");

        Image profilePicture = new Image("file:src/main/java/com/example/oopii_finalproject/Images/" + loggedInUser.getProfilePicture());
        ImageView profile = new ImageView(profilePicture);
        profile.setFitHeight(30);
        profile.setFitWidth(30);

        Label welcomeLabel = new Label("Welcome, " + loggedInUser.getUsername(), profile);
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        welcomeLabel.setGraphic(profile);
        welcomeLabel.setContentDisplay(ContentDisplay.LEFT);
        welcomeLabel.setGraphicTextGap(10);

        TextField searchField = new TextField();
        searchField.setPromptText("Search products...");
        searchField.setPrefWidth(250);
        searchField.setStyle("-fx-background-color: white; -fx-border-color: #d9d9d9; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 8;");

        categoryCombo = new ComboBox<>();
        categoryCombo.setPrefWidth(150);
        categoryCombo.setPromptText("Category");
        categoryCombo.setStyle("-fx-background-color: #d0f2eb; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 5;");

        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-background-color: #009879; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 14;");

        searchBtn.setOnAction(e -> {
            String query = searchField.getText();
            String category = categoryCombo.getSelectionModel().getSelectedItem();
            customerDashboardController.searchProducts(query, category);
        });

        viewCartBtn = new Button("🛒 View Cart");
        viewCartBtn.setStyle("-fx-background-color: #009879;-fx-text-fill: white;-fx-font-weight: bold;-fx-background-radius: 8;-fx-padding: 8 14;-fx-cursor: hand;");
        viewCartBtn.setVisible(false);
        viewCartBtn.setManaged(false);
        viewCartBtn.setOnAction(e -> customerDashboardController.openCartFrame());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(welcomeLabel, spacer, searchField, categoryCombo, searchBtn, viewCartBtn);

        root.setTop(topBar);

        productGrid = new FlowPane();
        productGrid.setPadding(new Insets(20));
        productGrid.setHgap(20);
        productGrid.setVgap(20);
        productGrid.setPrefWrapLength(900);

        Image bg = new Image("file:src/main/java/com/example/oopii_finalproject/Images/Bg2.jpg");

        BackgroundImage gridBg = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true));

        productGrid.setBackground(new Background(gridBg));

        ScrollPane scrollPane = new ScrollPane(productGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPannable(true);
        scrollPane.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                Region viewport = (Region) scrollPane.lookup(".viewport");
                if (viewport != null) {
                    viewport.setStyle("-fx-background-color: transparent;");
                }
            }
        });

        root.setCenter(scrollPane);

        customerDashboardController.loadProducts();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Customer Dashboard");
        stage.setMaximized(true);
        stage.show();
    }

    public void populateCategories(ArrayList<String> categories) {
        categoryCombo.getItems().clear();
        categoryCombo.getItems().add("All Categories");
        if (categories != null) {
            for (String c : categories) {
                categoryCombo.getItems().add(c);
            }
        }
        categoryCombo.getSelectionModel().selectFirst();
    }

    public void showViewCartButton() {
        viewCartBtn.setVisible(true);
        viewCartBtn.setManaged(true);
    }

    public Stage getStage() {
        return stage;
    }

    private Button sidebarButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(180);
        btn.setStyle("-fx-background-color: white; -fx-text-fill: #009879; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10 0; -fx-cursor: hand;");
        return btn;
    }

    public void displayProducts(ArrayList<Product> products) {
        productGrid.getChildren().clear();
        for (Product p : products) {
            productGrid.getChildren().add(new ProductCard(p, customerDashboardController));
        }
    }

    public void refreshProductCardFor(CartItem item) {
        productGrid.getChildren().forEach(node -> {
            if (node instanceof ProductCard card) {
                if (card.getProduct().getProductId() == item.getProduct().getProductId()) {

                    boolean exists = customerDashboardController.cartContains(item);

                    if (exists) {
                        card.setCartItem(item);
                    } else {
                        card.setCartItem(null);
                    }

                    card.refreshQuantityBadge();
                }
            }
        });
    }

    public void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    public void redirectToLogin() {
        new LoginFrame(stage).show();
    }

    public void reloadProducts() {
        customerDashboardController.loadProducts();
    }

    public void openFavoritesFrame() {
        new FavoritesFrame(stage, loggedInUser, customerDashboardController).show();
    }

}
