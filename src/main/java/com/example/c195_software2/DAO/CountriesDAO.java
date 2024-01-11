package com.example.c195_software2.DAO;

import com.example.c195_software2.model.Country;

import java.util.HashMap;

/**
 * CountriesDAO interface
 */
public interface CountriesDAO {
    /**
     * Get all countries from database
     * @return HashMap with CountryID / Country as Key/Value pairs
     */
    HashMap<Integer, Country> getAllCountries();
}
