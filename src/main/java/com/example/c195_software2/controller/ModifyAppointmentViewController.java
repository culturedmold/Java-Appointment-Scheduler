package com.example.c195_software2.controller;

import com.example.c195_software2.DAOImpl.AppointmentsDAOImpl;
import com.example.c195_software2.DAOImpl.ContactsDAOImpl;
import com.example.c195_software2.DAOImpl.CustomersDAOImpl;
import com.example.c195_software2.model.Appointment;
import com.example.c195_software2.model.Contact;
import com.example.c195_software2.model.Customer;
import com.example.c195_software2.util.TimeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for modify-appointment-view.fxml
 */
public class ModifyAppointmentViewController extends Controller implements Initializable {
    private Appointment selectedAppointment = null;
    @FXML
    private TextField appointment_ID_field;
    @FXML
    private TextField appointment_title_field;
    @FXML
    private TextField appointment_description_field;
    @FXML
    private TextField appointment_location_field;
    @FXML
    private TextField appointment_type_field;
    @FXML
    private DatePicker modify_appt_start_date_picker;
    @FXML
    private TextField appt_start_time_field;
    @FXML
    private DatePicker modify_appt_end_date_picker;
    @FXML
    private TextField appt_end_time_field;
    @FXML
    private ComboBox<String> start_am_pm_combobox;
    @FXML
    private ComboBox<String> end_am_pm_combobox;
    @FXML
    private TextField appointment_user_ID_field;
    @FXML
    private ComboBox<Customer> customer_combobox;
    @FXML
    private ComboBox<Contact> contact_combobox;


    /**
     * Check the form fields for empty fields
     * @param title Appointment title
     * @param description Appointment description
     * @param type Appointment type
     * @param location Appointment location
     * @param userID User ID
     * @param custID Customer ID
     * @param contactID Contact ID
     * @param startDate Appointment start date
     * @param startTime Appointment start time
     * @param endDate Appointment end date
     * @param endTime Appointment end time
     * @param startAMPM Appointment start AM/PM
     * @param endAMPM Appointment end AM/PM
     * @return boolean - true if empty fields exist, false otherwise
     */
    public boolean emptyFieldsCheck(String title, String description, String type, String location, String userID, Integer custID, Integer contactID, LocalDate startDate, String startTime, LocalDate endDate, String endTime, String startAMPM, String endAMPM) {
        if (
                title == null
                || description == null
                || type == null
                || location == null
                || userID == null
                || custID == null
                || contactID == null
                || startDate == null
                || startTime == null
                || endDate == null
                || endTime == null
                || startAMPM == null
                || endAMPM == null
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty Fields Not Allowed. Please ensure all fields are filled out and try again.");
            alert.showAndWait();
            return true;
        }
        return false;
    }


