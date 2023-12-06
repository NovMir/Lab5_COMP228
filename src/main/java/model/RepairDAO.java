package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DButil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairDAO {
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
    private static Repair getRepairFromResultSet(ResultSet rs) throws SQLException {
        Repair repair = new Repair();
        if (rs.next()) {
            repair.setRepairId(rs.getInt("repair_id"));
            repair.setOwnerId(rs.getInt("owner_id"));
            repair.setCarId(rs.getInt("car_id"));
            repair.setRepairDate(rs.getDate("repair_date")); // Assuming this is a SQL Date
            repair.setDescription(rs.getString("description"));
            repair.setCost(rs.getInt("cost"));
        }
        return repair;
    }



    public static void addRepair(int ownerId, int carId, String repairDate, String description, int cost) throws SQLException {
        String sqlStmt = "INSERT INTO repair_jobs (repairid, ownerid, carid, repair_date, description, cost) " +
                "VALUES (Repair_ID_Seq.NEXTVAL, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";

        Object[] params = { ownerId, carId, repairDate, description, cost };

        try {
            DButil.dbExecuteUpdate(sqlStmt, params);
        } catch (SQLException e) {
            System.out.println("Could not insert repair job: " + e);
            throw e;
        }
    }

    public static Repair getRepairByID(int repairID) throws SQLException {
        String selectStmt = "SELECT * FROM repair_jobs WHERE repair_id = ?";
        try (Connection conn = DButil.dbConnect();
             PreparedStatement preparedStatement = conn.prepareStatement(selectStmt)) {

            preparedStatement.setInt(1, repairID);

            ResultSet rsRep = DButil.dbExecutePreparedQuery(preparedStatement);
            return getRepairFromResultSet(rsRep);
        } catch (SQLException e) {
            System.out.println("Error while searching for a repair: " + e);
            throw e;
        }
    }


    public static ObservableList<Repair> getAllRepairs() throws SQLException, ClassNotFoundException {
        // Declare a SELECT statement
        String selectStmt = "SELECT * FROM repair_jobs";
        Connection conn = null;

        // Execute SELECT statement
        try {
            // Get ResultSet from dbExecuteQuery method
            ResultSet rsRep = DButil.dbExecuteQuery(selectStmt);

            // Send ResultSet to the getRepairList method and get repair list
            ObservableList<Repair> repairList = getRepairList(rsRep);

            // Return repair list
            return repairList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
                System.out.println("Db is disconnected");
            }
        }
    }



    public static void updateRepairJob(int ownerId, int carId, String repairDate, String description, int cost, int repairId) throws SQLException {
        String sqlStmt = "UPDATE repair_job SET owner_id = ?, car_id = ?, repair_date = ?, description = ?, cost = ? WHERE repair_id = ?";

        try (Connection conn = DButil.dbConnect();
             PreparedStatement pstmt = conn.prepareStatement(sqlStmt)) {

            pstmt.setInt(1, ownerId);
            pstmt.setInt(2, carId);
            pstmt.setDate(3, java.sql.Date.valueOf(repairDate)); // assuming repairDate is in 'YYYY-MM-DD' format
            pstmt.setString(4, description);
            pstmt.setInt(5, cost);
            pstmt.setInt(6, repairId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not update repair job: " + e);
            throw e;
        }
    }


    public static void deleteRepairById(int repairID) {
        try {
            String sql = "DELETE FROM repairs WHERE repair_id = ?";
            Object[] params = { repairID };

            // Use dbExecuteUpdate from DBUtil to execute the prepared statement
            DButil.dbExecuteUpdate(sql, params);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    // Close resources, e.g., Connection, PreparedStatement, ResultSet
    private void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}