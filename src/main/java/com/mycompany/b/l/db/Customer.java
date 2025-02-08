/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author User
 */
import java.time.LocalDateTime;
public class Customer {
    private String customerID;
    private String username;
    private String passwordHash;
    private String name;
    private String address;
    private String nic;
    private String phoneNumber;
    private String email;
    private LocalDateTime registrationDate;
    private int totalBookings;

    // Default constructor
    public Customer() {
        this.registrationDate = LocalDateTime.now(); // Automatically set registration date to the current date/time
        this.totalBookings = 0; // Default value for total bookings
    }

    // Parameterized constructor
    public Customer(String customerID, String username, String passwordHash, String name, String address, 
                    String nic, String phoneNumber, String email) {
        this.customerID = customerID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.registrationDate = LocalDateTime.now(); // Automatically set registration date to the current date/time
        this.totalBookings = 0; // Default value for total bookings
    }

    Customer(String string, String string0, String string1, String string2, String string3, String string4, String string5, String string6, LocalDateTime toLocalDateTime, int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters and Setters
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    // Method to display customer information
    public void displayCustomerInfo() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("Username: " + username);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("NIC: " + nic);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Registration Date: " + registrationDate);
        System.out.println("Total Bookings: " + totalBookings);
    }
    
}
