package com.example.c195_software2.DAOImpl;

import com.example.c195_software2.DAO.CustomersDAO;
import com.example.c195_software2.database.DBConnection;
import com.example.c195_software2.model.*;
import com.example.c195_software2.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Implementation of CustomersDAO
 */
public class CustomersDAOImpl implements CustomersDAO {
    private static final String selectAllCustomersSQL = "SELECT * FROM customers";
    private static final String getApptByCustSQL = "SELECT * FROM appointments WHERE Customer_ID = ?";
    private static final String deleteCustomerSQL = "DELETE from customers WHERE Customer_ID = ?";
    private static final String getScheduleByMonthSQL = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(?) AND Customer_ID = ?";
    private static final String getScheduleByWeekSQL = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(?) AND Customer_ID = ?";
    private static final String filterMonthCountCurrentYearSQL = "SELECT MONTH(Start) AS 'Month', COUNT(*) AS 'Count_Month' FROM appointments WHERE Customer_ID = ? AND YEAR(Start) = YEAR(now()) GROUP BY MONTH(Start)";
    private static final String filterTypeCountCurrentYearSQL = "SELECT Type, COUNT(*) AS 'Count_Type' FROM appointments WHERE Customer_ID = ? AND YEAR(Start) = YEAR(now()) GROUP BY Type";
    private static final String filterMonthCountAllSQL = "SELECT MONTH(Start) AS 'Month', COUNT(*) AS 'Count_Month' FROM appointments WHERE Customer_ID = ? GROUP BY MONTH(Start)";
    private static final String filterTypeCountAllSQL = "SELECT Type, COUNT(*) AS 'Count_Type' FROM appointments WHERE Customer_ID = ? GROUP BY Type";
    private static final String modifyCustomerSQL = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
    private static final String createCustomerSQL = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public ObservableList<Appointment> getAppointmentsByCustomer(int custID) {
        ObservableList<Appointment> appointmentsByCustList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getApptByCustSQL);
            ps.setString(1, String.valueOf(custID));

            ResultSet rs = ps.executeQuery();

            System.out.println(ps);

            if (rs == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No records found.");
                alert.showAndWait();
            } else {
                while (rs.next()) {
                    int ID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    int contactID = rs.getInt("Contact_ID");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");

                    Appointment tempAppointment = new Appointment(ID, title, description, location, contactID, type, start, end, customerID, userID);
                    appointmentsByCustList.add(tempAppointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsByCustList;
    }

    @Override
    public ObservableList<Appointment> getCustomerScheduleMonth(String date, int custID) {
        ObservableList<Appointment> custScheduleList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getScheduleByMonthSQL);
            ps.setString(1, String.valueOf(date));
            ps.setString(2, String.valueOf(custID));

            ResultSet rs = ps.executeQuery();

            System.out.println(ps);

            if (rs == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No records found.");
                alert.showAndWait();
            } else {
                while (rs.next()) {
                    int ID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    int contactID = rs.getInt("Contact_ID");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");

                    Appointment tempAppointment = new Appointment(ID, title, description, location, contactID, type, start, end, customerID, userID);
                    custScheduleList.add(tempAppointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return custScheduleList;
    }

    @Override
    public ObservableList<Appointment> getCustomerScheduleWeek(String date, int custID) {
        ObservableList<Appointment> custScheduleList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getScheduleByWeekSQL);
            ps.setString(1, String.valueOf(date));
            ps.setString(2, String.valueOf(custID));

            ResultSet rs = ps.executeQuery();

            System.out.println(ps);

            if (rs == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No records found.");
                alert.showAndWait();
            } else {
                while (rs.next()) {
                    int ID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    int contactID = rs.getInt("Contact_ID");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");

                    Appointment tempAppointment = new Appointment(ID, title, description, location, contactID, type, start, end, customerID, userID);
                    custScheduleList.add(tempAppointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return custScheduleList;
    }

    @Override
    public ObservableList<AppointmentMonthCount> getAppointmentsByMonthCurrentYearFilter(int custID) {
        ObservableList<AppointmentMonthCount> monthCountList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(filterMonthCountCurrentYearSQL);
            ps.setString(1, String.valueOf(custID));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AppointmentMonthCount mc = new AppointmentMonthCount(rs.getString("Month"), rs.getInt("Count_Month"));

                monthCountList.add(mc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthCountList;
    }

    @Override
    public ObservableList<AppointmentTypeCount> getAppointmentsByTypeCurrentYearFilter(int custID) {
        ObservableList<AppointmentTypeCount> typeCountList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(filterTypeCountCurrentYearSQL);
            ps.setString(1, String.valueOf(custID));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AppointmentTypeCount ac = new AppointmentTypeCount(rs.getString("Type"), rs.getInt("Count_Type"));
                typeCountList.add(ac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeCountList;
    }

    @Override
    public ObservableList<AppointmentMonthCount> getAllAppointmentsByMonthFilter(int custID) {
        ObservableList<AppointmentMonthCount> monthCountList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(filterMonthCountAllSQL);
            ps.setString(1, String.valueOf(custID));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AppointmentMonthCount mc = new AppointmentMonthCount(rs.getString("Month"), rs.getInt("Count_Month"));

                monthCountList.add(mc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthCountList;
    }

    @Override
    public ObservableList<AppointmentTypeCount> getAllAppointmentsByTypeFilter(int custID) {
        ObservableList<AppointmentTypeCount> typeCountList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(filterTypeCountAllSQL);
            ps.setString(1, String.valueOf(custID));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AppointmentTypeCount ac = new AppointmentTypeCount(rs.getString("Type"), rs.getInt("Count_Type"));
                typeCountList.add(ac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeCountList;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customersList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectAllCustomersSQL);
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer tempCustomer = new Customer(Integer.valueOf(rs.getInt("Customer_ID")), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), Integer.valueOf(rs.getInt("Division_ID")));
                customersList.add(tempCustomer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customersList;
    }

    @Override
    public boolean createCustomer(String name, String address, String postalCode, String phone, Integer divisionID) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(createCustomerSQL);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, String.valueOf(LocalDateTime.now()));
            ps.setString(6, Session.getSessionUser().getUsername());
            ps.setString(7, String.valueOf(LocalDateTime.now()));
            ps.setString(8, Session.getSessionUser().getUsername());
            ps.setString(9, String.valueOf(divisionID));

            System.out.println(ps);

            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCustomer(Integer custID, String name, String address, String postalCode, String phone, Integer divisionID) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(modifyCustomerSQL);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, String.valueOf(LocalDateTime.now()));
            ps.setString(6, Session.getSessionUser().getUsername());
            ps.setString(7, String.valueOf(divisionID));
            ps.setString(8, String.valueOf(custID));

            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(int custID) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteCustomerSQL);
            ps.setString(1, String.valueOf(custID));
            System.out.println(ps);

            ps.executeUpdate();

            System.out.println(ps);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
