package controller;

import com.example.owner.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.CarDAO;
import model.Owner;
import model.OwnerDAO;
import model.Repair;

import java.sql.Date;
import java.sql.SQLException;

public class OwnerController {

    @FXML
    public TextField ownerEmail;
    @FXML
    public TextField ownerAddress;
    @FXML
    public TextField ownerPhone;
    @FXML
    public TextField ownerName;
    @FXML
    public TextArea resultArea;
    @FXML
    public TableView<Owner> ownerTable;
    @FXML
    public TableColumn<Owner, Number> ownerIdColumn;
    @FXML
    public TableColumn<Owner, String> ownerNameColumn;
    @FXML
    public TableColumn<Owner, String> ownerAddressColumn;
    @FXML
    public TableColumn<Owner, String> ownerPhoneColumn;
    @FXML
    public TableColumn<Owner, String> ownerEmailColumn;
    public TableColumn<Repair, Integer> repairIDcol;
    @FXML
    public TableColumn<Repair, Integer> ownerIDcol;
    @FXML
    public TableColumn<Repair, Integer> carIDcol;
    @FXML
    public TableColumn<Repair, String> descriptioncol;
    @FXML
    public TableColumn<Repair, Integer> costCol;
    @FXML
    public TableColumn<Repair, Date> repairdatecol;
    @FXML
    public TableView<Repair> repairsTable;
    @FXML
    public Button btnsearchOwner;
    @FXML
    public Button btndeleteOwner;
    @FXML
    public Button btnupdateOwner;
    @FXML
    public Button btnsearchAll;
    @FXML
    public Button btnaddOwner;
    @FXML
    public TextField ownerIdText;
    @FXML
    public Button btnrepairsbyowner;
    @FXML public TextField ownerUpdateName;
    @FXML public TextField ownerUpdateEmail;
    @FXML public TextField ownerUpdateAddress;
   @FXML public TextField ownerUpdatePhone;
    private Main mainApp;

    public OwnerController(){}

    @FXML
    //1
    private void initialize() {
        //on fxml file loading will be called automatically updates will be added
        //initializing the controller class
        ownerIdColumn.setCellValueFactory(cellData -> cellData.getValue().ownerIdProperty());
        ownerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ownerAddressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        ownerPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        ownerEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        // Initialization for repairTable
        repairIDcol.setCellValueFactory(cellData -> cellData.getValue().repairIdProperty().asObject());
        ownerIDcol.setCellValueFactory(cellData -> cellData.getValue().ownerIdproperty().asObject());
        carIDcol.setCellValueFactory(cellData -> cellData.getValue().carIdProperty().asObject());
        descriptioncol.setCellValueFactory(cellData -> cellData.getValue().descriptionproperty());
        costCol.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());
        repairdatecol.setCellValueFactory(cellData -> cellData.getValue().repairDateProperty());

    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }
    @FXML
    //2
    private void searchOwner(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //get owner info
            Owner owner = OwnerDAO.searchOwnerbyID(Integer.parseInt(ownerIdText.getText()));
            populateAndShowOwner(owner);
            ownerTable.setVisible(true);
            ownerTable.setManaged(true);
            repairsTable.setVisible(false);
            repairsTable.setManaged(false);

        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error occured while getting information from DB" + e);
            throw e;
        }
    }

    @FXML
    private void SearchAllOwners(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //get all
            ObservableList<Owner> ownerData = OwnerDAO.searchOwner();
            populateOwners(ownerData);
            ownerTable.setVisible(true);
            ownerTable.setManaged(true);
            repairsTable.setVisible(false);
            repairsTable.setManaged(false);
        } catch (SQLException e) {
            System.out.println("error");
            throw e;
        }
    }

    private void populateOwners(ObservableList<Owner> ownerData) throws ClassNotFoundException {
        ownerTable.setItems(ownerData);
    }

    @FXML
    //3
    private void populateAndShowOwner(Owner owner) throws ClassNotFoundException {
        if (owner != null) {
            populateOwner(owner);
            setOwnerInfoToTextArea(owner);
        } else {
            resultArea.setText("This owner does not exist!\n");
        }
    }

    //3
    @FXML
    private void setOwnerInfoToTextArea(Owner owner) {
        resultArea.setText("Name: " + owner.getName() + "email :" + owner.getEmail() + "address: " + owner.getAddress());
    }

    //4
    @FXML
    private void populateOwner(Owner owner) {
        //declare and observablelist for table view
        ObservableList<Owner> ownerData = FXCollections.observableArrayList();
        ownerData.add(owner);
        ownerTable.setItems(ownerData);
    }
//5

    //6
    @FXML
    private void insertOwner(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            OwnerDAO.insertOwnerInfo(ownerName.getText(), ownerEmail.getText(), ownerAddress.getText(), ownerPhone.getText());
            resultArea.setText("Employee inserted");
        } catch (SQLException e) {
            resultArea.setText("Problem occured" + e);
            throw e;
        }
    }

    //7
    @FXML
    private void deleteOwner(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            OwnerDAO.deleteOwnerWithId(Integer.parseInt(ownerIdText.getText()));
            resultArea.setText("employee deleted");
        } catch (SQLException e) {
            resultArea.setText("Problem");
            throw e;

        }


    }

@FXML
    public void searchRepairbyownerid(ActionEvent actionEvent) throws SQLException{
            try {
                int ownerId = Integer.parseInt(ownerIdText.getText());
                ObservableList<Repair> repairs = OwnerDAO.getRepairsByOwnerID(ownerId);
                populateRepairs(repairs);

                ownerTable.setVisible(false);
                ownerTable.setManaged(false);
                repairsTable.setVisible(true);
                repairsTable.setManaged(true);

            } catch (SQLException e) {
                System.out.println("Error occurred while getting information from DB.\n" + e);
                throw e;

            }
    }


@FXML
    private void populateRepairs(ObservableList<Repair> repairs) {
        repairsTable.setItems(repairs);
    }

    public void handleUpdate(ActionEvent actionEvent) {try {
        String ownerId = ownerIdText.getText();
        String name = ownerUpdateName.getText();
        String address = ownerUpdateAddress.getText();
        String phone = ownerUpdatePhone.getText();

        String email = ownerUpdateEmail.getText();


        OwnerDAO.updateOwner(ownerId, name,  address, phone,email);

        // Show confirmation message
        // For example, you might update a label or show an alert
        // showMessage("Owner updated successfully.");

    } catch (IllegalArgumentException e) {
        // Show error message for missing or invalid inputs
        // For example, you might update a label or show an alert
        // showMessage("Error: " + e.getMessage());
    } catch (SQLException | ClassNotFoundException e) {
        // Handle database errors
        // For example, you might log the error and show an error message
        // showMessage("Database error: " + e.getMessage());
        e.printStackTrace();
    }

    }
}
