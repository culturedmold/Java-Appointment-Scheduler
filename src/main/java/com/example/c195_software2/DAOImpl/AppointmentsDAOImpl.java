package com.example.c195_software2.DAOImpl;

import com.example.c195_software2.DAO.AppointmentsDAO;
import com.example.c195_software2.database.DBConnection;
import com.example.c195_software2.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Implementation of AppointmentsDAO
 */
public class AppointmentsDAOImpl implements AppointmentsDAO {
    private static final String getAllApptsSQL = "SELECT * FROM appointments";
    private static final String deleteApptSQL = "DELETE FROM appointments WHERE Appointment_ID = ?";
    private static final String deleteApptByCustSQL = "DELETE FROM appointments WHERE Customer_ID = ?";
    private static final String createNewApptSQL = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String updateApptSQL = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, Last_Update = ?, Last_Updated_By = ? WHERE Appointment_ID = ?";
    private static final String getApptsByWeekSQL = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(?)";
    private static final String getApptsByMonthSQL = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(?)";
    @Override
    public ObservableList<Appointment> getAllAppts() {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getAllApptsSQL);
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");


                Appointment tempAppointment = new Appointment(ID, title, description, location, contactID, type, start, end, customerID, userID);
                appointmentsList.add(tempAppointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;
    }

    @Override
    public ObservableList<Appointment> getAllAppointmentsByMonth(String date) {
        ObservableList<Appointment> appointmentsByMonthList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getApptsByMonthSQL);
            ps.setString(1, date);
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                Appointment tempAppointment = new Appointment(ID, title, description, location, contactID, type, start, end, customerID, userID);
                appointmentsByMonthList.add(tempAppointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsByMonthList;
    }

    @Override
    public ObservableList<Appointment> getAllAppointmentsByWeek(String date) {
        ObservableList<Appointment> appointmentsByWeekList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getApptsByWeekSQL);
            ps.setString(1, date);
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                Appointment tempAppointment = new Appointment(ID, title, description, location, contactID, type, start, end, customerID, userID);
                appointmentsByWeekList.add(tempAppointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsByWeekList;
    }

    @Override
    public boolean deleteAppt(int apptID) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteApptSQL);
            ps.setString(1, String.valueOf(apptID));

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteApptByCust(int custID) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteApptByCustSQL);
            ps.setString(1, String.valueOf(custID));

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean createAppt(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, String custID, String userID, String contactID) {

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(createNewApptSQL);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start)); // YYYY-MM-DD HH:MI:SS
            ps.setTimestamp(6, Timestamp.valueOf(end)); // YYYY-MM-DD HH:MI:SS
            ps.setString(7, custID);
            ps.setString(8, userID);
            ps.setString(9, contactID);
            ps.setString(10, LocalDateTime.now().toString());
            ps.setString(11, userID);
            ps.setString(12, LocalDateTime.now().toString());
            ps.setString(13, userID);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAppt(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, String custID, String userID, String contactID, String appointmentID) {

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(updateApptSQL);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, custID);
            ps.setString(8, userID);
            ps.setString(9, contactID);
            ps.setString(10, LocalDateTime.now().toString());
            ps.setString(11, userID);
            ps.setString(12, appointmentID);

            System.out.println(ps);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