    /**
     * Method to check if conflicting appointments for the customer (by Customer ID) exist
     * @param custID Customer ID as int
     * @param appointmentID Appointment ID as int
     * @param startZDTUTC Appointment start time as LocalDateTime
     * @param endZDTUTC Appointment end time as LocalDateTime
     * @return boolean - true if conflicts exist, false otherwise
     */
    public boolean checkConflictingAppointments(int custID, int appointmentID, LocalDateTime startZDTUTC, LocalDateTime endZDTUTC) {
        CustomersDAOImpl customersDAOImpl = new CustomersDAOImpl();
        ObservableList<Appointment> currentCustomerAppointments = customersDAOImpl.getAppointmentsByCustomer(custID);

        System.out.println("Current Appointments: " + currentCustomerAppointments.size());

        for (Appointment a : currentCustomerAppointments) {
            if (a.getAppointmentID() != appointmentID) {
                if (startZDTUTC.isAfter(a.getStartZonedDateTime().toLocalDateTime()) && startZDTUTC.isBefore(a.getEndZonedDateTime().toLocalDateTime())) {
                    return true;
                }
                if (endZDTUTC.isAfter(a.getStartZonedDateTime().toLocalDateTime()) && endZDTUTC.isBefore(a.getEndZonedDateTime().toLocalDateTime())) {
                    return true;
                }
                if (startZDTUTC.isEqual(a.getStartZonedDateTime().toLocalDateTime()) || endZDTUTC.isEqual(a.getEndZonedDateTime().toLocalDateTime())) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * Get Customer object from customer_combobox and return the customer ID
     * @return customer ID as Integer
     */
    public Integer getSelectedCustomerID() {
        try {
            Customer selectedCustomer = customer_combobox.getSelectionModel().getSelectedItem();
            return selectedCustomer.getID();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get Contact object from contact_combobox and return the contact ID
     * @return Contact ID as Integer
     */
    public Integer getSelectedContactID() {
        try {
            Contact selectedContact = contact_combobox.getSelectionModel().getSelectedItem();
            return selectedContact.getID();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Save the modified/updated appointment
     * Triggered by save button
     * Displays a success or error alert
     * If successfully, customer is navigated back to appointment-view.fxml
     * @param event Button click
     */
    public void saveUpdateAppointment(ActionEvent event) {

        String title = appointment_title_field.getText();
        String description = appointment_description_field.getText();
        String type = appointment_type_field.getText();
        String location = appointment_location_field.getText();
        String userID = appointment_user_ID_field.getText();
        Integer customerID = getSelectedCustomerID();
        Integer contactID = getSelectedContactID();
        LocalDate startDate = modify_appt_start_date_picker.getValue();
        String startTime = appt_start_time_field.getText();
        LocalDate endDate = modify_appt_end_date_picker.getValue();
        String endTime = appt_end_time_field.getText();
        String startAMPM = start_am_pm_combobox.getValue();
        String endAMPM = end_am_pm_combobox.getValue();

        // Check empty fields and alert if necessary
        boolean emptyFields = emptyFieldsCheck(title, description, type, location, userID, customerID, contactID, startDate, startTime, endDate, endTime, startAMPM, endAMPM);
        if (emptyFields) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty Fields Not Allowed");
            alert.setContentText("Please ensure all fields are filled out and try again.");
            alert.showAndWait();
            return;
        }

        LocalDateTime startLDT = TimeManager.combineLocalDateTime(startDate, startTime, startAMPM);
        LocalDateTime endLDT = TimeManager.combineLocalDateTime(endDate, endTime, endAMPM);

        if (endLDT.isBefore(startLDT)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "End date/time cannot be before start date/time.");
            alert.showAndWait();
            return;
        }

        boolean validateBusinessHoursStart = TimeManager.validateBusinessHours(startLDT);
        boolean validateBusinessHoursEnd = TimeManager.validateBusinessHours(endLDT);

        System.out.println("start bad: " + validateBusinessHoursStart + " end bad: " + validateBusinessHoursEnd);

        if (validateBusinessHoursStart == true || validateBusinessHoursEnd == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There was a problem with your start and/or end appointment date times. Appointment must be during business hours (8AM - 10PM, Monday - Friday.");
            alert.showAndWait();
            return;
        }

        // Check appointments by custID to determine if there are existing conflicting appointments
        boolean conflictingAppointment = checkConflictingAppointments(customerID, selectedAppointment.getAppointmentID(), startLDT, startLDT);
        if (conflictingAppointment) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer " + customer_combobox.getSelectionModel().getSelectedItem().getID() + " is already scheduled during this time. Please check start and end date/time, and try again.");
            alert.showAndWait();
            return;
        }

        // PERFORM UPDATE
        boolean performUpdate = new AppointmentsDAOImpl().updateAppt(title, description, location, type, startLDT, endLDT, String.valueOf(customerID), userID, String.valueOf(contactID), String.valueOf(selectedAppointment.getAppointmentID()));
        if (performUpdate) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successful!");
            alert.showAndWait();
            openNewView(event, "appointments-view.fxml", "Appointments");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error performing the update. Please try again.");
            alert.showAndWait();
        }
    }


    /**
     * Cancel the modification and return to appointments-view.fxml
     * User is prompted to confirm their choice to avoid accidental cancellations
     * @param event Button click
     */
    public void cancelModifyAppointment(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel? Your changes will not be saved.");
        Optional<ButtonType> confirmCancel = alert.showAndWait();

        if (confirmCancel.isPresent() && confirmCancel.get() == ButtonType.OK) {
            openNewView(event, "appointments-view.fxml", "Appointments");
        }
    }


    /**
     * Initialize the view and controller
     * selectedAppointment is gotten from AppointmentsViewController and fields are set to default values using existing values in selectedAppointment object
     * @param url Parameter to initialize the view
     * @param resourceBundle Parameter to initialize the view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = AppointmentsViewController.getSelectedAppt();

        ObservableList<String> amPMList = FXCollections.observableArrayList();
        amPMList.add("AM");
        amPMList.add("PM");
        start_am_pm_combobox.setItems(amPMList);
        end_am_pm_combobox.setItems(amPMList);

        // Load Fields
        appointment_ID_field.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        appointment_title_field.setText(selectedAppointment.getTitle());
        appointment_description_field.setText(selectedAppointment.getDescription());
        appointment_location_field.setText(selectedAppointment.getLocation());
        appointment_type_field.setText(selectedAppointment.getType());
        appointment_user_ID_field.setText(String.valueOf(selectedAppointment.getUserID()));

        // Date Pickers
        modify_appt_start_date_picker.setValue(selectedAppointment.getStartAsLocalDate());
        modify_appt_end_date_picker.setValue(selectedAppointment.getEndAsLocalDate());

        // Time Fields
        appt_start_time_field.setText(String.valueOf(selectedAppointment.getLocalTimeString(selectedAppointment.getStartLocalTime())));
        appt_end_time_field.setText(String.valueOf(selectedAppointment.getLocalTimeString(selectedAppointment.getEndLocalTime())));

        if (selectedAppointment.getStartZonedDateTime().isBefore(selectedAppointment.getStartZonedDateTime().withHour(12))) {
            start_am_pm_combobox.setValue("AM");
        } else {
            start_am_pm_combobox.setValue("PM");
        }

        if (selectedAppointment.getEndZonedDateTime().isAfter(selectedAppointment.getEndZonedDateTime().withHour(12))) {
            end_am_pm_combobox.setValue("PM");
        } else {
            end_am_pm_combobox.setValue("AM");
        }

        ObservableList<Customer> customersList = new CustomersDAOImpl().getAllCustomers();
        ObservableList<Contact> contactsList = new ContactsDAOImpl().getAllContacts();

        Customer initialCustomer = null;
        for (Customer c : customersList) {
            if (c.getID() == selectedAppointment.getCustomerID()) {
                initialCustomer = c;
            }
        }

        Contact initialContact = null;
        for (Contact c : contactsList) {
            if (c.getID().equals(selectedAppointment.getContact())) {
                initialContact = c;
            }
        }

        // Load Customer and Contact ComboBox
        customer_combobox.setItems(customersList);
        customer_combobox.setValue(initialCustomer);
        contact_combobox.setItems(contactsList);
        contact_combobox.setValue(initialContact);

    }
}
