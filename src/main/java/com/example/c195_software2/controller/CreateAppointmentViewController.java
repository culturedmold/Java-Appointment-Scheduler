package com.example.c195_software2.controller;

import com.example.c195_software2.DAOImpl.AppointmentsDAOImpl;
import com.example.c195_software2.DAOImpl.ContactsDAOImpl;
import com.example.c195_software2.DAOImpl.CustomersDAOImpl;
import com.example.c195_software2.util.Session;
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
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for create-appointment-view.fxml
 */
public class CreateAppointmentViewController extends Controller implements Initializable {
    @FXML
    private TextField create_appt_title_field;
    @FXML
    private TextField create_appt_description_field;
    @FXML
    private TextField create_appt_location_field;
    @FXML
    private TextField create_appt_type_field;
    @FXML
    private TextField create_appt_start_time_field;
    @FXML
    private DatePicker create_appt_start_date_picker;
    @FXML
    private TextField create_appt_end_time_field;
    @FXML
    private DatePicker create_appt_end_date_picker;
    @FXML
    private ComboBox<Customer> customer_combobox;
    @FXML
    private ComboBox<Contact> contact_combobox;
    @FXML
    private TextField create_appt_user_ID_field;
    @FXML
    private ComboBox<String> start_am_pm_combobox;
    @FXML
    private ComboBox<String> end_am_pm_combobox;

    /**
     * Check empty fields in create appointment form
     * @param title the appointment title
     * @param description appointment description
     * @param type appointment type
     * @param location appointment location
     * @param userID user ID
     * @param custID customer ID
     * @param contactID contact ID
     * @param startDate appointment start date
     * @param startTime appointment start time
     * @param endDate appointment end date
     * @param endTime appointment end time
     * @param startAMPM AM/PM value for start time
     * @param endAMPM AM/PM value for end time
     * @return returns boolean - true if empty fields are present, otherwise false
     */
    public boolean emptyFieldsCheck(String title, String description, String type, String location, String userID, Integer custID, Integer contactID, LocalDate startDate, String startTime, LocalDate endDate, String endTime, String startAMPM, String endAMPM) {
        if ( title == null
                || description == null
                || location == null
                || userID == null
                || type == null
                || custID == null
                || contactID== null
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
     * Get Integer customer ID from customer_combobox
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
     * Get Integer contact ID from customer_combobox
     * @return contact ID as Integer
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
     * Check whether there are conflicting appointments for the customer (determined by customer ID)
     * @param custID customer ID
     * @param startDateTime appointment start time as LocalDateTime object
     * @param endDateTime appointment end time as LocalDateTime object
     * @return boolean - true if conflicting appointments exist, false otherwise
     */
    public boolean checkConflictingAppointments(int custID, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        CustomersDAOImpl customersDAOImpl = new CustomersDAOImpl();
        ObservableList<Appointment> currentCustomerAppointments = customersDAOImpl.getAppointmentsByCustomer(custID);

        System.out.println("Current Appointments: " + currentCustomerAppointments.size());

        for (Appointment a : currentCustomerAppointments) {
                if (startDateTime.isAfter(a.getStartZonedDateTime().toLocalDateTime()) && startDateTime.isBefore(a.getEndZonedDateTime().toLocalDateTime())) {
                    return true;
                }
                if (endDateTime.isAfter(a.getStartZonedDateTime().toLocalDateTime()) && endDateTime.isBefore(a.getEndZonedDateTime().toLocalDateTime())) {
                    return true;
                }
                if (startDateTime.isEqual(a.getStartZonedDateTime().toLocalDateTime()) || endDateTime.isEqual(a.getEndZonedDateTime().toLocalDateTime())) {
                    return true;
                }
        }

        return false;
    }

    /**
     * Create new appointment
     * Will validate parameters using helper methods and return appropriate error messages if issues are encountered
     * @param event Button click
     */
    public void createNewAppointment(ActionEvent event) {

        String title = create_appt_title_field.getText();
        String description = create_appt_description_field.getText();
        String location = create_appt_location_field.getText();
        String type = create_appt_type_field.getText();
        String startTime = create_appt_start_time_field.getText();
        String endTime = create_appt_end_time_field.getText();
        LocalDate startDate = create_appt_start_date_picker.getValue();
        LocalDate endDate = create_appt_end_date_picker.getValue();
        Integer customerID = getSelectedCustomerID();
        String userID = create_appt_user_ID_field.getText();
        Integer contactID = getSelectedContactID();
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

        LocalDateTime startDateTime = TimeManager.combineLocalDateTime(startDate, startTime, startAMPM);
        LocalDateTime endDateTime = TimeManager.combineLocalDateTime(endDate, endTime, endAMPM);

        if (endDateTime.isBefore(startDateTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "End date/time cannot be before start date/time.");
            alert.showAndWait();
            return;
        }

        boolean validateBusinessEnd = TimeManager.validateBusinessHours(endDateTime);
        boolean validateBusinessStart = TimeManager.validateBusinessHours(startDateTime);

        if (validateBusinessEnd == true || validateBusinessStart == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There was a problem with your start and/or end appointment date times. Appointment must be during business hours (8AM - 10PM, Monday - Friday.");
            alert.showAndWait();
            return;
        }


        // Check appointments by custID to determine if there are existing conflicting appointments
        boolean conflictingAppointment = checkConflictingAppointments(customerID, startDateTime, endDateTime);
        if (conflictingAppointment) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer " + customer_combobox.getSelectionModel().getSelectedItem().getID() + " is already scheduled during this time. Please check start and end date/time, and try again.");
            alert.showAndWait();
            return;
        }

        // PERFORM UPDATE
        boolean performUpdate = new AppointmentsDAOImpl().createAppt(title, description, location, type, startDateTime, endDateTime, String.valueOf(customerID), userID, String.valueOf(contactID));
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
     * Cancel and return to appointments-view.fxml
     * User will be prompted to confirm to avoid accidental cancellation
     * @param event Button click
     */
    public void createApptCancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        Optional<ButtonType> confirmDelete = alert.showAndWait();

        if (confirmDelete.isPresent() && confirmDelete.get() == ButtonType.OK) {
            openNewView(event, "appointments-view.fxml", "Appointments");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_appt_user_ID_field.setText(String.valueOf(Session.getSessionUser().getUserID()));

        customer_combobox.setItems(new CustomersDAOImpl().getAllCustomers());
        contact_combobox.setItems(new ContactsDAOImpl().getAllContacts());

        ObservableList<String> AMPMList = FXCollections.observableArrayList();
        AMPMList.add("AM");
        AMPMList.add("PM");

        end_am_pm_combobox.setItems(AMPMList);
        start_am_pm_combobox.setItems(AMPMList);
    }
}
