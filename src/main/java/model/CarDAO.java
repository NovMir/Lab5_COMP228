package model;
import util.DButil;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.*;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DButil;

import java.sql.*;

    public class CarDAO {
        // Select a Car by ID
        public static Car searchCarByID(int carId) throws SQLException {
            String selectStmt = "SELECT * FROM CAR WHERE CARID = ?";
            try (Connection conn = DButil.dbConnect();
                 PreparedStatement preparedStatement = conn.prepareStatement(selectStmt)) {

                preparedStatement.setInt(1, carId);
                ResultSet rsCar = DButil.dbExecutePreparedQuery(preparedStatement);
                Car car = getCarFromResultSet(rsCar);
                return car;
            } catch (SQLException e) {
                System.out.println("Error while searching a car: " + e);
                throw e;
            }
        }
        private static Repair getRepairFromResultSet(ResultSet rsRepair) throws SQLException {
            Repair repair = null;
            if (rsRepair.next()) {
                repair = new Repair();
                repair.setRepairId(rsRepair.getInt("REPAIRID"));
                repair.setOwnerId(rsRepair.getInt("OWNERID"));
                repair.setCarId(rsRepair.getInt("CARID"));
                repair.setDescription(rsRepair.getString("DESCRIPTION"));
                repair.setCost(rsRepair.getInt("COST"));
                repair.setRepairDate(rsRepair.getDate("REPAIR_DATE"));
            }
            return repair;
        }
        //resultset for repair

        private static ObservableList<Repair> getRepairList(ResultSet rsRepair) throws SQLException, ClassNotFoundException {
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



            //return empList (ObservableList of Employees)
            return repairList;
        }


        // Use ResultSet from DB as parameter and set Car Object's attributes
        private static Car getCarFromResultSet(ResultSet rsCar) throws SQLException {
            Car car = null;
            if (rsCar.next()) {
                car = new Car();
                car.setCarId(rsCar.getInt("CARID"));
                car.setModel(rsCar.getString("MODEL"));
                car.setVin(rsCar.getInt("VIN"));
                car.setBuildYear(rsCar.getInt("BUILDYEAR"));
                car.setType(rsCar.getString("TYPE"));
                car.setMake_car(rsCar.getString("MAKE_CAR"));
            }
            return car;
        }
        public static ObservableList<Repair> getRepairsByCarID(int carID) throws SQLException, ClassNotFoundException {
            String selectStmt = "SELECT * FROM REPAIR_JOBS WHERE carId = ?";
            try (Connection conn = DButil.dbConnect();
                 PreparedStatement preparedStatement = conn.prepareStatement(selectStmt)) {
                preparedStatement.setInt(1, carID);

                try (ResultSet rsRepair = DButil.dbExecutePreparedQuery(preparedStatement)) {
                    // Use getRepairList to process the ResultSet and return the list
                    return getRepairList(rsRepair);
                }
            } catch (SQLException e) {
                System.out.println("SQL select operation has been failed: " + e);
                throw e;
            }
        }



        // Select all Cars
        public static ObservableList<Car> searchCars() throws SQLException, ClassNotFoundException {
            String selectStmt = "SELECT * FROM CAR";
            Connection conn = null;
            try {
                ResultSet rsCar = DButil.dbExecuteQuery(selectStmt);

                ObservableList<Car> carList = getCarList(rsCar);
                return carList;
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("SQL select operation has been failed: " + e);
                throw e;
            }finally {
                if (conn != null) {
                    conn.close();
                    System.out.println("Db is disconnected");}
            }
        }

        // Convert ResultSet to ObservableList
        private static ObservableList<Car> getCarList(ResultSet rsCar) throws SQLException {
            ObservableList<Car> carList = FXCollections.observableArrayList();
            while (rsCar.next()) {
                Car car = new Car();
                car.setCarId(rsCar.getInt("CARID"));
                car.setModel(rsCar.getString("MODEL"));
                car.setVin(rsCar.getInt("VIN"));
                car.setBuildYear(rsCar.getInt("BUILDYEAR"));
                car.setType(rsCar.getString("TYPE"));
                car.setMake_car(rsCar.getString("MAKE_CAR"));
                carList.add(car);
            }
            return carList;
        }

        // Update Car
        public static void updateCar(int carId, int vin, int buildYear) throws SQLException {
            String sql = "UPDATE CAR SET , VIN = ?, BUILDYEAR = ?, WHERE CARID = ?";
            Object[] params = { vin, buildYear, carId};
            try {
                DButil.dbExecuteUpdate(sql, params);
            } catch (SQLException e) {
                System.out.println("Error during Car update operation: " + e);
                throw e;
            }
        }

        // Delete Car
        public static void deleteCarWithId(int carId) throws SQLException {
            String delStmt = "DELETE FROM CAR WHERE CARID = ?";
            Object[] params = {carId};
            try {
                DButil.dbExecuteUpdate(delStmt, params);
            } catch (SQLException e) {
                System.out.println("Error during Car Delete operation: " + e);
                throw e;
            }
        }

        // Insert Car
        public static void insertCarInfo(String model, int vin, int buildYear, String type) throws SQLException {
            String sqlStmt = "INSERT INTO CAR (CARID, MODEL, VIN, BUILDYEAR, TYPE) VALUES (CAR_ID_SEQ.NEXTVAL, ?, ?, ?, ?)";
            Object[] params = {model, vin, buildYear, type};
            try {
                DButil.dbExecuteUpdate(sqlStmt, params);


            } catch (SQLException e) {
                System.out.println("Could not insert car: " + e);
                throw e;
            }
        }
    }


