package com.example.c195_software2.model;

/**
 * Division Model
 */
public class Division {
    private int divisionID;
    private int countryID;
    private String divisionName;


    public Division(int divisionID, int countryID, String divisionName) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.divisionName = divisionName;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    @Override
    public String toString() {
        return this.getDivisionID() + " - " + this.getDivisionName();
    }
}
