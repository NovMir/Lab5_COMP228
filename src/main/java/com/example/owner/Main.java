package com.example.owner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
//Main class which extends from application class
public class Main extends Application {
    //This is our primary stage (it contains everything)
    private Stage primaryStage;
     //This is the BorderPane rootLayout;
    private BorderPane rootLayout;
    @Override
    public void start(Stage primaryStage) throws IOException {
        //1)declare a primary stage
        this.primaryStage = primaryStage;
        //Optional : seT A TITLEFOR PRIMARY STAGE
        this.primaryStage.setTitle("Owner");
        //2)Initialize RootLayout
        initRootLayout();

        //3)display the EmployeeOperations view
        showOwnersView();
    }



    //Initialize
    public void initRootLayout(){
        try{
            //load root layout from RootLayout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("root-layout.fxml"));
            rootLayout = (BorderPane) loader.load();
            //second show the scene containing the root layout
          Scene scene = new Scene(rootLayout);//sending rootlayout to the scene
            primaryStage.setScene(scene); //Set the scene in primary stage.
            //Third, show the primary stage
            primaryStage.show(); //Display the primary stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("owner.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //stage.setTitle("Hello!");
        //stage.setScene(scene);
        //stage.show();
        private void showOwnersView() {
            try {FXMLLoader loader = new FXMLLoader(Main.class.getResource("owner.fxml"));
                AnchorPane showOwnersView = (AnchorPane) loader.load();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    public static void main(String[] args) {
        launch();
    }
}