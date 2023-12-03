package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DButil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OwnerDAO {
    public static Owner searchEmployee(int OwnerId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM OWNER WHERE OWNERID = ";
        try  (Connection conn = DButil.dbConnect();
              PreparedStatement preparedStatement = conn.prepareStatement(selectStmt)) {

            preparedStatement.setInt(1, OwnerId);
            ResultSet rsOwn = preparedStatement.executeQuery();
            Owner owner= getOwnerFromResultSet(rsOwn);
            return owner;
        } catch (SQLException e) {
            System.out.println("Error while searching an employee: " + e);
            throw e;
        }
    }

    private static Owner getOwnerFromResultSet(ResultSet rsOwn) throws SQLException{
    }

}
