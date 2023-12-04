package model;

import util.DButil;

import java.sql.SQLException;



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
            }
            return car;
        }

        // Select all Cars
        public static ObservableList<Car> searchCars() throws SQLException, ClassNotFoundException {
            String selectStmt = "SELECT * FROM CAR";
            try (Connection conn = DButil.dbConnect();
                 ResultSet rsCar = DButil.dbExecuteQuery(selectStmt)) {

                ObservableList<Car> carList = getCarList(rsCar);
                return carList;
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("SQL select operation has been failed: " + e);
                throw e;
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
                carList.add(car);
            }
            return carList;
        }

        // Update Car
        public static void updateCar(int carId, String model, int vin, int buildYear, String type) throws SQLException {
            String sql = "UPDATE CAR SET MODEL = ?, VIN = ?, BUILDYEAR = ?, TYPE = ? WHERE CARID = ?";
            Object[] params = {model, vin, buildYear, type, carId};
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


