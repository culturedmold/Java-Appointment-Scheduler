package com.example.c195_software2.controller;

import com.example.c195_software2.DAOImpl.AppointmentsDAOImpl;
import com.example.c195_software2.DAOImpl.CountriesDAOImpl;
import com.example.c195_software2.DAOImpl.CustomersDAOImpl;
import com.example.c195_software2.DAOImpl.FirstLevelDivisionsDAOImpl;
import com.example.c195_software2.model.Appointment;
import com.example.c195_software2.model.Country;
import com.example.c195_software2.model.Customer;
import com.example.c195_software2.model.Division;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for customers-view.fxml
 */
public class CustomersViewController extends Controller implements Initializable {
    private CustomersDAOImpl customersDAOImpl;
    private static Customer selectedCustomer;

    // Columns for customers_table
    @FXML private TableView<Customer> customers_table;
    @FXML private TableColumn<Customer, Integer> cust_ID_col;
    @FXML private TableColumn<Customer, String> cust_name_col;
    @FXML private TableColumn<Customer, String> cust_address_col;
    @FXML private TableColumn<Customer, String> cust_postal_code_col;
    @FXML private TableColumn<Customer, String> cust_division_col;
    @FXML private TableColumn<Customer, String> cust_phone_col;

    // Columns for cust_schedule_table
    @FXML private TableView<Appointment> cust_schedule_table;
    @FXML private TableColumn<Appointment, Integer> appt_ID_col;
    @FXML private TableColumn<Appointment, String> appt_title_col;
    @FXML private TableColumn<Appointment, String> appt_desc_col;
    @FXML private TableColumn<Appointment, String> appt_loc_col;
    @FXML private TableColumn<Appointment, String> appt_type_col;
    @FXML private TableColumn<Appointment, String> appt_start_col;
    @FXML private TableColumn<Appointment, String> appt_end_col;
    @FXML private TableColumn<Appointment, Integer> appt_contact_ID_col;
    @FXML private TableColumn<Appointment, Integer> appt_user_ID_col;

    @FXML private Label selected_customer_label;
    @FXML private DatePicker cust_date_filter_picker;

    // Radio buttons
    @FXML private ToggleGroup schedule_filter_toggle_group;
    @FXML private RadioButton month_radio_button;
    @FXML private RadioButton week_radio_button;
    @FXML private RadioButton month_type_radio_button;
    @FXML private RadioButton all_radio_button;


    /**
     * Open appointments-view.fxml
     * @param event Button click
     */
    public void openAppointmentsView(ActionEvent event) {
        openNewView(event, "appointments-view.fxml", "Appointments");
    }

    /**
     * Open contacts-view.fxml
     * @param event Button click
     */
    public void openContactsView(ActionEvent event) {
        openNewView(event, "contacts-view.fxml", "Contacts");
    }

    /**
     * Logout from application
     * User will be prompted to confirm to ensure no accidental logging out
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
     * Get value from datepicker and return as String
     * @return String if datepicker value is not null, null otherwise
     */
    public String getDatePickerString() {
        String selectedDateString = String.valueOf(cust_date_filter_picker.getValue());

        if (selectedDateString == "null") {
            System.out.println("No value selected");
            return null;
        }

        System.out.println(selectedDateString);
        return selectedDateString;
    }


