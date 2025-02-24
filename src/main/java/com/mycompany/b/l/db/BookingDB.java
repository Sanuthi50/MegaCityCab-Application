/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.b.l.db;

/**
 *
 * @author User
 */
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDB {
    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();   

    // Create a new booking
    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (CustomerID, PickupLocation, DropLocation, Price, Discount, Tax, BookingDate, Status, CarID, DriverID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection(); 
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, booking.getCustomerId());
            statement.setString(2, booking.getPickupLocation());
            statement.setString(3, booking.getDropLocation());
            statement.setBigDecimal(4, booking.getPrice());
            statement.setBigDecimal(5, booking.getDiscount());
            statement.setBigDecimal(6, booking.getTax());
            statement.setTimestamp(7, booking.getBookingDate());
            statement.setString(8, booking.getStatus());
            statement.setInt(9, booking.getCarId());
            statement.setInt(10, booking.getDriverId());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read a booking by ID
    public Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE BookingID = ?";
        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, bookingId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Booking(
                        resultSet.getInt("BookingID"),
                        resultSet.getInt("CustomerID"),
                        resultSet.getString("PickupLocation"),
                        resultSet.getString("DropLocation"),
                        resultSet.getBigDecimal("Price"),
                        resultSet.getBigDecimal("Discount"),
                        resultSet.getBigDecimal("Tax"),
                        resultSet.getTimestamp("BookingDate"),
                        resultSet.getString("Status"),
                        resultSet.getInt("CarID"),
                        resultSet.getInt("DriverID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Read all bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = dbConnection.getConnection(); 
        Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Booking booking = new Booking(
                        resultSet.getInt("BookingID"),
                        resultSet.getInt("CustomerID"),
                        resultSet.getString("PickupLocation"),
                        resultSet.getString("DropLocation"),
                        resultSet.getBigDecimal("Price"),
                        resultSet.getBigDecimal("Discount"),
                        resultSet.getBigDecimal("Tax"),
                        resultSet.getTimestamp("BookingDate"),
                        resultSet.getString("Status"),
                        resultSet.getInt("CarID"),
                        resultSet.getInt("DriverID")
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Update a booking
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET CustomerID = ?, PickupLocation = ?, DropLocation = ?, Price = ?, Discount = ?, Tax = ?, BookingDate = ?, Status = ?, CarID = ?, DriverID = ? WHERE BookingID = ?";
        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, booking.getCustomerId());
            statement.setString(2, booking.getPickupLocation());
            statement.setString(3, booking.getDropLocation());
            statement.setBigDecimal(4, booking.getPrice());
            statement.setBigDecimal(5, booking.getDiscount());
            statement.setBigDecimal(6, booking.getTax());
            statement.setTimestamp(7, booking.getBookingDate());
            statement.setString(8, booking.getStatus());
            statement.setInt(9, booking.getCarId());
            statement.setInt(10, booking.getDriverId());
            statement.setInt(11, booking.getBookingId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a booking by ID
    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE BookingID = ?";
        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, bookingId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
