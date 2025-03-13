package com.mycompany.b.l.db;

import java.time.LocalDate;

/**
 * Driver class represents a driver in the system.
 * It includes personal details, license information, and work-related attributes.
 */
public class Driver {

  
    private int driverID; // Mark as final
    private String name;
    private String nic;
    private String licenseNumber;
    private LocalDate licenseExpiryDate;
    private String phoneNumber;
    private String address;
    private String email;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Integer assignedCarID;
    private boolean availability;
    private int yearsOfExperience;
    private double rating;
    private LocalDate lastTripDate;
    private String emergencyContact;
    private double salary;
    private String username;
    private String password;

    public void setdriverID(int driverID) {
       this.driverID = driverID;
    }

    String getSalt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Enum for gender specification.
     */
    public enum Gender {
        Male, Female, Other
    }

    /**
     * Default constructor initializing default values.
     */
    public Driver() {
        this.driverID = 0; // Default ID
        this.availability = true; // Default availability
        this.yearsOfExperience = 0; // Default years of experience
        this.rating = 0.0; // Default rating
        this.salary = 0.0; // Default salary
    }

    /**
     * Parameterized constructor to initialize all attributes.
     */
    public Driver(int driverID, String name, String nic, String licenseNumber, LocalDate licenseExpiryDate,
                  String phoneNumber, String address, String email, LocalDate dateOfBirth, Gender gender,
                  boolean availability, int yearsOfExperience, double rating,
                  LocalDate lastTripDate, String emergencyContact, double salary,Integer assignedCarID, String username, String password) {
        this.driverID = driverID;
        this.setName(name);
        this.setNic(nic);
        this.setLicenseNumber(licenseNumber);
        this.licenseExpiryDate = licenseExpiryDate;
        this.setPhoneNumber(phoneNumber);
        this.setAddress(address);
        this.setEmail(email);
        this.setDateOfBirth(dateOfBirth);
        this.setGender(gender);
        this.assignedCarID = assignedCarID;
        this.availability = availability;
        this.setYearsOfExperience(yearsOfExperience);
        this.setRating(rating);
        this.lastTripDate = lastTripDate;
        this.setEmergencyContact(emergencyContact);
        this.setSalary(salary);
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public int getDriverID() {
        return driverID;
    }
      public void setDriverID(int driverID) {
        this.driverID = driverID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        if (nic == null || nic.trim().isEmpty()) {
            throw new IllegalArgumentException("NIC cannot be null or empty");
        }
        this.nic = nic;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("License number cannot be null or empty");
        }
        this.licenseNumber = licenseNumber;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        this.gender = gender;
    }

    public Integer getAssignedCarID() {
        return assignedCarID;
    }

    public void setAssignedCarID(Integer assignedCarID) {
        this.assignedCarID = assignedCarID;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        this.rating = rating;
    }

    public LocalDate getLastTripDate() {
        return lastTripDate;
    }

    public void setLastTripDate(LocalDate lastTripDate) {
        this.lastTripDate = lastTripDate;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        if (emergencyContact == null || emergencyContact.trim().isEmpty()) {
            throw new IllegalArgumentException("Emergency contact cannot be null or empty");
        }
        this.emergencyContact = emergencyContact;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0.0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the driver object.
     */
    @Override
    public String toString() {
        return "Driver [driverID=" + driverID + ", name=" + name + ", nic=" + nic + ", licenseNumber=" + licenseNumber
                + ", licenseExpiryDate=" + (licenseExpiryDate != null ? licenseExpiryDate : "null")
                + ", phoneNumber=" + phoneNumber + ", address=" + address
                + ", email=" + email + ", dateOfBirth=" + (dateOfBirth != null ? dateOfBirth : "null")
                + ", gender=" + (gender != null ? gender : "null") + ", assignedCarID=" + assignedCarID
                + ", availability=" + availability + ", yearsOfExperience=" + yearsOfExperience
                + ", rating=" + rating + ", lastTripDate=" + (lastTripDate != null ? lastTripDate : "null")
                + ", emergencyContact=" + emergencyContact + ", salary=" + salary + ", username=" + username + ", password=" + password + "]";
    }
}