    /**
     * Get and return selectedCustomer object
     * @return selectedCustomer object
     */
    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * Open modify-customer-view.fxml
     * @param event Button click
     */
    public void openModifyCustomerView(ActionEvent event) {
        selectedCustomer = customers_table.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            openNewView(event, "modify-customer-view.fxml", "Modify Customer");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selectd. Please select a customer from the table and try again.");
            alert.showAndWait();
        }
    }

    /**
     * Open create-customer-view.fxml
     * @param event Button click
     */
    public void openCreateCustomerView(ActionEvent event) {
        openNewView(event, "create-customer-view.fxml", "Create Customer");
    }

    /**
     * Filter schedules by selected customer
     * If no customer is selected (selectedCustomer == null), then an alert is displayed telling the user to select a customer from the table
     */
    public void viewCustomerSchedule() {
        selectedCustomer = customers_table.getSelectionModel().getSelectedItem();
        selected_customer_label.setText("Schedule for: " + selectedCustomer.getName());

        if (selectedCustomer != null) {
            cust_schedule_table.setItems(customersDAOImpl.getAppointmentsByCustomer(selectedCustomer.getID()));
            all_radio_button.setSelected(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected. Please select a customer from the table, and try again.");
            alert.showAndWait();
        }
    }

    /**
     * Filter schedules by selected customer for a chosen month (from datepicker)
     * If no customer is selected (selectedCustomer == null), then an alert is displayed telling the user to select a customer from the table
     * If no date is selected in datepicker, an alert is displayed telling the user to select a date
     */
    public void viewScheduleByMonth() {
        selectedCustomer = customers_table.getSelectionModel().getSelectedItem();

        String dateString = getDatePickerString();

        if (selectedCustomer == null) {
            all_radio_button.setSelected(true);
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected. Please select a customer and try again.");
            alert.showAndWait();
            cust_date_filter_picker.setValue(null);

            return;
        }

        if (dateString == null) {
            all_radio_button.setSelected(true);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a date.");
            alert.showAndWait();
            all_radio_button.setSelected(true);
            viewCustomerSchedule();

            return;
        }

        ObservableList<Appointment> scheduleByMonthList = customersDAOImpl.getCustomerScheduleMonth(dateString, selectedCustomer.getID());
        cust_schedule_table.setItems(scheduleByMonthList);
    }

    /**
     * Filter schedules by selected customer for a chosen week (from datepicker)
     * If no customer is selected (selectedCustomer == null), then an alert is displayed telling the user to select a customer from the table
     * If no date is selected in datepicker, an alert is displayed telling the user to select a date
     */
    public void viewScheduleByWeek() {
        String dateString = getDatePickerString();

        if (selectedCustomer == null) {
            all_radio_button.setSelected(true);
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected. Please select a customer and try again.");
            alert.showAndWait();
            cust_date_filter_picker.setValue(null);

            return;
        }

        if (dateString == null) {
            all_radio_button.setSelected(true);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a date.");
            alert.showAndWait();
            all_radio_button.setSelected(true);
            viewCustomerSchedule();

            return;
        }

        ObservableList<Appointment> scheduleByWeekList = customersDAOImpl.getCustomerScheduleWeek(dateString, selectedCustomer.getID());
        cust_schedule_table.setItems(scheduleByWeekList);
    }


    /**
     * Opens month-type-report-view.fxml
     * @param event Button click
     */
    public void openMonthTypeFilterView(ActionEvent event) {
        openNewView(event, "month-type-report-view.fxml", "Customer Appointments - Month and Type");
    }


    /**
     * Delete selected customer
     * If no customer is selected, user will be alerted to select a customer from the table
     * User will be prompted to confirm their decision to avoid accidental deletions
     */
    public void deleteCustomer() {
        Alert alert;

        selectedCustomer = customers_table.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            alert = new Alert(Alert.AlertType.ERROR, "No customer selected. Please select a customer and try again.");
            alert.showAndWait();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer? This action cannot be undone.");
        Optional<ButtonType> confirmDelete = alert.showAndWait();

        if (confirmDelete.isPresent() && confirmDelete.get() == ButtonType.OK) {
            try {

                ObservableList<Appointment> currentCustomerAppointments = customersDAOImpl.getAppointmentsByCustomer(selectedCustomer.getID());

                if (currentCustomerAppointments.size() == 0) {
                    customersDAOImpl.deleteCustomer(selectedCustomer.getID());
                } else {
                    AppointmentsDAOImpl appointmentsDAOImpl = new AppointmentsDAOImpl();

                    for (Appointment a : currentCustomerAppointments) {
                        appointmentsDAOImpl.deleteAppt(a.getAppointmentID());
                    }

                    customersDAOImpl.deleteCustomer(selectedCustomer.getID());
                }

                alert = new Alert(Alert.AlertType.INFORMATION, "Customer " + selectedCustomer.getID() + " was successfully deleted.");
                alert.showAndWait();

                // UPDATE CUSTOMERS TABLE
                customers_table.setItems(customersDAOImpl.getAllCustomers());

            } catch (Exception e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR, "There was a problem trying to delete customer " + selectedCustomer.getID() + ". Please try again.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Initialize the view/controller
     * Table values are set using data returned from databse when CustomersDAOImpl.getAllCustomers() is called
     *
     * Lambda Expressions used to set cell values in table. This was done using Lambda expressions instead of using new PropertyValueFactory. Using Lambda allows us to have proper compile time checking to see if the method exists and returns the appropriate type.
     *
     * @param url Parameter to initialize view
     * @param resourceBundle Parameter to initialize view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customersDAOImpl = new CustomersDAOImpl();
        customers_table.setItems(customersDAOImpl.getAllCustomers());

        // HashMap of first-level divisions used in displaying/altering customer data
        HashMap<Integer, Division> divisionHashMap = new FirstLevelDivisionsDAOImpl().getAllDivisions();

        // HashMap of countries used in displaying/altering customer data
        HashMap<Integer, Country> countryHashMap = new CountriesDAOImpl().getAllCountries();
        System.out.println(divisionHashMap.keySet());

        // Set customer table column values
        cust_ID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
        cust_name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        cust_address_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        cust_postal_code_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostalCode()));
        cust_division_col.setCellValueFactory(cellData -> new SimpleStringProperty(divisionHashMap.get(cellData.getValue().getDivisionID()).getDivisionName() + ", " + countryHashMap.get(divisionHashMap.get(cellData.getValue().getDivisionID()).getCountryID()).getName()));
        cust_phone_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));

        // Set appointments (schedule) table column values
        appt_ID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentID()).asObject());
        appt_title_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        appt_desc_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        appt_loc_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        appt_type_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        appt_start_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartFormattedDisplay()));
        appt_end_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndFormattedDisplay()));
        appt_contact_ID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getContact()).asObject());
        appt_user_ID_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserID()).asObject());

        // Radio buttons
        month_radio_button.setToggleGroup(schedule_filter_toggle_group);
        week_radio_button.setToggleGroup(schedule_filter_toggle_group);
        all_radio_button.setToggleGroup(schedule_filter_toggle_group);
        all_radio_button.setSelected(true);
    }
}
