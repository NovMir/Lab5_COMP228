package controller;

import com.example.owner.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Car;
import model.CarDAO;
import model.Repair;
import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class CarController {

    @FXML
    public ComboBox<String> typeCb;
    @FXML
    public TextField newVinnumber;
    @FXML
    public TextField newBuildYear;
    @FXML
    public Button btnUpdate;
    @FXML
    public TextArea resultArea;
    @FXML
    public TextField carIdText;
    @FXML
    public TextField modelField;
    @FXML
    public TextField vinField;
    @FXML
    public TextField buildYearField;
    @FXML
    public TextField makeField;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnSearchall;
    @FXML
    public Button btnSearchRepairs;
    @FXML
    public Button btnSearchCarId;
    @FXML
    public TableColumn<Repair, Integer> repairIDcoloumn;
    @FXML
    public TableColumn<Repair, Integer> owneridcol;
    public TableColumn<Repair, Integer> caridcolumn;
    public TableColumn<Repair, String> descriptioncolumn;
    public TableColumn<Repair, Integer> costcolumn;
    public TableColumn<Repair, Date> repairDatecolumn;
    public TableView<Repair> repairTable;

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, Integer> carIDColumn;

    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, Integer> vinColumn;
    @FXML
    private TableColumn<Car, Integer> buildyearColumn;
    @FXML
    private TableColumn<Car, String> typeColumn;
    @FXML
    public TableColumn<Car, String> makeColoumn;


    @FXML
    public TextField inputTypeField;
    private Main mainApp;

    public CarController(){}
    @FXML
    private void initialize() {
        carIDColumn.setCellValueFactory(cellData -> cellData.getValue().carIdProperty().asObject());

        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        vinColumn.setCellValueFactory(cellData -> cellData.getValue().vinProperty().asObject());
        buildyearColumn.setCellValueFactory(cellData -> cellData.getValue().buildYearProperty().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        makeColoumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        // Initialize ComboBox items
        populateComboBox();
        // Initialization for repairTable
        repairIDcoloumn.setCellValueFactory(cellData -> cellData.getValue().repairIdProperty().asObject());
        owneridcol.setCellValueFactory(cellData -> cellData.getValue().ownerIdproperty().asObject());
        carIDColumn.setCellValueFactory(cellData -> cellData.getValue().carIdProperty().asObject());
        descriptioncolumn.setCellValueFactory(cellData -> cellData.getValue().descriptionproperty());
        costcolumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());
        repairDatecolumn.setCellValueFactory(cellData -> cellData.getValue().repairDateProperty());
    }

    @FXML
    private void populateComboBox() {
        typeCb.getItems().addAll("Sedan", "SUV", "Hatchback", "Coupe", "Electric");

        // Optional: Set a listener for selection changes
        typeCb.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            // Handle selection change
            System.out.println("Selected type: " + newValue);
        });
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp ;
    }
    @FXML
    public void handleTypeSelection(ActionEvent actionEvent) {
        String selectedType = typeCb.getValue();//get the selecte dvalue
        inputTypeField.setText(selectedType);
    }

    @FXML
    public void insertCarInfo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        try{

            String model = modelField.getText();
            int vin = Integer.parseInt(vinField.getText()); //
            int buildYear = Integer.parseInt(buildYearField.getText());
            String type = inputTypeField.getText();

            CarDAO.insertCarInfo(model, vin, buildYear, type);

        } catch (NumberFormatException e) {

            System.out.println("Invalid input: " + e.getMessage());
            // Optionally, show an error alert to the user
        } catch (SQLException e) {
            // Handle SQL Exception
            System.out.println("Database error: " + e.getMessage());

        }
        }


    public void deleteCarInfo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            int carId = Integer.parseInt(carIdText.getText());
            CarDAO.deleteCarWithId(carId);

        }catch(NumberFormatException e){
            System.out.println("Invalid car ID format: " + e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("Error during Car Delete operation: " + e.getMessage());

        }
    }






    public void searchCarTable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try { ObservableList<Car> carData = CarDAO.searchCars();
            populateCars(carData);
            carTable.setVisible(true);
            carTable.setManaged(true);
            repairTable.setVisible(false);
            repairTable.setManaged(false);

        } catch(SQLException e) { System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;

        }
    }

    private void populateCars(ObservableList<Car> carData) throws ClassNotFoundException {
        //Set items to the employeeTable
        carTable.setItems(carData);
    }


    public void getRepairsByCarId(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       try{
           int carId = Integer.parseInt(carIdText.getText());
           ObservableList<Repair> repairs =  CarDAO.getRepairsByCarID(carId);
           populateRepairs(repairs);

           carTable.setVisible(false);
           carTable.setManaged(false);
           repairTable.setVisible(true);
           repairTable.setManaged(true);
          
       }catch (SQLException e){
               System.out.println("Error occurred while getting information from DB.\n" + e);
               throw e;
    }
}

    private void populateRepairs(ObservableList<Repair> repairs) {
        repairTable.setItems(repairs);
    }

    public void searchSingleCar(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            int carId = Integer.parseInt(carIdText.getText());
            Car car = CarDAO.searchCarByID(carId);
            populateCar(car);
            carTable.setVisible(true);
            carTable.setManaged(true);
            repairTable.setVisible(false);
            repairTable.setManaged(false);


        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error occurred while getting  information from DB.\n" + e);
            throw e;
        }
    }


   
//populate car
    private void populateCar(Car car) throws ClassNotFoundException {
    ObservableList<Car> carData = FXCollections.observableArrayList();
    carData.add(car);
    carTable.setItems(carData);
    }

    public void updateCarInfo(ActionEvent actionEvent)throws SQLException {
        try{
            int vin = Integer.parseInt(newVinnumber.getText()); //
            int buildYear = Integer.parseInt(newBuildYear.getText());
            int carId = Integer.parseInt(carIdText.getText());
            CarDAO.updateCar(carId,vin,buildYear);
        }catch (SQLException e){
            System.out.println("Problem occured");
        }
    }
}