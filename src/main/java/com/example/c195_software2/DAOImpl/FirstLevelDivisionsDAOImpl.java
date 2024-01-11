package com.example.c195_software2.DAOImpl;

import com.example.c195_software2.DAO.FirstLevelDivisionsDAO;
import com.example.c195_software2.database.DBConnection;
import com.example.c195_software2.model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Implementation of FirstLevelDivisionsDAO
 */
public class FirstLevelDivisionsDAOImpl implements FirstLevelDivisionsDAO {
    public static final String selectAllDivisionsSQL = "SELECT * FROM first_level_divisions";
    public static final String selectDivisionsByCountryIDSQL = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";

    @Override
    public HashMap<Integer, Division> getAllDivisions() {
        HashMap<Integer, Division> divisionHashMap = new HashMap<>();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectAllDivisionsSQL);
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Division tempDivision = new Division(rs.getInt("Division_ID"), rs.getInt("Country_ID"), rs.getString("Division"));
                divisionHashMap.put(tempDivision.getDivisionID(), tempDivision);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return divisionHashMap;
    }

    @Override
    public HashMap<Integer, Division> getDivisionByCountryID(Integer countryID) {
        HashMap<Integer, Division> divisionHashMap = new HashMap<>();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectDivisionsByCountryIDSQL);
            ps.setString(1, String.valueOf(countryID));
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Division tempDivision = new Division(rs.getInt("Division_ID"), rs.getInt("Country_ID"), rs.getString("Division"));
                divisionHashMap.put(tempDivision.getDivisionID(), tempDivision);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return divisionHashMap;
    }
}
