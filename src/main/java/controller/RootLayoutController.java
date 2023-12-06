package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import com.example.owner.Main;


import java.io.IOException;

public class RootLayoutController {
    @FXML public MenuItem ownerView;
    @FXML public MenuItem carView;
    @FXML public MenuItem repairJobsView;
    //references main app
    private Main mainApp;






    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }







    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void handleOwnersView(ActionEvent actionEvent) {
        mainApp.showOwnersView();
    }

    public void handleCarView(ActionEvent actionEvent) {
        mainApp.showCarView();
    }

    public void handleRepairsView(ActionEvent actionEvent) {
        mainApp.showRepairJobView();
    }
}