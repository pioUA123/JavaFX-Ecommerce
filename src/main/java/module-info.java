module com.example.oopii_finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.graphics;


    opens com.example.oopii_finalproject to javafx.fxml;
    exports com.example.oopii_finalproject;
    exports com.example.oopii_finalproject.UI.Controllers.AdminControllers;
    opens com.example.oopii_finalproject.UI.Controllers.AdminControllers to javafx.fxml;
}