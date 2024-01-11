package com.example.c195_software2.controller;

import com.example.c195_software2.DAOImpl.ContactsDAOImpl;
import com.example.c195_software2.model.Appointment;
import com.example.c195_software2.model.Contact;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controller for contacts-view.fxml
 */
public class ContactsViewController extends Controller implements Initializable {
    ContactsDAOImpl contactsDAOImpl;

    // Tables
    @FXML
    private TableView<Contact> contacts_table;
    @FXML
    private TableView<Appointment> contact_schedule_table;

    // Table columns
    // Contacts Table Columns
    @FXML
    private TableColumn<Contact, Integer> contact_ID_col;
    @FXML
    private TableColumn<Contact, String> contact_name_col;
    @FXML
    private TableColumn<Contact, String> contact_email_col;

    // Contact Schedule Table Columns
    @FXML
    private TableColumn<Appointment, Integer> appt_ID_col;
    @FXML
    private TableColumn<Appointment, String> appt_title_col;
    @FXML
    private TableColumn<Appointment, String> appt_description_col;
    @FXML
    private TableColumn<Appointment, String> appt_location_col;
    @FXML
    private TableColumn<Appointment, String> appt_type_col;
    @FXML
    private TableColumn<Appointment, String> appt_start_col;
    @FXML
    private TableColumn<Appointment, String> appt_end_col;
    @FXML
    private TableColumn<Appointment, Integer> appt_custID_col;
    @FXML
    private TableColumn<Appointment, Integer> appt_userID_col;

    @FXML
    private Label selected_contact_label;
    @FXML
    private Label user_timezone_label;

    /**
     * Open appointments-view.fxml
     * @param event Button click
     */
    public void openAppointmentsView(ActionEvent event) {
        openNewView(event, "appointments-view.fxml", "Appointments");
    }

    /**
     * Open customers-view.fxml
     * @param event Button click
     */
    public void openCustomersView(ActionEvent event) {
        openNewView(event, "customers-view.fxml", "Customers");
    }

    /**
     * Logout of application
     * User will be prompted to confirm to prevent accidental logout
     * @param event Button click
     */
    public void logout(ActionEvent event) {
        Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?");
        Optional<ButtonType> confirm = logoutAlert.showAndWait();

        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            openNewView(event, "login-view.fxml", "Login");
        }
    }


    /**
     * Set selectedContact variable as object from contacts_table and return it
     * @return selectedContact object
     */
    public Contact getSelectedContact() {
        Contact selectedContact = contacts_table.getSelectionModel().getSelectedItem();
        return selectedContact;
    }

    /**
     * Display the schedule for selectedContact in the table
     * If selectedContact is null, user will see an alert prompting them to select a contact from the table before proceeding
     */
    public void getContactSchedule() {
        Contact selectedContact = getSelectedContact();

        if (selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No contact selected. Please select a contact from the table and try again.");
            alert.showAndWait();
            return;
        }

        selected_contact_label.setText("Schedule for: " + selectedContact.getID() + " - " + selectedContact.getName());

        // Set schedule table
        contact_schedule_table.setItems(contactsDAOImpl.getContactSchedule(selectedContact.getID()));

    }

    /**
     * Initialize the view/controller
     * Table is populated using data from database when ContactsDAOImpl.getAllContacts() is called
     *
     * Lambda Expressions used to set cell values in table. This was done using Lambda expressions instead of using new PropertyValueFactory. Using Lambda allows us to have proper compile time checking to see if the method exists and returns the appropriate type.
     *
     * @param url Parameter to initialize the view
     * @param resourceBundle Parameter to initialize the view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactsDAOImpl = new ContactsDAOImpl();

        user_timezone_label.setText(TimeZone.getDefault().toZoneId().toString());

        // Set contacts_table columns
        contacts_table.setItems(contactsDAOImpl.getAllContacts());
        contact_ID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
        contact_name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        contact_email_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        // Set schedule table columns
        // Lambda used to populate cells
        appt_ID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentID()).asObject());
        appt_description_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        appt_location_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        appt_title_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        appt_type_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        appt_start_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartFormattedDisplay()));
        appt_end_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndFormattedDisplay()));
        appt_custID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserID()).asObject());
        appt_userID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserID()).asObject());

    }
}
