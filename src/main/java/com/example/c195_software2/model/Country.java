package com.example.c195_software2.model;

/**
 * Country Model
 */
public class Country {
    public Integer CountryID;
    public String name;

    public Country(int ID, String name) {
        this.name = name;
        this.CountryID = ID;
    }

    public int getCountryID() {
        return CountryID;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.CountryID + " - " + this.name;
    }
}
