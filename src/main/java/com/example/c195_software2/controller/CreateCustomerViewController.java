package com.example.c195_software2.controller;

import com.example.c195_software2.DAOImpl.CountriesDAOImpl;
import com.example.c195_software2.DAOImpl.CustomersDAOImpl;
import com.example.c195_software2.DAOImpl.FirstLevelDivisionsDAOImpl;
import com.example.c195_software2.model.Country;
import com.example.c195_software2.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for create-customer-view.fxml
 */
public class CreateCustomerViewController extends Controller implements Initializable {

    @FXML
    private TextField name_field;
    @FXML
    private TextField address_field;
    @FXML
    private TextField postal_code_field;
    @FXML
    private TextField phone_field;
    @FXML
    private ComboBox<Country> country_combobox;
    @FXML
    private ComboBox<Division> division_combobox;

    /**
     * Sets division options once a country has been selected in the country_combobox
     * Triggered by action performed on country_combobox
     */
    public void setDivisionOptions() {
        Country selectedCountry = country_combobox.getSelectionModel().getSelectedItem();

        if (selectedCountry != null) {
            ObservableList<Division> divisionList = FXCollections.observableArrayList();
            HashMap<Integer, Division> divisionHashMap = new FirstLevelDivisionsDAOImpl().getDivisionByCountryID(selectedCountry.getCountryID());

            for (Integer divisionID : divisionHashMap.keySet()) {
                divisionList.add(divisionHashMap.get(divisionID));
            }

            division_combobox.setItems(divisionList);
        }

        division_combobox.setValue(null);
    }

    /**
     * Displays an alert if user tries to access the division_combobox prior to setting a country
     * While division_combobox will not be populated with any data until customer_combobox is set, this helps prevent user confusion and gives additional context
     */
    public void firstLevelDivisionComboBoxAlert() {
//        System.out.println(country_combobox.getSelectionModel().getSelectedItem());
        if (country_combobox.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a country and try again.");
            alert.showAndWait();
        }
    }

    /**
     * Check for empty fields in the create customer form
     * @return boolean - true if empty fields exist, false otherwise
     */
    public boolean checkEmptyFields() {
        if (
                name_field.getText() == null
                || address_field.getText() == null
                || postal_code_field.getText() == null
                || phone_field.getText() == null
                || country_combobox.getSelectionModel().getSelectedItem() == null
                || division_combobox.getSelectionModel().getSelectedItem() == null
        ) {
            return true;
        }
        return false;
    }

    /**
     * Create new customer method
     * Performs checks using helper methods to ensure fields are accurate and appropriate and alerts user if changes are needed
     * @param event Button click
     */
    public void createNewCustomer(ActionEvent event) {
        boolean emptyFields = checkEmptyFields();

        Alert alert;

        if (emptyFields) {
            alert = new Alert(Alert.AlertType.ERROR, "Empty fields not allowed. Please ensure all fields are filled out and try again.");
            alert.showAndWait();
            return;
        }

        CustomersDAOImpl customersDAOImpl = new CustomersDAOImpl();

        String name = name_field.getText();
        String address = address_field.getText();
        String postalCode = postal_code_field.getText();
        String phone = phone_field.getText();
        Integer divisionID = division_combobox.getSelectionModel().getSelectedItem().getDivisionID();

        boolean createNewCustomer = customersDAOImpl.createCustomer(name, address, postalCode, phone, divisionID);

        if (createNewCustomer) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer created successfully!");
            alert.showAndWait();

            openNewView(event, "customers-view.fxml", "Customers");
        }

    }

    /**
     * Cancel and return to appointments-view.fxml
     * Alert prompts customer to confirm they want to cancel to avoid accidental cancellation
     * @param event Button click
     */
    public void cancelCreateCustomer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel? Your changes will not be saved.");
        Optional<ButtonType> confirmCancel = alert.showAndWait();

        if (confirmCancel.isPresent() && confirmCancel.get() == ButtonType.OK) {
            openNewView(event, "appointments-view.fxml", "Appointments");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<Integer, Country> countryHashMap = new CountriesDAOImpl().getAllCountries();
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        for (Integer countryID : countryHashMap.keySet()) {
            countryList.add(countryHashMap.get(countryID));
        }

        country_combobox.setItems(countryList);
    }
}
