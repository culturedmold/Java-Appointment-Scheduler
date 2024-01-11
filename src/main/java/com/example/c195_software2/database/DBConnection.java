package com.example.c195_software2.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection
 */
public abstract class DBConnection {

    // strings to establish a connection to our DB
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String location = "//localhost/";
    private static final String dbName = "client_schedule";
    private static final String jdbcURL = protocol + vendorName + location + dbName + "?connectionTimeZone = SERVER"; // local
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // driver reference
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection = null;

    // "?connectionTimeZone = SERVER"


    /**
     * Method to open a connection to database and initialize Connection object
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // locate driver
            connection = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection successful.");
        } catch (SQLException e) {
            e.printStackTrace();
            // System.out.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to get and return Connection object
     * @return Connection object (connection variable)
     */
    public static Connection getConnection() {
        return connection;
    }


    /**
     * Close the connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Close DB connection.");
        } catch (Exception e) { // handle race conditions
            System.out.println("Error: " + e.getMessage());
        }
    }
}
