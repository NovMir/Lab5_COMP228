package model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//changed standard types to property classes.bind to field in table
public class Owner {
 //declare owner table column
    private IntegerProperty ownerId;
    private StringProperty name;
    private StringProperty address;
    private StringProperty phone;
    private StringProperty email;
    // Constructor
    public Owner() {
        this.ownerId = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
    }

    // Getters
    public int getOwnerId() {
        return ownerId.get();
    }

    public String getName() {
        return this.name.get();
    }

    public String getAddress() {
        return this.address.get();
    }

    public String getPhone() {
        return this.phone.get();
    }

    public String getEmail() {
        return this.email.get();
    }

    // Setters
    public void setOwnerId(int ownerId) {
        this.ownerId.set(ownerId);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public IntegerProperty ownerIdProperty(){
        return ownerId;
    }
    public StringProperty nameProperty(){
        return name;
    }
   public StringProperty addressProperty(){
        return  address;
   }
   public StringProperty phoneProperty(){
        return  phone;
   }
   public StringProperty emailProperty(){
        return email;
   }
}


