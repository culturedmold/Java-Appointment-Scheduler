package com.example.c195_software2.util;

import com.example.c195_software2.DAOImpl.AppointmentsDAOImpl;
import com.example.c195_software2.database.DBConnection;
import com.example.c195_software2.model.Appointment;
import com.example.c195_software2.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Session Class
 * Handles authentication and login activity recording
 */
public class Session {
    // user authentication query
    private static final String sessionAuthSQL = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
    // DAOImpl user is initialized if authenticateUser method returns true
    private static User sessionUser = null;

    public static LocalDateTime getLoginTime() {
        return loginTime;
    }

    private static LocalDateTime loginTime;

    /**
     * Method to authenticate user
     * Validates user credentials against database values
     *
     * @param username Username from textfield
     * @param password Password from textfield
     * @return boolean - true if successfully authenticate, false otherwise
     */
    public static Boolean authenticateSession(String username, String password) {
        try {

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sessionAuthSQL);
            ps.setString(1, username);
            ps.setString(2, password);

            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getString("User_Name").equals(username) && rs.getString("Password").equals(password)) {
                    sessionUser = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"));
                }
            }

            // Set login time
            loginTime = LocalDateTime.now(TimeZone.getDefault().toZoneId());

            if (sessionUser == null) {
                return false;
            }

            // Validate username and password again, since MySQL is not case-sensitive

            ObservableList<Appointment> allAppointmentsList = new AppointmentsDAOImpl().getAllAppts();
            ObservableList<Appointment> imminentAppointments = FXCollections.observableArrayList();

            for (Appointment a : allAppointmentsList) {
                boolean isImminent = TimeManager.evaluateTimeDelta(getLoginTime(), a.getStart().toLocalDateTime());
                if (isImminent) {
                    imminentAppointments.add(a);
                }
            }

            StringBuilder alertContentText;

            Alert imminentAppointmentAlert = new Alert(Alert.AlertType.INFORMATION);

            if (imminentAppointments.size() == 0) {
                alertContentText = new StringBuilder("No appointments within 15 minutes.");
                imminentAppointmentAlert.setContentText(alertContentText.toString());
            } else {
                alertContentText = new StringBuilder("Imminent Appointments: \n");
                for (Appointment a : imminentAppointments) {
                    String appointmentAlertInfo = a.getAppointmentID() + " " + a.getStartZonedDateTime() + "\n";
                    alertContentText.append(appointmentAlertInfo);
                }
                imminentAppointmentAlert.setContentText(alertContentText.toString());
            }

            imminentAppointmentAlert.showAndWait();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * Record login activity to .txt file
     *
     * @param username Username
     * @param attemptTime LocalDateTime of login attempt
     * @param success Whether login was successful or unsuccessful
     */
    public static void recordLoginActivity(String username, LocalDateTime attemptTime, boolean success) {
        try {
            FileWriter fileWriter = new FileWriter("loginActivity.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            String loginActivityString = "Time (UTC): " + attemptTime + " Username: " + username + " Successful Login: " + success;
            System.out.println(loginActivityString);
            printWriter.println(loginActivityString);
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static User getSessionUser() {
        return sessionUser;
    }

    public static Locale getSessionLocale() {
        return Locale.getDefault();
    }

}
