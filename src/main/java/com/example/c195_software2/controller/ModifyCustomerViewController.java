package com.example.c195_software2.controller;

import com.example.c195_software2.DAOImpl.CountriesDAOImpl;
import com.example.c195_software2.DAOImpl.CustomersDAOImpl;
import com.example.c195_software2.DAOImpl.FirstLevelDivisionsDAOImpl;
import com.example.c195_software2.model.Country;
import com.example.c195_software2.model.Customer;
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
 * Controller for modify-customer-view.fxml
 */
public class ModifyCustomerViewController extends Controller implements Initializable {
    Customer selectedCustomer;

    @FXML
    private TextField customer_ID_field;
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
     * Method to set division options for division_combobox
     * Triggered by action on country_combobox
     *
     * Lambda expression - populate the divisionList with the divisions from the divisionHashMap containing the divisions associated with the selected country
     */
    public void setDivisionOptions() {
        Country selectedCountry = country_combobox.getSelectionModel().getSelectedItem();

        if (selectedCountry != null) {
            ObservableList<Division> divisionList = FXCollections.observableArrayList();
            HashMap<Integer, Division> divisionHashMap = new FirstLevelDivisionsDAOImpl().getDivisionByCountryID(selectedCountry.getCountryID());

//            for (Integer divisionID : divisionHashMap.keySet()) {
//                divisionList.add(divisionHashMap.get(divisionID));
//            }

            // Lambda expression
            divisionHashMap.keySet().forEach(divisionKey -> divisionList.add(divisionHashMap.get(divisionKey)));

            division_combobox.setItems(divisionList);
        }

        division_combobox.setValue(null);
    }

    /**
     * Method to save the update/modification to the customer
     * Displays appropriate messages if the customer was updated successfully or if an error was occurred
     *
     * @param event Button click
     */
    public void modifyCustomer(ActionEvent event) {
        Integer custID = selectedCustomer.getID();
        String name = name_field.getText();
        String address = address_field.getText();
        String postalCode = postal_code_field.getText();
        String phone = phone_field.getText();
        Integer divisionID = division_combobox.getSelectionModel().getSelectedItem().getDivisionID();

        CustomersDAOImpl customersDAOImpl = new CustomersDAOImpl();

        boolean performUpdate = customersDAOImpl.updateCustomer(custID, name, address, postalCode, phone, divisionID);

        if (performUpdate) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer " + custID + " successfully updated.");
            alert.showAndWait();
            openNewView(event, "customers-view.fxml", "Customers");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There was a problem performing the customer update. Please try again.");
            alert.showAndWait();
        }
    }

    // Cancel button
    public void cancelModifyCustomer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel? Your changes will not be saved.");
        Optional<ButtonType> confirmCancel = alert.showAndWait();

        if (confirmCancel.isPresent() && confirmCancel.get() == ButtonType.OK) {
            openNewView(event, "customers-view.fxml", "Customers");
        }
    }

    /**
     * Initialize the view/controller
     * selectedCustomer is received from CustomersViewController.getSelectedCustomer()
     * Fields are pre-populated with data from selectedCustomer object
     *
     * @param url Parameter to initialize the view
     * @param resourceBundle Parameter to initialize the view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = CustomersViewController.getSelectedCustomer();

        // SET FIELD VALUES
        customer_ID_field.setText(String.valueOf(selectedCustomer.getID()));
        name_field.setText(selectedCustomer.getName());
        address_field.setText(selectedCustomer.getAddress());
        postal_code_field.setText(selectedCustomer.getPostalCode());
        phone_field.setText(selectedCustomer.getPhone());

        // SET COMBOBOX VALUES
        HashMap<Integer, Country> countryHashMap = new CountriesDAOImpl().getAllCountries();
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        for (Integer countryID : countryHashMap.keySet()) {
            countryList.add(countryHashMap.get(countryID));
        }

        HashMap<Integer, Division> divisionHashMap = new FirstLevelDivisionsDAOImpl().getAllDivisions();
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        Country initialCountry = countryHashMap.get(divisionHashMap.get(selectedCustomer.getDivisionID()).getCountryID());

        country_combobox.setItems(countryList);
        country_combobox.setValue(initialCountry);

        divisionHashMap = new FirstLevelDivisionsDAOImpl().getDivisionByCountryID(initialCountry.getCountryID());

        for (Integer divisionID : divisionHashMap.keySet()) {
            divisionList.add(divisionHashMap.get(divisionID));
        }

        division_combobox.setItems(divisionList);
        division_combobox.setValue(divisionHashMap.get(selectedCustomer.getDivisionID()));
    }
}
