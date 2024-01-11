package com.example.c195_software2.DAOImpl;

import com.example.c195_software2.DAO.ContactsDAO;
import com.example.c195_software2.database.DBConnection;
import com.example.c195_software2.model.Appointment;
import com.example.c195_software2.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Implementation of ContactsDAO
 */
public class ContactsDAOImpl implements ContactsDAO {
    private static final String selectAllContactsSQL = "SELECT * FROM contacts";
    private static final String getAppointmentsByContactID = "SELECT * FROM appointments WHERE Contact_ID = ?";
    @Override
    public ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectAllContactsSQL);
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Contact tempContact = new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email"));
                contactsList.add(tempContact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactsList;
    }

    @Override
    public ObservableList<Appointment> getContactSchedule(int contactID) {
        ObservableList<Appointment> contactScheduleList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getAppointmentsByContactID);
            ps.setString(1, String.valueOf(contactID));

            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                Appointment tempAppointment = new Appointment(ID, title, description, location, contID, type, start, end, customerID, userID);
                contactScheduleList.add(tempAppointment);
            }

            return contactScheduleList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
