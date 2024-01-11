package com.example.c195_software2.model;

/**
 * AppointmentTypeCount model
 */
public class AppointmentTypeCount {
    private String type;
    private Integer count;

    public AppointmentTypeCount(String type, Integer count) {
        this.type = type;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public Integer getCount() {
        return count;
    }
}
