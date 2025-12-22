package com.example.oopii_finalproject;

import com.example.oopii_finalproject.UI.Frames.LoginFrame;
import javafx.application.Application;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        LoginFrame loginFrame = new LoginFrame(stage);
        loginFrame.show();
    }

    public static void main(String[] args) {
        launch();
    }
}