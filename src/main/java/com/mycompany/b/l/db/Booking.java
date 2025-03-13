/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.b.l.db;

/**
 *
 * @author User
 */
import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int customerId;
    private String pickupLocation;
    private String dropLocation;
    private double price; // Changed to double for simplicity
    private double discount; // Changed to double for simplicity
    private double tax; // Changed to double for simplicity
    private Timestamp bookingDate;
    private Status status; // Changed to enum type
    private Integer carId;
    private Integer driverId;
    private double distance;
    private Timestamp pickuptime;

   

    // Enum for Status
    public enum Status {
        Pending, Completed, Confirmed, Canceled;

        // Case-insensitive lookup method
        public static Status fromString(String value) {
            if (value != null) {
                for (Status status : Status.values()) {
                    if (status.name().equalsIgnoreCase(value)) {
                        return status;
                    }
                }
                throw new IllegalArgumentException("Unknown Status: " + value);
            }
            return null; // or handle default case
        }
    }
    // Constructor
    public Booking(int bookingId, int customerId, String pickupLocation, String dropLocation,
                   double price, double discount, double tax, Timestamp bookingDate,
                   Status status, Integer carId, Integer driverId,double distance,Timestamp pickuptime) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.price = price;
        this.discount = discount;
        this.tax = tax;
        this.bookingDate = bookingDate;
        this.status = status;
        this.carId = carId;
        this.driverId = driverId;
        this.distance = distance;
        this.pickuptime= pickuptime;
    }
    

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public Timestamp getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(Timestamp pickuptime) {
        this.pickuptime = pickuptime;
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

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

   @Override
public String toString() {
    return "Booking{" +
            "bookingId=" + bookingId +
            ", customerId=" + customerId +
            ", pickupLocation='" + pickupLocation + '\'' +
            ", dropLocation='" + dropLocation + '\'' +
            ", price=" + price +
            ", discount=" + discount +
            ", tax=" + tax +
            ", bookingDate=" + bookingDate +
            ", status=" + status +
            ", carId=" + carId +
            ", driverId=" + driverId +
            ", pickuptime=" + pickuptime + 
            '}';
}

}