package com.example.c195_software2.model;

/**
 * User Model
 */
public class User {
    private final String password;
    private final int userID;
    private final String username;
    public User(int userID, String username, String password) {
        this.password = password;
        this.userID = userID;
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }
    public String getUsername() {
        return username;
    }
}
