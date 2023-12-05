package com.example.owner;

import controller.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.RootLayout;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private RootLayout rootLayout;
    private RootLayoutController rootLayoutController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JavaFX App");

        initRootLayout();

        showOwnersView();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("root_layout.fxml"));
            rootLayout = loader.load();
            rootLayoutController = loader.getController();
            rootLayoutController.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            handleException("Error initializing root layout", e);
        }
    }

    public void showOwnersView() {
        try {
            rootLayoutController.loadView("owner.fxml");
        } catch (IOException e) {
            handleException("Error loading owner view", e);
        }
    }

    private void handleException(String message, Exception e) {
        // You can customize how you handle exceptions here
        System.err.println(message);
        e.printStackTrace();
    }

    public static void main(String[] args) {
        launch(args);
    }
}