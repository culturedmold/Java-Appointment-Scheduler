package com.example.c195_software2.model;

/**
 * Customer Model
 */
public class Customer {
    private final Integer ID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private Integer divisionID;

    public Customer(Integer ID, String name, String address, String postalCode, String phone, Integer divisionID) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    @Override
    public String toString() {
        String toStr = this.getName() + " - " + this.getID();
        return toStr;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Integer divisionID) {
        this.divisionID = divisionID;
    }
}
