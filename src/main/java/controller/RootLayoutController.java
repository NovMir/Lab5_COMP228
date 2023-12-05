package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import com.example.owner.Main;
import model.RootLayout;

import java.io.IOException;

public class RootLayoutController {

    @FXML
    private Button btnOwner;

    @FXML
    private Button btnRepair;

    @FXML
    private Button btnCar;

    @FXML
    private AnchorPane viewContainer;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        // Set the Owner view as the default view
        switchToOwnerView();
    }

    @FXML
    public void switchToOwnerView() {
        // Load the Owner.fxml into the view container
        loadView("Owner.fxml");
    }

    @FXML
    public void switchToRepairView() {
        // Load the Repair.fxml into the view container
        loadView("Repair.fxml");
    }

    @FXML
    public void switchToCarView() {
        // Load the Car.fxml into the view container
        loadView("Car.fxml");
    }

    public void loadView(String viewFileName) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(viewFileName));
            viewContainer.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed, e.g., log or display an error message
        }
    }
}