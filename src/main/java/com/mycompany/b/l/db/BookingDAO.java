/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.b.l.db;

import com.mycompany.b.l.db.Booking.Status;
import static com.mysql.cj.conf.PropertyKey.logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class BookingDAO {

    private static final Logger LOG = Logger.getLogger(BookingDAO.class.getName());

    // Use the Singleton DatabaseConnection instance
    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();

    // Add a new booking
    public int addBooking(int customerID, String pickupLocation, String dropLocation, double price, double discount, double tax, Timestamp bookingDate, Status status, Integer carID, Integer driverID,double distance) {
    String sql = "INSERT INTO bookings (customerID, pickupLocation, dropLocation, price, discount, tax, bookingDate, status, carID, driverID,distance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    try (Connection conn = dbConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        pstmt.setInt(1, customerID);
        pstmt.setString(2, pickupLocation);
        pstmt.setString(3, dropLocation);
        pstmt.setDouble(4, price);
        pstmt.setDouble(5, discount);
        pstmt.setDouble(6, tax);
        pstmt.setTimestamp(7, bookingDate);
        pstmt.setString(8, status.name());
         pstmt.setDouble(11, distance);// Convert enum to String

        // Handle null values for carID and driverID
        if (carID != null) {
            pstmt.setInt(9, carID);
        } else {
            pstmt.setNull(9, Types.INTEGER);
        }

        if (driverID != null) {
            pstmt.setInt(10, driverID);
        } else {
            pstmt.setNull(10, Types.INTEGER);
        }

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the generated ID
                }
            }
        }
    } catch (SQLException e) {
        // Log the exception instead of printing the stack trace
        LOG.log(Level.SEVERE, "SQL error while adding booking", e);
    }
    return -1; // Return -1 if insertion fails
}
    // Retrieve a booking by ID
    public Booking getBookingById(int bookingID) {
        String query = "SELECT * FROM bookings WHERE BookingID = ?";
        Booking booking = null;

        try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookingID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                booking = new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("CustomerID"),
                    rs.getString("PickupLocation"),
                    rs.getString("DropLocation"),
                    rs.getDouble("Price"),
                    rs.getDouble("Discount"),
                    rs.getDouble("Tax"),
                    rs.getTimestamp("BookingDate"),
                    Status.fromString(rs.getString("Status")), // Convert string to enum
                    rs.getInt("CarID"),
                    rs.getInt("DriverID"),
                    rs.getDouble("Distance")
                );
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error getting booking by ID: " + e.getMessage(), e);
        }

        return booking;
    }

    // Retrieve all bookings
    public List<Booking> getAllBookings() {
        String query = "SELECT * FROM bookings";
        List<Booking> bookingList = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("CustomerID"),
                    rs.getString("PickupLocation"),
                    rs.getString("DropLocation"),
                    rs.getDouble("Price"),
                    rs.getDouble("Discount"),
                    rs.getDouble("Tax"),
                    rs.getTimestamp("BookingDate"),
                   Status.fromString(rs.getString("Status")),
                    rs.getInt("CarID"),
                    rs.getInt("DriverID"),
                        rs.getDouble("Distance")
                );
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error getting all bookings: " + e.getMessage(), e);
        }

        return bookingList;
    }

    public void updateBooking(int bookingID, Integer customerID, String pickupLocation, String dropLocation, Double price, Double discount, Double tax,
                          Timestamp bookingDate, String status, Integer carID, Integer driverID, Double distance) {
    StringBuilder query = new StringBuilder("UPDATE bookings SET ");
    List<Object> parameters = new ArrayList<>();
    boolean hasFieldsToUpdate = false;

    // Dynamically build the query based on provided fields
    if (customerID != null) { query.append("CustomerID = ?, "); parameters.add(customerID); hasFieldsToUpdate = true; }
    if (pickupLocation != null) { query.append("PickupLocation = ?, "); parameters.add(pickupLocation); hasFieldsToUpdate = true; }
    if (dropLocation != null) { query.append("DropLocation = ?, "); parameters.add(dropLocation); hasFieldsToUpdate = true; }
    if (price != null) { query.append("Price = ?, "); parameters.add(price); hasFieldsToUpdate = true; }
    if (discount != null) { query.append("Discount = ?, "); parameters.add(discount); hasFieldsToUpdate = true; }
    if (tax != null) { query.append("Tax = ?, "); parameters.add(tax); hasFieldsToUpdate = true; }
    if (bookingDate != null) { query.append("BookingDate = ?, "); parameters.add(bookingDate); hasFieldsToUpdate = true; }
    if (status != null) { query.append("Status = ?, "); parameters.add(status); hasFieldsToUpdate = true; }
    if (carID != null) { query.append("CarID = ?, "); parameters.add(carID); hasFieldsToUpdate = true; }
    if (driverID != null) { query.append("DriverID = ?, "); parameters.add(driverID); hasFieldsToUpdate = true; }
    if (distance != null) { query.append("Distance = ?, "); parameters.add(distance); hasFieldsToUpdate = true; }

    if (!hasFieldsToUpdate) {
        LOG.warning("No fields provided for update.");
        return;
    }

    // Remove the trailing comma and space
    query.setLength(query.length() - 2); 
    query.append(" WHERE BookingID = ?");
    parameters.add(bookingID);

    try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
         PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

        for (int i = 0; i < parameters.size(); i++) {
            pstmt.setObject(i + 1, parameters.get(i));
        }

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            LOG.info("Booking updated successfully.");
        } else {
            LOG.warning("No booking found with BookingID: " + bookingID);
        }
    } catch (SQLException e) {
        LOG.log(Level.SEVERE, "Error updating booking with BookingID: " + bookingID, e);
    }
}
    // Delete a booking
    public void deleteBooking(int bookingID) {
        String query = "DELETE FROM bookings WHERE BookingID = ?";

        try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, bookingID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error deleting booking: " + e.getMessage(), e);
        }
    }

    // Search for bookings by customer ID
    public List<Booking> searchBookingsByCustomerID(int customerID) {
        String query = "SELECT * FROM bookings WHERE CustomerID = ?";
        List<Booking> bookingList = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("CustomerID"),
                    rs.getString("PickupLocation"),
                    rs.getString("DropLocation"),
                    rs.getDouble("Price"),
                    rs.getDouble("Discount"),
                    rs.getDouble("Tax"),
                    rs.getTimestamp("BookingDate"),
                    Status.fromString(rs.getString("Status")),// Convert string to enum
                    rs.getInt("CarID"),
                    rs.getInt("DriverID"),
                    rs.getDouble("Distance")
                );
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error searching bookings by customer ID: " + e.getMessage(), e);
        }

        return bookingList;
    
    }
    public List<Booking> getBookingsByDriverId(int driverId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE DriverID = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, driverId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(createBookingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by driver ID: " + e.getMessage());
        }
        return bookings;
    }
    public List<Booking> getBookingsWithNullDriverId() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE DriverID IS NULL";
        try (Connection conn = dbConnection.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                bookings.add(createBookingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings with null driver ID: " + e.getMessage());
        }
        return bookings;
    }
    
     public List<Booking> getBookingsByCustomerIdOrderByBookingDate(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE CustomerID = ? ORDER BY BookingDate";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(createBookingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by customer ID: " + e.getMessage());
        }
        return bookings;
    }
    public List<Booking> getBookingsByStatus(String status) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE Status = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(createBookingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by status: " + e.getMessage());
        }
        return bookings;
    }
    public List<Booking> getBookingsByCarId(int carId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE CarID = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, carId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(createBookingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by car ID: " + e.getMessage());
        }
        return bookings;
    }
    public List<Booking> getBookingsByDateRange(Timestamp startDate, Timestamp endDate) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE BookingDate BETWEEN ? AND ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setTimestamp(1, startDate);
            statement.setTimestamp(2, endDate);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookings.add(createBookingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by date range: " + e.getMessage());
        }
        return bookings;
    }
    public int countBookingsByStatus(String status) {
            String sql = "SELECT COUNT(*) FROM bookings WHERE Status = ?";
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, status);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error counting bookings by status: " + e.getMessage());
            }
            return 0;
        }
        public List<Booking> getBookingsByCustomerIdAndStatus(int customerId, String status) {
            List<Booking> bookings = new ArrayList<>();
            String sql = "SELECT * FROM bookings WHERE CustomerID = ? AND Status = ?";

            // Validate input parameters (optional)
            if (status == null) {
                throw new IllegalArgumentException("Status cannot be null");
            }

            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set parameters
                statement.setInt(1, customerId);
                statement.setString(2, status);

                // Execute query
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        bookings.add(createBookingFromResultSet(resultSet));
                    }
                }
            } catch (SQLException e) {
                // Log the error using a proper logging framework
                System.err.println("Error fetching bookings by customer ID and status: " + e.getMessage());
                // Optionally, rethrow the exception or handle it as needed
            }

            return bookings;
        }
    public List<Booking> getBookingsByDriverIdAndStatus(int driverId, String status) {
            List<Booking> bookings = new ArrayList<>();
            String sql = "SELECT * FROM bookings WHERE DriverID = ? AND Status = ?";
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, driverId);
                statement.setString(2, status);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    bookings.add(createBookingFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                System.err.println("Error fetching bookings by driver ID and status: " + e.getMessage());
            }
            return bookings;
        }
     public List<Booking> getBookingsByCarIdAndStatus(int carId, String status) {
            List<Booking> bookings = new ArrayList<>();
            String sql = "SELECT * FROM bookings WHERE CarID = ? AND Status = ?";
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, carId);
                statement.setString(2, status);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    bookings.add(createBookingFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                System.err.println("Error fetching bookings by car ID and status: " + e.getMessage());
            }
            return bookings;
        }
     public List<Booking> getBookingsByCustomerIdAndDriverId(int customerId, int driverId) {
            List<Booking> bookings = new ArrayList<>();
            String sql = "SELECT * FROM bookings WHERE CustomerID = ? AND DriverID = ?";
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                statement.setInt(2, driverId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    bookings.add(createBookingFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                System.err.println("Error fetching bookings by customer ID and driver ID: " + e.getMessage());
            }
            return bookings;
        }
     public List<Booking> getBookingsByCustomerIdAndCarIdAndDriverId(int customerId, int carId, int driverId) {
            List<Booking> bookings = new ArrayList<>();
            String sql = "SELECT * FROM bookings WHERE CustomerID = ? AND CarID = ? AND DriverID = ?";
            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                statement.setInt(2, carId);
                statement.setInt(3, driverId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    bookings.add(createBookingFromResultSet(resultSet));
                }
            } catch (SQLException e) {
                System.err.println("Error fetching bookings by customer ID, car ID, and driver ID: " + e.getMessage());
            }
            return bookings;
        }
     private Booking createBookingFromResultSet(ResultSet resultSet) throws SQLException {

            return new Booking(
                    resultSet.getInt("BookingID"),
                    resultSet.getInt("CustomerID"),
                    resultSet.getString("PickupLocation"),
                    resultSet.getString("DropLocation"),
                    resultSet.getDouble("Price"),
                    resultSet.getDouble("Discount"),
                    resultSet.getDouble("Tax"),
                    resultSet.getTimestamp("BookingDate"),
                    Status.fromString(resultSet.getString("Status")),
                    resultSet.getInt("CarID"),
                    resultSet.getInt("DriverID"),
                    resultSet.getDouble("Distance")
                    
            );
        }



}