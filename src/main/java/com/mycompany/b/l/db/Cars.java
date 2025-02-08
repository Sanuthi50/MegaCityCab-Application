/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author User
 */
import java.time.LocalDate;
import java.util.UUID;

public class Cars {
     public enum FuelType { PETROL, DIESEL, ELECTRIC, HYBRID }
    public enum Status { AVAILABLE, UNAVAILABLE, IN_SERVICE }

    private UUID carID;
    private String licensePlate;
    private String model;
    private String make;
    private int year;
    private int capacity;
    private FuelType fuelType;
    private boolean availability;
    private UUID driverID; // Nullable foreign key
    private LocalDate lastServiceDate;
    private LocalDate insuranceExpiryDate;
    private double mileage;
    private Status status;

    // Constructor
    public Cars(UUID carID, String licensePlate, String model, String make, int year, int capacity, FuelType fuelType,
                boolean availability, UUID driverID, LocalDate lastServiceDate, LocalDate insuranceExpiryDate,
                double mileage, Status status) {
        this.carID = carID;
        this.licensePlate = licensePlate;
        this.model = model;
        this.make = make;
        this.year = year;
        this.capacity = capacity;
        this.fuelType = fuelType;
        this.availability = availability;
        this.driverID = driverID;
        this.lastServiceDate = lastServiceDate;
        this.insuranceExpiryDate = insuranceExpiryDate;
        this.mileage = mileage;
        this.status = status;
    }

    // Getters and Setters
    public UUID getCarID() { return carID; }
    public void setCarID(UUID carID) { this.carID = carID; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public FuelType getFuelType() { return fuelType; }
    public void setFuelType(FuelType fuelType) { this.fuelType = fuelType; }

    public boolean isAvailability() { return availability; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    public UUID getDriverID() { return driverID; }
    public void setDriverID(UUID driverID) { this.driverID = driverID; }

    public LocalDate getLastServiceDate() { return lastServiceDate; }
    public void setLastServiceDate(LocalDate lastServiceDate) { this.lastServiceDate = lastServiceDate; }

    public LocalDate getInsuranceExpiryDate() { return insuranceExpiryDate; }
    public void setInsuranceExpiryDate(LocalDate insuranceExpiryDate) { this.insuranceExpiryDate = insuranceExpiryDate; }

    public double getMileage() { return mileage; }
    public void setMileage(double mileage) { this.mileage = mileage; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
}
