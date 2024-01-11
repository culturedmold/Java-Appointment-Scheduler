package com.example.c195_software2.DAO;

import com.example.c195_software2.model.Division;

import java.util.HashMap;

/**
 * FirstLevelDivisionDAO interface
 */
public interface FirstLevelDivisionsDAO {
    /**
     * Get all first level divisions from database
     * @return HashMap - Division ID / Division as Key / Value pairs
     */
    HashMap<Integer, Division> getAllDivisions();

    /**
     * Get all first level divisions from database by country ID
     * @return HashMap - Division ID / Division as Key / Value pairs
     */
    HashMap<Integer, Division> getDivisionByCountryID(Integer countryID);
}
