package com.example.c195_software2.DAO;

import com.example.c195_software2.model.Appointment;
import com.example.c195_software2.model.AppointmentTypeCount;
import com.example.c195_software2.model.Customer;
import com.example.c195_software2.model.AppointmentMonthCount;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 * CustomersDAO interface
 */
public interface CustomersDAO {
    /**
     * Filter appointments by month
     * @param custID Customer ID as int
     * @return ObservableList of AppointmentMonthCount objects
     */
    ObservableList<AppointmentMonthCount> getAllAppointmentsByMonthFilter(int custID);

    /**
     * Filter appointments by type
     * @param custID Customer ID as int
     * @return ObservableList of AppointmentTypeCount objects
     */
    ObservableList<AppointmentTypeCount> getAllAppointmentsByTypeFilter(int custID);

    /**
     * Get all customers from database
     * @return ObservableList of Customer objects
     */
    ObservableList<Customer> getAllCustomers();

    /**
     * Method to create a customer/insert new customer into database
     * @param name Customer name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone number
     * @param divisionID Division ID
     * @return boolean - true if customer successfully created (inserted into database), false otherwise
     */
    boolean createCustomer(String name, String address, String postalCode, String phone, Integer divisionID);

    /**
     * Method to get all appointments from database by customer ID
     * @param custID Customer ID as int
     * @return ObservableList of Appointment objects
     */
    ObservableList<Appointment> getAppointmentsByCustomer(int custID);

    /**
     * Method to get appointments by customer ID for a given month
     * @param date Date formatted as String
     * @param custID Customer ID
     * @return ObservableList of Appointment objects
     */
    ObservableList<Appointment> getCustomerScheduleMonth(String date, int custID);

    /**
     * Method to get appointments by customer ID for a given week
     * @param date Date formatted as String
     * @param custID Customer ID
     * @return ObservableList of Appointment objects
     */
    ObservableList<Appointment> getCustomerScheduleWeek(String date, int custID);

    /**
     * Filter appointments by month (current year) for a given customer
     * @param custID Customer ID as int
     * @return ObservableList of AppointmentMonthCount objects
     */
    ObservableList<AppointmentMonthCount> getAppointmentsByMonthCurrentYearFilter(int custID);

    /**
     * Filter appointments by type (current year) for a given customer
     * @param custID Customer ID as int
     * @return ObservableList of AppointmentTypeCount objects
     */
    ObservableList<AppointmentTypeCount> getAppointmentsByTypeCurrentYearFilter(int custID);

    /**
     * Update/modify existing customer in database
     * @param custID Customer ID as Integer
     * @param name Customer name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone number
     * @param divisionID Division ID
     * @return boolean - true if updated/modified successfully, false otherwise
     */
    boolean updateCustomer(Integer custID, String name, String address, String postalCode, String phone, Integer divisionID);

    /**
     * Delete customer from database
     * @param custID Customer ID as int
     * @return boolean - true if deleted successfully, false otherwise
     */
    boolean deleteCustomer(int custID);

}
