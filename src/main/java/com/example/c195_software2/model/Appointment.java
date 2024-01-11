package com.example.c195_software2.model;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Appointment Model
 */
public class Appointment {

    private final Integer appointmentID;
    private String title;
    private String description;
    private String location;
    private int contactID;
    private String type;
    private final Timestamp start;
    private final Timestamp end;
    private int customerID;
    private int userID;

    // CONSTRUCTOR
    public Appointment(int appointmentID, String title, String description, String location, int contactID, String type, Timestamp start, Timestamp end, int customerID, int userID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getContact() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = Integer.valueOf(contactID);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Timestamp getStart() {
        return start;
    }

    public LocalDateTime getStartAsLocalDateTime() {
        return start.toLocalDateTime();
    }

    public LocalDate getStartAsLocalDate() {
//        LocalDateTime startLDT = this.getStart().toLocalDateTime();
//        ZonedDateTime dbStartTimeUTC = ZonedDateTime.of(startLDT, ZoneId.of("UTC"));
//        ZonedDateTime startDateTimeDisplay = dbStartTimeUTC.withZoneSameInstant(ZoneId.systemDefault());
//
//        return startDateTimeDisplay.toLocalDate();

        return this.getStart().toLocalDateTime().toLocalDate();
    }

    public LocalTime getStartLocalTime() {
//        LocalDateTime startLDT = this.getStart().toLocalDateTime();
//        ZonedDateTime dbStartTimeUTC = ZonedDateTime.of(startLDT, ZoneId.of("UTC"));
//        ZonedDateTime startDateTimeDisplay = dbStartTimeUTC.withZoneSameInstant(ZoneId.systemDefault());
//
//        return startDateTimeDisplay.toLocalTime();
        return this.getStart().toLocalDateTime().toLocalTime();
    }

    public Timestamp getEnd() {
        return end;
    }

    public LocalDate getEndAsLocalDate() {
//        LocalDateTime endLDT = this.getEnd().toLocalDateTime();
//        ZonedDateTime dbEndTimeUTC = ZonedDateTime.of(endLDT, ZoneId.of("UTC"));
//        ZonedDateTime endDateTimeDisplay = dbEndTimeUTC.withZoneSameInstant(ZoneId.systemDefault());
//        return endDateTimeDisplay.toLocalDateTime().toLocalDate();
        return this.getEnd().toLocalDateTime().toLocalDate();
    }

    public LocalTime getEndLocalTime() {
//        LocalDateTime endLDT = this.getEnd().toLocalDateTime();
//        ZonedDateTime dbEndTimeUTC = ZonedDateTime.of(endLDT, ZoneId.of("UTC"));
//        ZonedDateTime endDateTimeDisplay = dbEndTimeUTC.withZoneSameInstant(ZoneId.systemDefault());
//
//        return endDateTimeDisplay.toLocalTime();
        ZonedDateTime endZDT = ZonedDateTime.of(this.getEnd().toLocalDateTime(), ZoneId.systemDefault());
        return endZDT.toLocalDateTime().toLocalTime();
    }

    public String getLocalTimeString(LocalTime localTime) {
        String formatted = localTime.format(DateTimeFormatter.ofPattern("hh:mm"));
        return formatted;
    }

    // get formatted timestamps for proper displaying to user (per project requirements, displays in User's local time)
    public ZonedDateTime getStartZonedDateTime() {
        LocalDateTime startLDT = this.getStart().toLocalDateTime();
//        ZonedDateTime dbStartTimeUTC = ZonedDateTime.of(startLDT, ZoneId.of("UTC"));
//        ZonedDateTime startDateTimeDisplay = dbStartTimeUTC.withZoneSameInstant(ZoneId.systemDefault());

        ZonedDateTime startDateTimeDisplay = ZonedDateTime.of(startLDT, ZoneId.systemDefault());

        return startDateTimeDisplay;
    }

    public ZonedDateTime getEndZonedDateTime() {
        LocalDateTime endLDT = this.getEnd().toLocalDateTime();

        ZonedDateTime endDateTimeDisplay = ZonedDateTime.of(endLDT, ZoneId.systemDefault());

        return endDateTimeDisplay;
    }

    // Format ZonedDateTime for Display
    public String formatZonedDateTime(ZonedDateTime zdt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyy, hh:mm a");

        return zdt.format(formatter);
    }

    public String getStartFormattedDisplay() {
        return this.formatZonedDateTime(this.getStartZonedDateTime());
    }

    public String getEndFormattedDisplay() {
        return this.formatZonedDateTime(this.getEndZonedDateTime());
    }

}
