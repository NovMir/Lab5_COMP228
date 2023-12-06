package controller;

        import com.example.owner.Main;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import model.Owner;
        import model.Repair;
        import model.RepairDAO;

        import java.sql.SQLException;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;

public class RepairController {

    @FXML
    private TextField addRepairID;
    @FXML
    private TextField addOwnerID;
    @FXML
    private TextField addCarID;
    @FXML
    private TextField addDate;
    @FXML
    private TextField addDescription;
    @FXML
    private TextField addCost;
    @FXML
    private Button btnaddRepair;
    @FXML
    private TextField repairID;
    @FXML
    private TextField ownerID;
    @FXML
    private TextField carID;
    @FXML
    private TextField repairDate;
    @FXML
    private TextField description;
    @FXML
    private TextField repairCost;
    @FXML
    private Button btnupdateRepair;
    @FXML
    private TextArea resultArea;
    @FXML
    private TableView<Repair> repairsTable;
    @FXML
    private TableColumn<Repair, Integer> repairIDColumn;
    @FXML
    private TableColumn<Repair, Integer> ownerIDColumn;
    @FXML
    private TableColumn<Repair, Integer> carIDColumn;
    @FXML
    private TableColumn<Repair, String> dateColumn;
    @FXML
    private TableColumn<Repair, String> descriptionColumn;
    @FXML
    private TableColumn<Repair, Integer> costColumn;
    @FXML
    private Button btnsearchRepair;
    @FXML
    private Button btnsearchAll;
    @FXML
    private Button btndeleteRepair;
    @FXML
    private TextField ownerIdText;

    @FXML
    private TableColumn<Repair, Integer> repairIDcol;
    @FXML
    private TableColumn<Repair, Integer> ownerIDcol;
    @FXML
    private TableColumn<Repair, Integer> carIDcol;
    @FXML
    private TableColumn<Repair, String> descriptioncol;
    @FXML
    private TableColumn<Repair, Integer> costCol;
    @FXML
    private TableColumn<Repair, String> repairdatecol;

    private RepairDAO repairDAO = new RepairDAO();

    public RepairController() {
    }

    private Main mainApp;

    @FXML
    private void initialize() {
        // Initialize your TableView columns with the corresponding properties
        repairIDColumn.setCellValueFactory(new PropertyValueFactory<>("repairID"));
        ownerIDColumn.setCellValueFactory(new PropertyValueFactory<>("ownerID"));
        carIDColumn.setCellValueFactory(new PropertyValueFactory<>("carID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        repairIDcol.setCellValueFactory(new PropertyValueFactory<>("repairID"));
        ownerIDcol.setCellValueFactory(new PropertyValueFactory<>("ownerID"));
        carIDcol.setCellValueFactory(new PropertyValueFactory<>("carID"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        repairdatecol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    // Rest of the code...
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void searchRepairJob(ActionEvent actionEvent) throws SQLException {
        int searchRepairID = Integer.parseInt(ownerIdText.getText());
        Repair repair = RepairDAO.getRepairByID(searchRepairID);  // Use the instance
        if (repair != null) {
            resultArea.setText("Found Repair: " + repair.toString());
        } else {
            resultArea.setText("Repair not found for ID: " + searchRepairID);
        }
    }

    @FXML


    private void searchAllRepairID(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ObservableList<Repair> allRepairs = RepairDAO.getAllRepairs();
        populateRepair(allRepairs);
        if (!allRepairs.isEmpty()) {
            StringBuilder resultText = new StringBuilder("All Repairs:\n");
            for (Repair repair : allRepairs) {
                resultText.append(repair.toString()).append("\n");
            }
            resultArea.setText(resultText.toString());
        } else {
            resultArea.setText("No repairs found.");
        }
    }


    @FXML
    private void deleteRepairID(ActionEvent actionEvent) throws SQLException{
        int deleteRepairID;
        deleteRepairID = Integer.parseInt(ownerIdText.getText());
        RepairDAO.deleteRepairById(deleteRepairID);

        resultArea.setText("Repair deleted for ID: " + deleteRepairID);
    }



    @FXML
    private void addRepair(ActionEvent actionEvent) {
        try {
            int ownerId = Integer.parseInt(addOwnerID.getText());
            int carId = Integer.parseInt(addCarID.getText());
            String repairDate = addDate.getText();
            String description = addDescription.getText();
            int cost = Integer.parseInt(addCost.getText());

            RepairDAO.addRepair(ownerId, carId, repairDate, description, cost);
            resultArea.setText("Repair added successfully");
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid number format: " + e.getMessage());
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while inserting repair: " + e.getMessage());
        } catch (Exception e) {
            resultArea.setText("An error occurred: " + e.getMessage());
        }
    }

    private void populateRepair(ObservableList<Repair> repairData) throws ClassNotFoundException {
        repairsTable.setItems(repairData);
    }

/*    private void updateRepair() {
        Repair repair = createRepairFromFields();
        boolean success = repairDAO.updateRepair(repair);
        if (success) {
            resultArea.setText("Repair updated: " + repair.toString());
        } else {
            resultArea.setText("Failed to update repair for ID: " + repair.getRepairID());
        }
    }*/

    private Repair createRepairFromFields() {
        try {
            int repairId = Integer.parseInt(repairID.getText());
            int ownerId = Integer.parseInt(ownerID.getText());
            int carId = Integer.parseInt(carID.getText());
            String dateString = repairDate.getText();

            // Convert String date to Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);

            String descriptionText = description.getText();
            int cost = Integer.parseInt(repairCost.getText());

            return new Repair(repairId, ownerId, carId, date, descriptionText, cost);
        } catch (NumberFormatException | ParseException e) {
            resultArea.setText("Please enter valid numeric values for Repair ID, Owner ID, Car ID, and Cost, and a valid date format.");
            return null;
        }
    }

    private void clearAddFields() {
        addRepairID.clear();
        addOwnerID.clear();
        addCarID.clear();
        addDate.clear();
        addDescription.clear();
        addCost.clear();
    }

    public void SearchAllRepairID(ActionEvent actionEvent) {
    }
}
