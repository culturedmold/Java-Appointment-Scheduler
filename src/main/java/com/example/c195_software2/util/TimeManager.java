package com.example.c195_software2.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

/**
 * TimeManager class to manage time conversions and validate business hours
 */
public final class TimeManager {

    /**
     * Method to evaluate whether a time falls within a range
     * @param initialDateTime LocalDateTime - the first date time (such as the login time of a user)
     * @param dateTimeToEvaluate LocalDateTime - the time to evaluate if it falls within the range
     * @return boolean - true if dateTimeToEvaluate falls within the range, false otherwise
     */
    public static boolean evaluateTimeDelta(LocalDateTime initialDateTime, LocalDateTime dateTimeToEvaluate) {

        if (dateTimeToEvaluate.isAfter(initialDateTime) && dateTimeToEvaluate.isBefore(initialDateTime.plusMinutes(15))) {
            return true;
        }

        return false;
    }

    /**
     * Method to convert LocalDateTime to ZonedDateTime
     * @param localDateTime LocalDateTime to convert
     * @param zoneID ZoneID used in the conversion
     * @return ZonedDateTime - the converted LocalDateTime
     */
    public static ZonedDateTime localDateTimeToZonedDateTime(LocalDateTime localDateTime, ZoneId zoneID) {

        return ZonedDateTime.of(localDateTime, zoneID);
    }

    /**
     * Method to convert a LocalDateTime to a string in "yyyy-MM-dd HH:mm:ss" format
     * @param localDateTime LocalDateTime to convert
     * @return converted LocalDateTime as a String
     */
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * Method to convert a ZonedDateTime to an Instant
     * @param zdt - ZonedDateTime to convert to Instant
     * @return the ZonedDateTime converted into an Instant
     */
    public static Instant zonedDateTimeToInstant(ZonedDateTime zdt) {
        return zdt.toInstant();
    }

    /**
     * Combine a LocalDate object, time as string, and am/pm as string into LocalDateTime
     *
     * @param localDate LocalDate object
     * @param timeString time as String
     * @param amPmString am/pm as String
     * @return LocalDateTimeObject
     */
    public static String combineLocalDateTimeToString(LocalDate localDate, String timeString, String amPmString) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        try {

            String localTimeString = timeString + " " + amPmString;

            // Get date and time values from fields/pickers/etc.
            LocalTime localTime = LocalTime.parse(localTimeString, timeFormatter);

            // Combine value from date picker and user-inputted time to get a LocalDateTime object
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);
            ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());

            System.out.println("ZDT " + zdt.format(DateTimeFormatter.ofPattern("dd MM yyy hh:mm a")));

//            String timeStringDB = zdtToLocalTime.format(HHFormatter);
            System.out.println(zdt);

            return zdt.toLocalDateTime().toString();

        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static LocalDateTime combineLocalDateTime(LocalDate localDate, String timeString, String amPmString) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        try {

            String localTimeString = timeString + " " + amPmString;

            // Get date and time values from fields/pickers/etc.
            LocalTime localTime = LocalTime.parse(localTimeString, timeFormatter);

            // Combine value from date picker and user-inputted time to get a LocalDateTime object
            LocalDateTime ldt = LocalDateTime.of(localDate, localTime);

            System.out.println("ldt: " + ldt);
            return ldt;

        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method to validate business hours
     * Used to determine if an appointment start or end date falls outside business hours

     * @return boolean
     */
    public static boolean validateBusinessHours(LocalDateTime localDateTime) {

        try {
            System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("dd MM yyy HH:mm")));

            ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
            ZonedDateTime zonedDateTimeEST = zdt.withZoneSameInstant(ZoneId.of("America/New_York"));

            System.out.println("ZdtEST " + zonedDateTimeEST.format(DateTimeFormatter.ofPattern("dd MM yyy hh:mm a")));


            ZonedDateTime businessStartEST = zonedDateTimeEST.withHour(8).withMinute(0);
            ZonedDateTime businessEndEST = zonedDateTimeEST.withHour(22).withMinute(0);

            System.out.println("Business Start: " + businessStartEST.format(DateTimeFormatter.ofPattern("dd MM yyy hh:mm a")));
            System.out.println("Business End: " + businessEndEST.format(DateTimeFormatter.ofPattern("dd MM yyy hh:mm a")));

            // Check to ensure the time falls within 8 AM EST (8.0.0) and 10 PM EST (22.0.0)
            if (zonedDateTimeEST.isAfter(businessEndEST)) {
                System.out.println(localDateTime + " cannot be after business hours");
                return true;
            }

            if (zonedDateTimeEST.isBefore(businessStartEST)) {
                System.out.println(localDateTime + " cannot be before business hours");
                return true;
            }

            DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

            // Check to ensure date is not Saturday or Sunday
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                System.out.println(localDateTime + " cannot be on weekend");
                return true;
            }

        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
