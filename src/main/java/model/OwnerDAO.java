package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DButil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;





import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OwnerDAO {


    //
    private static ObservableList<Repair> getRepairList(ResultSet rsRepair) throws SQLException {
        System.out.println("Entering getRepairList method.");
        ResultSetMetaData rsmd = rsRepair.getMetaData();
        System.out.println("Number of columns: " + rsmd.getColumnCount());
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            System.out.println("Column " + i + ": " + rsmd.getColumnName(i));
        }
        //Declare an observable List which comprises of repair objects
        ObservableList<Repair> repairList = FXCollections.observableArrayList();
        if (!rsRepair.isBeforeFirst()) {
            System.out.println("ResultSet is empty.");
        }
        while (rsRepair.next()) {
            Repair repair = new Repair();
            repair.setRepairId(rsRepair.getInt("REPAIRID"));
            repair.setOwnerId(rsRepair.getInt("OWNERID"));
            repair.setCarId(rsRepair.getInt("CARID"));
            repair.setDescription(rsRepair.getString("DESCRIPTION"));
            repair.setCost(rsRepair.getInt("COST"));
            repair.setRepairDate(rsRepair.getDate("REPAIR_DATE"));
            repairList.add(repair);
        }
        return repairList;
    }


    public static ObservableList<Repair> getRepairsByOwnerID (int OwnerId) throws SQLException {
        String selectStmt = "SELECT * FROM REPAIR_JOBS WHERE OWNERId = ?";
        try (Connection conn = DButil.dbConnect();
             PreparedStatement preparedStatement = conn.prepareStatement(selectStmt)) {
            preparedStatement.setInt(1, (OwnerId));

            try (ResultSet rsRepair = DButil.dbExecutePreparedQuery(preparedStatement)) {
                // Use getRepairList to process the ResultSet and return the list
                return getRepairList(rsRepair);
            }
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }



    //return empList (ObservableList of Employees)

    //select an owner
    public static Owner searchOwnerbyID(int OwnerId) throws SQLException {
        String selectStmt = "SELECT * FROM OWNER WHERE OWNERID = ?";
        try (Connection conn = DButil.dbConnect();
             PreparedStatement preparedStatement = conn.prepareStatement(selectStmt)) {

            preparedStatement.setInt(1, OwnerId);
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsOwn = DButil.dbExecutePreparedQuery(preparedStatement);
            Owner owner = getOwnerFromResultSet(rsOwn);
            return owner;
        } catch (SQLException e) {
            System.out.println("Error while searching an employee: " + e);
            throw e;
        }
    }
    //Use ResultSet from DB as parameter and set Employee Object's attributes and return employee object.
    private static Owner getOwnerFromResultSet(ResultSet rsOwn) throws SQLException {
        Owner owner = null;
        if (rsOwn.next()) {
            owner = new Owner();
            owner.setOwnerId(rsOwn.getInt("OWNERID"));
            owner.setName(rsOwn.getString("NAME"));
            owner.setAddress(rsOwn.getString("ADDRESS"));
            owner.setPhone(rsOwn.getString("PHONE"));
            owner.setEmail(rsOwn.getString("EMAIL"));
        }

            return owner;
        }



    //select owners
    public static ObservableList<Owner> searchOwner() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM OWNER";
        Connection conn = null;
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsOwn = DButil.dbExecuteQuery(selectStmt);
            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Owner> ownerList = getOwnerList(rsOwn);
            //Return employee object
            return ownerList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
                System.out.println("Db is disconnected");
            }
        }
    }

    //Select * from employees operation
    private static ObservableList<Owner> getOwnerList(ResultSet rsOwn) throws SQLException, ClassNotFoundException {
        System.out.println("Entering getOwnerList method.");
        ResultSetMetaData rsmd = rsOwn.getMetaData();
        System.out.println("Number of columns: " + rsmd.getColumnCount());
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            System.out.println("Column " + i + ": " + rsmd.getColumnName(i));
        }
        //Declare an observable List which comprises of owner objects
        ObservableList<Owner> ownerList = FXCollections.observableArrayList();
        if (!rsOwn.isBeforeFirst()) {
            System.out.println("ResultSet is empty.");
        }
        while (rsOwn.next()) {
            Owner owner = new Owner();
            owner.setOwnerId(rsOwn.getInt("OWNERID"));
            owner.setName(rsOwn.getString("NAME"));
            owner.setAddress(rsOwn.getString("ADDRESS"));
            owner.setPhone(rsOwn.getString("PHONE"));
            owner.setEmail(rsOwn.getString("EMAIL"));
            System.out.println("Retrieved: " +
                    owner.getOwnerId() + ", " +
                    owner.getName() + ", " +
                    owner.getAddress() + ", " +
                    owner.getPhone() + ", " +
                    owner.getEmail());
            ownerList.add(owner);
        }
        //return empList (ObservableList of Employees)
            return ownerList;
        }


    //update owner
   /* public static void updateOwner(int ownerId, String name, String address, String phoneNumber, String email) throws SQLException {
        // SQL statement now includes the email field
        String sql = "UPDATE owner SET name = ?, address = ?, phone = ?, email = ? WHERE ownerid = ?";

        // Include email in the array of parameters
        Object[] params = {name, address, phoneNumber, email, ownerId};

        try {
            // Execute the update
            DButil.dbExecuteUpdate(sql, params);
        } catch (SQLException e) {
            // Handle any SQL exceptions
            throw e;
        }
    }*/
    public static void updateOwner(String ownerId, String name,  String address,String phone,String email ) throws SQLException, ClassNotFoundException {
        // Validate that empId is provided
        if (ownerId == null || ownerId.isEmpty()) {
            throw new IllegalArgumentException("Owner ID is required.");
        }

        StringBuilder sql = new StringBuilder("UPDATE OWNER SET ");
        List<Object> params = new ArrayList<>();

        // Add parameters to the query if they are provided
        if (name != null && !name.isEmpty()) {
            sql.append("NAME = ?, ");
            params.add(name);
        }
        if (email != null && !email.isEmpty()) {
            sql.append("ADDRESS= ?, ");
            params.add(email);
        }
        if (phone != null && !phone.isEmpty()) {
            sql.append("PHONE = ?, ");
            params.add(phone);
        }
        if (address != null && !address.isEmpty()) {
            sql.append("EMAIL = ?, ");
            params.add(address);
        }

        // Check if any fields were updated, throw exception if none
        if (params.isEmpty()) {
            throw new IllegalArgumentException("No fields to update were provided.");
        }

        // Remove the last comma and space
        int lastCommaIndex = sql.lastIndexOf(",");
        if (lastCommaIndex != -1) {
            sql.deleteCharAt(lastCommaIndex);
        }

        // Finalize the SQL statement
        sql.append(" WHERE OWNERID = ?");
        params.add(ownerId);

        // Convert params list to an array and execute the update
        Object[] paramArray = params.toArray(new Object[0]);
        DButil.dbExecuteUpdate(sql.toString(), paramArray);
        System.out.println("Executing SQL: " + sql.toString());
        System.out.println("With parameters: " + Arrays.toString(paramArray));

    }

    //delete owner
    public static void deleteOwnerWithId(int ownerId) throws SQLException {
        String delstmt = "DELETE FROM OWNER WHERE OWNERID = ?";
        Object[] params = {ownerId};
        try{
            DButil.dbExecuteUpdate(delstmt, params);
        }catch(SQLException e){
            System.out.println("Error during Delete operation: " + e);
            throw e;
        }
    }
    public static void insertOwnerInfo( String name, String address, String phone, String email) throws SQLException{
        String sqlStmt = "INSERT INTO OWNER (OWNERID, NAME, ADDRESS, PHONE, EMAIL) VALUES (OWNER_ID_SEQ.NEXTVAL, ?, ?, ?, ?)";
        Object[] params = { name, address, phone, email};
        try{
            DButil.dbExecuteUpdate(sqlStmt, params);
        }catch (SQLException e){
            System.out.println("Could not insert" +  e);
            throw e;
        }
    }
}
