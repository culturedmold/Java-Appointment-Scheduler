package com.example.c195_software2.DAOImpl;

import com.example.c195_software2.DAO.CountriesDAO;
import com.example.c195_software2.database.DBConnection;
import com.example.c195_software2.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Implementation of CountriesDAO
 */
public class CountriesDAOImpl implements CountriesDAO {
    private static final String selectAllCountriesSQL = "SELECT * FROM countries";

    @Override
    public HashMap<Integer, Country> getAllCountries() {
        HashMap<Integer, Country> countriesHashmap = new HashMap<>();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectAllCountriesSQL);
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Country tempCountry = new Country(Integer.valueOf(rs.getInt("Country_ID")), rs.getString("Country"));
                countriesHashmap.put(tempCountry.getCountryID(), tempCountry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countriesHashmap;
    }
}
