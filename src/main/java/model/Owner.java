package model;
import util.DButil;

public class Owner {
    private int ownerId;
    private String name;
    private String address;
    private String phone;
    private String email;

    // Constructor
    public Owner(int ownerId, String name, String address, String phone, String email) {
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getters
    public int getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


