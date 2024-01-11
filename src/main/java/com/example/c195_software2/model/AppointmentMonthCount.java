package com.example.c195_software2.model;

import java.time.Month;

/**
 * AppointmentMonthCount Model
 */
public class AppointmentMonthCount {
    private String month;
    private Integer count;

    public AppointmentMonthCount(String month, Integer count) {
        this.month = month;
        this.count = count;
    }

    public String getMonthName() {
        return Month.of(Integer.parseInt(month)).name();
    }

    public Integer getCount() {
        return count;
    }
}
