package com.example.c195_software2.model;

/**
 * Contact Model
 */
public class Contact {
    private final Integer ID;
    private final String name;
    private final String email;

    public Contact(Integer ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return this.getName() + " - " + this.getID();
    }
}
