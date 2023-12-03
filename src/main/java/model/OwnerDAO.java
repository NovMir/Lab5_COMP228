package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DButil;

import java.sql.*;


public class OwnerDAO {
    public static Owner searchOwner(int OwnerId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM OWNER WHERE OWNERID = ";
        try (Connection conn = DButil.dbConnect();
             PreparedStatement preparedStatement = conn.prepareStatement(selectStmt)) {

            preparedStatement.setInt(1, OwnerId);
            ResultSet rsOwn = preparedStatement.executeQuery();
            Owner owner = getOwnerFromResultSet(rsOwn);
            return owner;
        } catch (SQLException e) {
            System.out.println("Error while searching an employee: " + e);
            throw e;
        }
    }

    private static Owner getOwnerFromResultSet(ResultSet rsOwn) throws SQLException {
        Owner owner = null;
        if (rsOwn.next()) {
            int ownerId = rsOwn.getInt("OWNERID");
            String name = rsOwn.getString("NAME");
            String address = rsOwn.getString("ADDRESS");
            String phone = rsOwn.getString("PHONE");
            String email = rsOwn.getString("EMAIL");

            owner = new Owner(ownerId, name, address, phone, email);
        }


        return owner;
    }

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

            int ownerId = rsOwn.getInt("OWNERID");
            String name = rsOwn.getString("NAME");
            String address = rsOwn.getString("ADDRESS");
            String phone = rsOwn.getString("PHONE");
            String email = rsOwn.getString("EMAIL");
            System.out.println("Retrieved: " + ownerId + ", " + name + ", " + address + ", " + phone + ", " + email);

            Owner owner = new Owner(ownerId, name, address, phone, email);

            ownerList.add(owner);
        }
        //return empList (ObservableList of Employees)
        return ownerList;
    }

    //update owner
    public static void updateOwner(int ownerId, String name, String address, String phoneNumber, String email) throws SQLException {
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
    }

}
