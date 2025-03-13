package com.mycompany.b.l.db;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Car {
    private int carID;
    private String licensePlate;
    private String model;
    private String make;
    private int year;
    private int capacity;
    private FuelType fuelType;
    private boolean availability;
    private LocalDate lastServiceDate;
    private LocalDate insuranceExpiryDate;
    private BigDecimal mileage;
    private Status status;
    private Integer driverID;
    private double price;
    private double discount;
    private double tax;
    

    // Default constructor (required for serialization/deserialization)
    public Car() {
    }

    // Constructor with all fields
    public Car(int carID, String licensePlate, String model, String make, int year, int capacity, FuelType fuelType, boolean availability, LocalDate lastServiceDate, LocalDate insuranceExpiryDate, BigDecimal mileage, Status status, Integer driverID,double price,Double discount,Double tax) {
        this.carID = carID;
        this.licensePlate = licensePlate;
        this.model = model;
        this.make = make;
        this.year = year;
        this.capacity = capacity;
        this.fuelType = fuelType;
        this.availability = availability;
        this.lastServiceDate = lastServiceDate;
        this.insuranceExpiryDate = insuranceExpiryDate;
        this.mileage = mileage;
        this.status = status;
        this.driverID = driverID;
        this.price = price;
        this.tax = tax;
        this.discount = discount;
      
    }

    // Enum for FuelType
    public enum FuelType {
        Petrol, Diesel, Hybrid, Electric;

        public static FuelType fromDbValue(String value) {
            if (value != null) {
                switch (value.toUpperCase()) {
                    case "PETROL":
                        return Petrol;
                    case "DIESEL":
                        return Diesel;
                    case "HYBRID":
                        return Hybrid;
                    case "ELECTRIC":
                        return Electric;
                    default:
                        throw new IllegalArgumentException("Unknown FuelType: " + value);
                }
            }
            return null; // or handle default case
        }
    }

   public enum Status {
    AVAILABLE("Available"),
    NOT_AVAILABLE("Not Available"),
    ON_TRIP("On Trip"),
    UNDER_MAINTENANCE("Under Maintenance");

    private final String dbValue;

    Status(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    @Override
    public String toString() {
        return dbValue; // Ensure toString() returns dbValue
    }

    public static Status fromDbValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Input value cannot be null");
        }
        String normalizedValue = value.trim(); // Trim extra spaces

        for (Status status : Status.values()) {
            if (status.dbValue.equalsIgnoreCase(normalizedValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + normalizedValue);
    }
}
    // Getters and Setters
    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public LocalDate getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(LocalDate lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public LocalDate getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public void setInsuranceExpiryDate(LocalDate insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
    }

    public BigDecimal getMileage() {
        return mileage;
    }

    public void setMileage(BigDecimal mileage) {
        this.mileage = mileage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

        public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

  
    @Override
    public String toString() {
        return "Car{" +
                "carID=" + carID +
                ", licensePlate='" + licensePlate + '\'' +
                ", model='" + model + '\'' +
                ", make='" + make + '\'' +
                ", year=" + year +
                ", capacity=" + capacity +
                ", fuelType=" + fuelType +
                ", availability=" + availability +
                ", lastServiceDate=" + lastServiceDate +
                ", insuranceExpiryDate=" + insuranceExpiryDate +
                ", mileage=" + mileage +
                ", status=" + status +
                ", driverID=" + driverID +
                ", price=" + price +
                ", dicount=" + discount +
                ", tax=" + tax +
                
              
                '}';
    }
}