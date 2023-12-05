package model;

import javafx.beans.property.*;

import java.util.Date;

public class Repair {
    private final IntegerProperty repairID = new SimpleIntegerProperty();
    private final IntegerProperty ownerID = new SimpleIntegerProperty();
    private final IntegerProperty carID = new SimpleIntegerProperty();
    private final ObjectProperty<Date> repairDate = new SimpleObjectProperty<>();
    private final StringProperty description = new SimpleStringProperty();
    private final IntegerProperty cost = new SimpleIntegerProperty();

    public Repair() {
    }

    public Repair(int repairID, int ownerID, int carID, Date repairDate, String description, int cost) {
        setRepairID(repairID);
        setOwnerID(ownerID);
        setCarID(carID);
        setRepairDate(repairDate);
        setDescription(description);
        setCost(cost);
    }

    public int getRepairID() {
        return repairID.get();
    }

    public IntegerProperty repairIDProperty() {
        return repairID;
    }

    public void setRepairID(int repairID) {
        this.repairID.set(repairID);
    }

    public int getOwnerID() {
        return ownerID.get();
    }

    public IntegerProperty ownerIDProperty() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID.set(ownerID);
    }

    public int getCarID() {
        return carID.get();
    }

    public IntegerProperty carIDProperty() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID.set(carID);
    }

    public Date getRepairDate() {
        return repairDate.get();
    }

    public ObjectProperty<Date> repairDateProperty() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate.set(repairDate);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getCost() {
        return cost.get();
    }

    public IntegerProperty costProperty() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost.set(cost);
    }

    @Override
    public String toString() {
        return "Repair{" +
                "repairID=" + repairID.get() +
                ", ownerID=" + ownerID.get() +
                ", carID=" + carID.get() +
                ", repairDate=" + repairDate.get() +
                ", description='" + description.get() + '\'' +
                ", cost=" + cost.get() +
                '}';
    }
}