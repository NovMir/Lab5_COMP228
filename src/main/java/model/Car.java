package model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

    public class Car {
        // Declare Car table columns using JavaFX properties
        private IntegerProperty carId;
        private StringProperty model;
        private IntegerProperty vin;
        private IntegerProperty buildYear;
        private StringProperty type;

        // Constructor
        public Car() {
            this.carId = new SimpleIntegerProperty();
            this.model = new SimpleStringProperty();
            this.vin = new SimpleIntegerProperty();
            this.buildYear = new SimpleIntegerProperty();
            this.type = new SimpleStringProperty();
        }

        // Getters
        public int getCarId() {
            return carId.get();
        }

        public String getModel() {
            return model.get();
        }

        public int getVin() {
            return vin.get();
        }

        public int getBuildYear() {
            return buildYear.get();
        }

        public String getType() {
            return type.get();
        }

        // Setters
        public void setCarId(int carId) {
            this.carId.set(carId);
        }

        public void setModel(String model) {
            this.model.set(model);
        }

        public void setVin(int vin) {
            this.vin.set(vin);
        }

        public void setBuildYear(int buildYear) {
            this.buildYear.set(buildYear);
        }

        public void setType(String type) {
            this.type.set(type);
        }

        // Property accessors
        public IntegerProperty carIdProperty() {
            return carId;
        }

        public StringProperty modelProperty() {
            return model;
        }

        public IntegerProperty vinProperty() {
            return vin;
        }

        public IntegerProperty buildYearProperty() {
            return buildYear;
        }

        public StringProperty typeProperty() {
            return type;
        }
    }


