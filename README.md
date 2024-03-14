Java Appointment Scheduling Desktop Application with MySQL Database

* IDE: Intellij IDEA 2022.2.2 (EDU)
* JDK: OpenJDK 21.0.1
* MySQL Connector: 8.2.0
* JavaFX: 17.0.2

# Appointment Schduler Desktop Application

## Overview
This application is provides an intuitive way to view and manage (apply CRUD operations) on appointments in a MySQL database. The application utilizes MVC design patterns and showcases OOP proficiency, DB operations, and package/dependency management. Users can log in, receive apointment alerts, and view filters. Business logic includes validating fields, verifying no overlapping appointments can be scheduled, monitoring login behavior, etc.

### Front-End
<img width="912" alt="Screenshot 2023-12-19 at 6 48 00 PM" src="https://github.com/culturedmold/JavaAppointmentScheduler/assets/122142361/829f55a4-ad87-4656-a637-280d30bc4ae7">

<img width="912" alt="Screenshot 2023-12-19 at 6 49 16 PM" src="https://github.com/culturedmold/JavaAppointmentScheduler/assets/122142361/53260528-f7ec-4c75-96fd-263b60a61c6e">

<img width="912" alt="Screenshot 2023-12-19 at 6 49 22 PM" src="https://github.com/culturedmold/JavaAppointmentScheduler/assets/122142361/30c95c4c-6e0a-4329-9fed-97d0074f0993">

<img width="512" alt="Screenshot 2023-12-19 at 6 49 42 PM" src="https://github.com/culturedmold/JavaAppointmentScheduler/assets/122142361/1541ff51-2aff-413b-a8d5-2f3a34608d0f">


### Reports
Reports can be accessed from various parts of the application:
1. From the Appointments view (appointments-view.fxml), we can filter reports for appointments by Month or Week using the date selector.
2. From Customers view (customers-view.fxml), we can filter reports for all appointments by customer, all appointments by customer for a specific month, all appointments by customer for a specific week.
3. Clicking the "By Month & Type" button on Customers view will take us to a window where we can filter reports for all appointments for a specified customer by month and type for the current year or all time.
4. Contacts view allows us to view a report of the full schedule for any individual contact.
5. Customers view, we can filter a report to display a customer's appointment list for any given month or week. Using the date selector and radio buttons, we can choose any week or month and display a report of the customer's appointments only for the chosen time period.

