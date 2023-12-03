package com.example.owner;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Owner;
import model.OwnerDAO;
import util.DButil;

import java.io.IOException;
import java.sql.SQLException;

//Main class which extends from application class
public class Main extends Application {
    //This is our primary stage (it contains everything)
    private Stage primaryStage;
    //This is the BorderPane rootLayout;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException {
        //1)declare a primary stage
        this.primaryStage = primaryStage;
        //Optional : seT A TITLEFOR PRIMARY STAGE
        this.primaryStage.setTitle("Owner");
        //2)Initialize RootLayout
        initRootLayout();

        //3)display the EmployeeOperations view
        showOwnersView();
        loadOwnerData();
        testUpdateOwner();

    } private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("root-layout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showOwnersView() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("owner.fxml"));
            AnchorPane showOwnersView = (AnchorPane) loader.load();
            rootLayout.setCenter(showOwnersView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadOwnerData() {
        try {
            ObservableList<Owner> owners = OwnerDAO.searchOwner();
            displayOwners(owners);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    } private static void displayOwners(ObservableList<Owner> owners) {
        for (Owner owner : owners) {
            System.out.println("Owner ID: " + owner.getOwnerId() + ", Name: " + owner.getName()
                    + ", Address: " + owner.getAddress() + ", Phone: " + owner.getPhone()
                    + ", Email: " + owner.getEmail());
        }


    }











        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("owner.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //stage.setTitle("Hello!");
        //stage.setScene(scene);
        //stage.show();
        private void testUpdateOwner() {
            int testOwnerId = 3; // Use an existing owner ID for testing
            String testName = "Mariam Al-Hassan";
            String testAddress = "123 Oak Avenue,Durham";
            String testPhoneNumber = "555-1234";
            String testEmail = "mariam.ahassan@gmail.com";

            try {
                // Call the updateOwner method with test data
                OwnerDAO.updateOwner(testOwnerId, testName, testAddress, testPhoneNumber,testEmail);
                System.out.println("Owner updated successfully.");
            } catch (SQLException  e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        launch();
    }
}