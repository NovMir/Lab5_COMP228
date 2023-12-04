package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Car;
import model.CarDAO;

import java.sql.SQLException;
public class CarController {

    @FXML
    private TableView<Car> carTable;
    @FXML
    private TableColumn<Car, Integer> carIDcolumn;
    @FXML
    private TableColumn<Car, Integer> numberColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, Integer> vinColumn;
    @FXML
    private TableColumn<Car, Integer> buildyearColumn;
    @FXML
    private TableColumn<Car, String> typeColumn;
    @FXML
    private ComboBox<String> comboboxCarType;
    @FXML
    private TextField numberField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField vinField;
    @FXML
    private TextField buildYearField;

    private void initialize() {
        carIDcolumn.setCellValueFactory(cellData -> cellData.getValue().carIdProperty().asObject());

        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        vinColumn.setCellValueFactory(cellData -> cellData.getValue().vinProperty().asObject());
        buildyearColumn.setCellValueFactory(cellData -> cellData.getValue().buildYearProperty().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        // Initialize ComboBox items
        comboboxCarType.setItems(FXCollections.observableArrayList("Sedan", "SUV", "Hatchback")); // Example types
    }
}
