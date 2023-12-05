package model;

import controller.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RootLayout extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Your Application Title");

        initRootLayout();
        showOwnerView(); // Optionally, you can show the Owner view by default
    }

    public void initRootLayout() {
        try {
            // Load RootLayout.fxml from resources
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Give the controller access to the main app
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed, e.g., log or display an error message
        }
    }

    public void showOwnerView() {
        // Show the Owner view when the application starts
        RootLayoutController controller = new RootLayoutController();
        controller.setMainApp(this);
        controller.switchToOwnerView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
