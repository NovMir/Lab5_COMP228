package model;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.sql.Date;


public class Repair {
    private IntegerProperty repairId;
    private IntegerProperty ownerId;
    private IntegerProperty carId;
    private SimpleObjectProperty<Date> repair_date;
    private StringProperty description;
    private IntegerProperty cost;

    //constructor
    public Repair() {
        this.carId = new SimpleIntegerProperty();
        this.ownerId = new SimpleIntegerProperty();
        this.repairId = new SimpleIntegerProperty();
        this.repair_date = new SimpleObjectProperty<>();
        this.description = new SimpleStringProperty();
        this.cost = new SimpleIntegerProperty();
    }

    public Repair(int repairID, int ownerID, int carID, java.util.Date repairDate, String description, int cost) {
        setRepairId(repairID);
        setOwnerId(ownerID);
        setCarId(carID);
        setRepairDate((Date) repairDate);
        setDescription(description);
        setCost(cost);
    }

    //repair id
    public int getRepairId(){
        return repairId.get();
    }

    public void setRepairId(int repairId) {
        this.repairId.set(repairId);
    }

    public IntegerProperty repairIdProperty() {
        return repairId;
    }
    //carid

    public int getCarId(){
        return carId.get();
    }
    public void setCarId(int carId) {
        this.carId.set(carId);
    }

    public  IntegerProperty carIdProperty(){
        return carId;
    }

    //ownerid
    public int getownerid(){
        return ownerId.get();
    }

    public void setOwnerId(int ownerId){
        this.ownerId.set(ownerId);
    }
    public IntegerProperty ownerIdproperty(){
        return ownerId;
    }

    public String getdescription(){
        return description.get();
    }

    public void setDescription(String description){
        this.description.get();
    }

    public StringProperty descriptionproperty(){
        return description;
    }
    //phone_number
    public int getcost () {
        return cost.get();
    }
    public void setCost (int cost){
        this.cost.set(cost);
    }
    public IntegerProperty costProperty() {
        return cost;
    }
    //hire_date
    public Object getRepairDate(){
        return repair_date.get();
    }
    public void setRepairDate(Date repairDate){
        this.repair_date.set(repairDate);
    }
    public SimpleObjectProperty<Date> repairDateProperty(){
        return repair_date;
    }
}