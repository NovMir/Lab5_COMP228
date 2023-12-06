package com.example.owner;

import controller.CarController;
import controller.OwnerController;
import controller.RepairController;
import controller.RootLayoutController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Owner;
import model.OwnerDAO;
import model.Car;
import model.CarDAO;
import java.io.IOException;
import java.sql.SQLException;

//Main class which extends from application class
public class Main extends Application {

    //This is our PrimaryStage (It contains everything)
    private Stage primaryStage;

    //This is the BorderPane of RootLayout
    private BorderPane rootLayout;



    private RootLayoutController rootLayoutController;


    @Override
    public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException {
        //1)declare a primary stage
        //1) Declare a primary stage (Everything will be on this stage)
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(" JavaFX App");
        primaryStage.setFullScreen(true);
        //2) Initialize RootLayout
        initRootLayout();

        showCarView();

        showOwnersView();
    }
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("root-layout.fxml"));
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("owner.fxml"));
            AnchorPane ownerView = (AnchorPane) loader.load();
            rootLayout.setCenter(ownerView);
            //give access to controller
            OwnerController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            handleException("Error loading owner view", e);
        }
    }
    public void showCarView(){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("car.fxml"));
            AnchorPane showCarView = (AnchorPane) loader.load();
            rootLayout.setCenter(showCarView);
            CarController controller = loader.getController();
            controller.setMainApp(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void handleException(String message, Exception e) {
        // You can customize how you handle exceptions here
        System.err.println(message);
        e.printStackTrace();
    }
    public void showRepairJobView(){
        try{FXMLLoader loader = new FXMLLoader(Main.class.getResource("repair.fxml"));
            AnchorPane showRepairJobView = (AnchorPane) loader.load();
            rootLayout.setCenter(showRepairJobView);
            RepairController controller = loader.getController();
            controller.setMainApp(this);

        }catch(IOException e){
            e.printStackTrace();
        }
    }



    /*private void loadOwnerData() {
        try {
            ObservableList<Owner> owners = OwnerDAO.searchOwner();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*//* private static void displayOwners(ObservableList<Owner> owners) {
        for (Owner owner : owners) {
            System.out.println("Owner ID: " + owner.getOwnerId(rsOwn.getInt("OWNERID")) + ", Name: " + owner.getName(rsOwn.getString("NAME"))
                    + ", Address: " + owner.getAddress(rsOwn.getString("ADDRESS")) + ", Phone: " + owner.getPhone(rsOwn.getString("PHONE"))
                    + ", Email: " + owner.getEmail(rsOwn.getString("EMAIL")));
        }*/














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
                OwnerDAO.updateOwner(String.valueOf(testOwnerId), testName, testAddress, testPhoneNumber, testEmail);
                System.out.println("Owner updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    public static void main(String[] args) {
        launch();
    }
}