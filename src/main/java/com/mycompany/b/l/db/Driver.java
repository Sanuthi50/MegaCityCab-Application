package com.mycompany.b.l.db;

import java.time.LocalDate;

/**
 * Driver class represents a driver in the system.
 * It includes personal details, license information, and work-related attributes.
 */
public class Driver {

    private int driverID;
    private String name;
    private String nic;
    private String licenseNumber;
    private LocalDate licenseExpiryDate;
    private String phoneNumber;
    private String address;
    private String email;
    private LocalDate dateOfBirth;
    private Gender gender;
    private int assignedCarID;
    private boolean availability;
    private int yearsOfExperience;
    private double rating;
    private LocalDate lastTripDate;
    private String emergencyContact;
    private double salary;

    /**
     * Enum for gender specification.
     */
    public enum Gender {
        Male, Female, Other
    }

    /**
     * Default constructor initializing default values.
     */
    public Driver(String updated_Name, String v, String aB1234567, LocalDate of, String string, String _New_Street_City, String updatedemailexamplecom, LocalDate of1, Gender Male, boolean par, int driverID1, double par1, LocalDate of2, String string1, double par2, int par3) {
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
                  int assignedCarID, boolean availability, int yearsOfExperience, double rating, 
                  LocalDate lastTripDate, String emergencyContact, double salary) {
        this.driverID = driverID;
        this.name = name;
        this.nic = nic;
        this.licenseNumber = licenseNumber;
        this.licenseExpiryDate = licenseExpiryDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.assignedCarID = assignedCarID;
        this.availability = availability;
        this.yearsOfExperience = Math.max(0, yearsOfExperience);
        this.rating = Math.min(5.0, Math.max(0.0, rating)); // Ensuring rating is between 0-5
        this.lastTripDate = lastTripDate;
        this.emergencyContact = emergencyContact;
        this.salary = Math.max(0.0, salary);
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
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
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
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAssignedCarID() {
        return assignedCarID;
    }

    public void setAssignedCarID(int assignedCarID) {
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
        this.yearsOfExperience = Math.max(0, yearsOfExperience);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = Math.min(5.0, Math.max(0.0, rating)); // Ensuring rating stays between 0-5
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
        this.emergencyContact = emergencyContact;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = Math.max(0.0, salary);
    }

    /**
     * Returns a string representation of the driver object.
     */
    @Override
    public String toString() {
        return "Driver [driverID=" + driverID + ", name=" + name + ", nic=" + nic + ", licenseNumber=" + licenseNumber
                + ", licenseExpiryDate=" + licenseExpiryDate + ", phoneNumber=" + phoneNumber + ", address=" + address
                + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", assignedCarID="
                + assignedCarID + ", availability=" + availability + ", yearsOfExperience=" + yearsOfExperience
                + ", rating=" + rating + ", lastTripDate=" + lastTripDate + ", emergencyContact=" + emergencyContact
                + ", salary=" + salary + "]";
    }
}
