package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Owner;
import model.OwnerDAO;

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
    //1
    private void initialize() {
        //on fxml file loading will be called automatically updates will be added
        //initializing the controller class
        ownerIdColumn.setCellValueFactory(cellData -> cellData.getValue().ownerIdProperty());
        ownerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ownerAddressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        ownerPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        ownerEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());


    }

    @FXML
    //2
    private void searchOwner(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //get owner info
            Owner owner = OwnerDAO.searchOwnerbyID(Integer.parseInt(ownerIdText.getText()));
            populateAndShowOwner(owner);

        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error occured while getting information from DB" + e);
            throw e;
        }
    }
    @FXML private void SearchAllOwners(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        try {
            //get all
            ObservableList<Owner> ownerData = OwnerDAO.searchOwner();
            populateOwners(ownerData);
        }catch (SQLException e){
            System.out.println("error");
            throw e;
        }
    }

    private void populateOwners(ObservableList<Owner> ownerData) throws ClassNotFoundException {
        ownerTable.setItems(ownerData);
    }

    @FXML
        //3
        private void populateAndShowOwner (Owner owner) throws ClassNotFoundException {
            if (owner != null) {
                populateOwner(owner);
                setOwnerInfoToTextArea(owner);
            } else {
                resultArea.setText("This owner does not exist!\n");
            }
        }
//3
  @FXML  private void setOwnerInfoToTextArea(Owner owner) {
        resultArea.setText("Name: " + owner.getName() + "email :" + owner.getEmail() + "address: " + owner.getAddress());
    }
//4
   @FXML private void populateOwner(Owner owner) {
        //declare and observablelist for table view
        ObservableList<Owner> ownerData = FXCollections.observableArrayList();
        ownerData.add(owner);
        ownerTable.setItems(ownerData);
    }
//5

    //6
    @FXML private void insertOwner(ActionEvent actionEvent) throws SQLException,ClassNotFoundException {
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
    private void deleteOwner(ActionEvent actionEvent) throws SQLException,ClassNotFoundException{
        try{
            OwnerDAO.deleteOwnerWithId(Integer.parseInt(ownerIdText.getText()));
            resultArea.setText("emplyee deleted");}
            catch(SQLException e){
                resultArea.setText("Problem");
                throw e;

            }


    }


}
