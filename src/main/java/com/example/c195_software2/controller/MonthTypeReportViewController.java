package com.example.c195_software2.controller;

import com.example.c195_software2.DAOImpl.CustomersDAOImpl;
import com.example.c195_software2.model.AppointmentTypeCount;
import com.example.c195_software2.model.Customer;
import com.example.c195_software2.model.AppointmentMonthCount;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for month-type-report-view.fxml
 */
public class MonthTypeReportViewController extends Controller implements Initializable {
    CustomersDAOImpl customersDAOImpl = new CustomersDAOImpl();
    @FXML
    private ComboBox<Customer> customer_combobox;
    @FXML
    private TableView<AppointmentMonthCount> month_table;
    @FXML
    private TableView<AppointmentTypeCount> type_table;
    @FXML
    private TableColumn<AppointmentMonthCount, String> month_col;
    @FXML
    private TableColumn<AppointmentMonthCount, Integer> count_month_col;
    @FXML
    private TableColumn<AppointmentTypeCount, String> type_col;
    @FXML
    private TableColumn<AppointmentTypeCount, Integer> count_type_col;
    @FXML
    private RadioButton all_radio_button;
    @FXML
    private RadioButton current_year_radio_button;
    @FXML
    private ToggleGroup month_type_toggle_group;

    /**
     * Navigate user to customers-view.fxml
     * @param event Button click
     */
    public void openCustomersView(ActionEvent event) {
        openNewView(event, "customers-view.fxml", "Customers");
    }

    /**
     * Method to get selected customer object from combobox and return it
     * @return Customer object - selectedCustomer
     */
    public Customer getSelectedCustomer() {
        Customer selectedCustomer = customer_combobox.getSelectionModel().getSelectedItem();

        return selectedCustomer;
    }

    /**
     * Generate the report and fill the cells in table with data
     * Displays error message if no customer is selected
     */
    public void generateMonthTypeReports() {
        Customer selectedCustomer = getSelectedCustomer();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected. Please select a customer and try again.");
            alert.showAndWait();
            all_radio_button.setSelected(true);
            return;
        }

        Toggle selectedToggle = month_type_toggle_group.getSelectedToggle();

        if (selectedToggle == current_year_radio_button) {

            month_table.setItems(customersDAOImpl.getAppointmentsByMonthCurrentYearFilter(selectedCustomer.getID()));
            type_table.setItems(customersDAOImpl.getAppointmentsByTypeCurrentYearFilter(selectedCustomer.getID()));
        }
        if (selectedToggle == all_radio_button) {

            month_table.setItems(customersDAOImpl.getAllAppointmentsByMonthFilter(selectedCustomer.getID()));
            type_table.setItems(customersDAOImpl.getAllAppointmentsByTypeFilter(selectedCustomer.getID()));
        }
    }


    /**
     * Initialize the view/controller
     * Lambda expressions used to populate table cell data
     *
     * @param url Parameter to initialize the view
     * @param resourceBundle Parameter to initialize the view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer_combobox.setItems(customersDAOImpl.getAllCustomers());

        month_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMonthName()));
        count_month_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());

        type_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        count_type_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());

        all_radio_button.setToggleGroup(month_type_toggle_group);
        current_year_radio_button.setToggleGroup(month_type_toggle_group);

        all_radio_button.setSelected(true);
    }
}
