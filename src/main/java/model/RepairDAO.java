package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairDAO {
    private static final String JDBC_URL = "jdbc:oracle:thin:@your_oracle_host:your_oracle_port:your_oracle_sid";
    private static final String JDBC_USER = "your_username";
    private static final String JDBC_PASSWORD = "your_password";

    public RepairDAO() {
        // Ensure the JDBC driver is loaded
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Oracle JDBC Driver not found.");
        }
    }

    public boolean addRepair(Repair repair) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO repairs (repair_id, owner_id, car_id, repair_date, description, cost) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, repair.getRepairID());
                preparedStatement.setInt(2, repair.getOwnerID());
                preparedStatement.setInt(3, repair.getCarID());
                preparedStatement.setDate(4, new java.sql.Date(repair.getRepairDate().getTime()));
                preparedStatement.setString(5, repair.getDescription());
                preparedStatement.setInt(6, repair.getCost());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Repair getRepairByID(int repairID) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM repairs WHERE repair_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, repairID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractRepairFromResultSet(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<Repair> getAllRepairs() {
        List<Repair> repairList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM repairs";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    repairList.add(extractRepairFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repairList;
    }

    private Repair extractRepairFromResultSet(ResultSet resultSet) throws SQLException {
        int repairID = resultSet.getInt("repair_id");
        int ownerID = resultSet.getInt("owner_id");
        int carID = resultSet.getInt("car_id");
        Date repairDate = resultSet.getDate("repair_date");
        String description = resultSet.getString("description");
        int cost = resultSet.getInt("cost");

        return new Repair(repairID, ownerID, carID, repairDate, description, cost);
    }

    public boolean updateRepair(Repair repair) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE repairs SET owner_id=?, car_id=?, repair_date=?, description=?, cost=? WHERE repair_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, repair.getOwnerID());
                preparedStatement.setInt(2, repair.getCarID());
                preparedStatement.setDate(3, new java.sql.Date(repair.getRepairDate().getTime()));
                preparedStatement.setString(4, repair.getDescription());
                preparedStatement.setInt(5, repair.getCost());
                preparedStatement.setInt(6, repair.getRepairID());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRepairById(int repairID) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "DELETE FROM repairs WHERE repair_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, repairID);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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