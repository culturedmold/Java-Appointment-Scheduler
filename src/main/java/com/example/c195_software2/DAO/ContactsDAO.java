package com.example.c195_software2.DAO;

import com.example.c195_software2.model.Appointment;
import com.example.c195_software2.model.Contact;
import javafx.collections.ObservableList;

/**
 * ContactsDAO interface
 */
public interface ContactsDAO {
    /**
     * Method to get all contacts from database
     * @return ObservableList of Contact objects
     */
    ObservableList<Contact> getAllContacts();

    /**
     * Method to get all appointments by contact ID from databse
     * @param contactID Contact ID as int
     * @return ObservableList of Appointment objects
     */
    ObservableList<Appointment> getContactSchedule(int contactID);
}
