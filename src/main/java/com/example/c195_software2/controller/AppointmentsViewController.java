package com.example.c195_software2.controller;

import com.example.c195_software2.DAOImpl.AppointmentsDAOImpl;
import com.example.c195_software2.util.Session;
import com.example.c195_software2.model.Appointment;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controller for appointments-view.fxml
 */
public class AppointmentsViewController extends Controller implements Initializable {
    private AppointmentsDAOImpl appointmentsDAOImpl;
    private static Appointment selectedAppt;

    @FXML
    private Label username_label;
    @FXML
    private Label timezone_label;

    // Table view
    @FXML
    private TableView<Appointment> appointments_table;

    // Table columns
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
    private RadioButton month_radio_button;
    @FXML
    private RadioButton week_radio_button;
    @FXML
    private RadioButton all_radio_button;
    @FXML
    private ToggleGroup appointments_filter_group;

    @FXML
    private DatePicker appt_datepicker;

    /**
     * Method to logout of application
     * Runs when user clicks "logout" button
     * @param event Button click on logout button
     */
    // Logout
    public void logout(ActionEvent event) {
        Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?");
        Optional<ButtonType> confirm = logoutAlert.showAndWait();

        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            openNewView(event, "login-view.fxml", "Login");
        }
    }

    /**
     * Method to open customers-view.fxml
     * Runs when user clicks associated button
     * @param event Button click
     */
    public void openCustomersView(ActionEvent event) {
        openNewView(event, "customers-view.fxml", "Customers");
    }

    /**
     * Method to open contacts-view.fxml
     * Runs when user clicks associated button
     * @param event Button click
     */
    public void openContactsView(ActionEvent event) {
        openNewView(event, "contacts-view.fxml", "Contacts");
    }


    /**
     * Get selectedAppt and return it
     * @return returns selectedAppt
     */
    public static Appointment getSelectedAppt() {
        return selectedAppt;
    }

    /**
     * Delete the selected appointment
     * Customer will be prompted to confirm their choice to avoid accidental deletions
     */
    public void deleteAppt() {
        Appointment selectedAppointment = appointments_table.getSelectionModel().getSelectedItem();

        // if no appointment is selected, show alert
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No appointment selected. Please select an appointment and try again.");
            alert.showAndWait();
            return;
        }

        Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment? The action cannot be undone.");
        Optional<ButtonType> confirmDelete = confirmDeleteAlert.showAndWait();

        if (confirmDelete.isPresent() && confirmDelete.get() == ButtonType.OK) {
            boolean deleted = appointmentsDAOImpl.deleteAppt(selectedAppointment.getAppointmentID());

            if (deleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment Deleted.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "There was a problem trying to process the request.");
                alert.showAndWait();
            }
        }

        appointments_table.setItems(appointmentsDAOImpl.getAllAppts());
    }


    /**
     * Get value from datepicker and return as string
     * @return appt_datepicker value as string
     */
    public String getDatePickerString() {
        String dateString = String.valueOf(appt_datepicker.getValue());

        if (dateString == "null") {
            return null;
        }

        return dateString;
    }


    /**
     * Open modify-appointment-view.fxml
     * If selectedAppt is null, customer will be notified to select an appointment before they can proceed
     * @param event Button click
     */
    public void openModifyAppointmentView(ActionEvent event) {
        selectedAppt = appointments_table.getSelectionModel().getSelectedItem();
        if (selectedAppt == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Appointment Selected");
            alert.setContentText("No appointment selected. Please select an appointment and try again.");
            alert.showAndWait();
        }
        openNewView(event, "modify-appointment-view.fxml", "Modify Appointment");
    }

    /**
     * Open create-appointment-view.fxml
     * @param event Button click
     */
    public void openCreateAppointmentView(ActionEvent event) {
        openNewView(event, "create-appointment-view.fxml", "Create Appointment");
    }


    /**
     * Method to filter appointment by month
     * The month is determined by the month that is selected in the datepicker
     * If no date is selected, user will be prompted to select a date before continuing
     */
    public void viewAppointmentsByMonth() {
        String dateString = getDatePickerString();

        if (dateString == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No date selected. Please select a date and try again.");
            alert.showAndWait();
            all_radio_button.setSelected(true);
            return;
        }

        ObservableList<Appointment> appointmentsThisMonthList = appointmentsDAOImpl.getAllAppointmentsByMonth(dateString);
        appointments_table.setItems(appointmentsThisMonthList);

        if (appointmentsThisMonthList.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No appointments for the selected month.");
            alert.showAndWait();
        }
    }

    /**
     * Method to filter appointment by week
     * The month is determined by the week that is selected in the datepicker
     * If no date is selected, user will be prompted to select a date before continuing
     */
    public void viewAppointmentsByWeek() {
        String dateString = getDatePickerString();

        if (dateString == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No date selected. Please select a date and try again.");
            alert.showAndWait();
            all_radio_button.setSelected(true);
            return;
        }

        ObservableList<Appointment> appointmentsThisWeekList = appointmentsDAOImpl.getAllAppointmentsByWeek(dateString);
        appointments_table.setItems(appointmentsThisWeekList);

        if (appointmentsThisWeekList.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No appointments for the selected week.");
            alert.showAndWait();
        }
    }

    /**
     * Method to filter appointments by all
     */
    public void viewAll() {
        appointments_table.setItems(appointmentsDAOImpl.getAllAppts());
    }

    /**
     Initialize the view
     * Tables are populated with data returned from database when initializer calls AppointmentsDAOImpl.getAllAppts()    *
     *
     * Lambda Expressions used to set cell values in table. This was done using Lambda expressions instead of using new PropertyValueFactory. Using Lambda allows us to have proper compile time checking to see if the method exists and returns the appropriate type.
     *
     * @param url Parameter to initialize the view
     * @param resourceBundle Parameter to initialize the view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsDAOImpl = new AppointmentsDAOImpl();

        ObservableList<Appointment> allAppointmentsList = appointmentsDAOImpl.getAllAppts();
        // populate table with observable list
        appointments_table.setItems(allAppointmentsList);

        // Set table columns
        // Lambda expressions
        appt_ID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentID()).asObject());
        appt_title_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        appt_description_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        appt_location_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        appt_type_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        appt_custID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerID()).asObject());

        // Start and end columns
        appt_start_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartFormattedDisplay()));
        appt_end_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndFormattedDisplay()));

        appt_userID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserID()).asObject());

        username_label.setText("User: " + Session.getSessionUser().getUsername());
        timezone_label.setText("Local Time Zone: " + TimeZone.getDefault().toZoneId().toString());

        month_radio_button.setToggleGroup(appointments_filter_group);
        week_radio_button.setToggleGroup(appointments_filter_group);
        all_radio_button.setToggleGroup(appointments_filter_group);
        all_radio_button.isSelected();

    }
}
