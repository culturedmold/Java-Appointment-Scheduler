package com.example.c195_software2.DAO;

import com.example.c195_software2.model.Appointment;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 * AppointmentsDAO interface
 */
public interface AppointmentsDAO {
    /**
     * Get all appointments from database
     * @return ObservableList of Appointment objects
     */
    ObservableList<Appointment> getAllAppts();

    /**
     * Get all appointments for a specified month
     * @param date Date formatted as a string
     * @return ObservableList of Appointment objects
     */
    ObservableList<Appointment> getAllAppointmentsByMonth(String date);

    /**
     * Get all appointments for a specified week
     * @param date Date formatted as a string
     * @return ObservableList of Appointment objects
     */
    ObservableList<Appointment> getAllAppointmentsByWeek(String date);


    /**
     * Delete an appointment
     * @param apptID Appointment ID as int
     * @return boolean - true if deleted successfully, false otherwise
     */
    boolean deleteAppt(int apptID);

    /**
     * Delete an appointment by customer
     * @param custID Customer ID as int
     * @return boolean - true if deleted successfully, false otherwise
     */
    boolean deleteApptByCust(int custID);


    /**
     * Insert appointment into database
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type Appointment type
     * @param start Appointment start
     * @param end Appointment end
     * @param custID Customer ID
     * @param userID User ID
     * @param contactID Contact ID
     * @return boolean - true if appointment created (inserted into database) successfully, false otherwise
     */
    boolean createAppt(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, String custID, String userID, String contactID);

    /**
     * Updates an existing appointment in the database
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type Appointment type
     * @param start Appointment start
     * @param end Appointment end
     * @param custID Customer ID
     * @param userID User ID
     * @param contactID Contact ID
     * @param appointmentID Appointment ID
     * @return boolean - true if updated, false otherwise
     */
    boolean updateAppt(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, String custID, String userID, String contactID, String appointmentID);




    }
