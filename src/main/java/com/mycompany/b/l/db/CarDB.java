/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author User
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarDB {
       // Shared database connection details
    static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycab";
    static final String USER = "root";
    static final String PASS = "Admin";

    // SQL queries
    static final String SELECT_ALL_QUERY = "SELECT * FROM Cars";
    static final String SELECT_BY_ID_QUERY = "SELECT * FROM Cars WHERE CarID = ?";
    static final String INSERT_QUERY = "INSERT INTO Cars (CarID, LicensePlate, Model, Make, Year, Capacity, FuelType, Availability, DriverID, LastServiceDate, InsuranceExpiryDate, Mileage, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static final String UPDATE_QUERY = "UPDATE Cars SET LicensePlate = ?, Model = ?, Make = ?, Year = ?, Capacity = ?, FuelType = ?, Availability = ?, DriverID = ?, LastServiceDate = ?, InsuranceExpiryDate = ?, Mileage = ?, Status = ? WHERE CarID = ?";
    static final String DELETE_QUERY = "DELETE FROM Cars WHERE CarID = ?";

    // Utility method to create a connection to the database
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Method to get all cars
    public List<Cars> getAllCars() {
        List<Cars> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY)) {

            while (rs.next()) {
                cars.add(new Cars(
                        UUID.fromString(rs.getString("CarID")),  // Convert the String to UUID
                        rs.getString("LicensePlate"),
                        rs.getString("Model"),
                        rs.getString("Make"),
                        rs.getInt("Year"),
                        rs.getInt("Capacity"),
                        Cars.FuelType.valueOf(rs.getString("FuelType").toUpperCase()),  // Enum conversion
                        rs.getBoolean("Availability"),
                        rs.getString("DriverID") != null ? UUID.fromString(rs.getString("DriverID")) : null,  // Nullable foreign key
                        rs.getDate("LastServiceDate").toLocalDate(),
                        rs.getDate("InsuranceExpiryDate").toLocalDate(),
                        rs.getDouble("Mileage"),
                        Cars.Status.valueOf(rs.getString("Status").toUpperCase())  // Enum conversion
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // Method to get a car by ID
    public Cars getCarById(String carID) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_QUERY)) {

            pstmt.setString(1, carID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Cars(
                            UUID.fromString(rs.getString("CarID")),  // Convert the String to UUID
                            rs.getString("LicensePlate"),
                            rs.getString("Model"),
                            rs.getString("Make"),
                            rs.getInt("Year"),
                            rs.getInt("Capacity"),
                            Cars.FuelType.valueOf(rs.getString("FuelType").toUpperCase()),  // Enum conversion
                            rs.getBoolean("Availability"),
                            rs.getString("DriverID") != null ? UUID.fromString(rs.getString("DriverID")) : null,  // Nullable foreign key
                            rs.getDate("LastServiceDate").toLocalDate(),
                            rs.getDate("InsuranceExpiryDate").toLocalDate(),
                            rs.getDouble("Mileage"),
                            Cars.Status.valueOf(rs.getString("Status").toUpperCase())  // Enum conversion
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no car is found
    }

    // Method to add a new car
    public boolean addCar(Cars car) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY)) {

            pstmt.setString(1, car.getCarID().toString()); // Using UUID
            pstmt.setString(2, car.getLicensePlate());
            pstmt.setString(3, car.getModel());
            pstmt.setString(4, car.getMake());
            pstmt.setInt(5, car.getYear());
            pstmt.setInt(6, car.getCapacity());
            pstmt.setString(7, car.getFuelType().toString()); // Enum conversion
            pstmt.setBoolean(8, car.isAvailability());
            pstmt.setString(9, car.getDriverID() != null ? car.getDriverID().toString() : null); // Nullable foreign key
            pstmt.setDate(10, Date.valueOf(car.getLastServiceDate()));
            pstmt.setDate(11, Date.valueOf(car.getInsuranceExpiryDate()));
            pstmt.setDouble(12, car.getMileage());
            pstmt.setString(13, car.getStatus().toString()); // Enum conversion

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update a car
    public boolean updateCar(Cars car) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, car.getLicensePlate());
            pstmt.setString(2, car.getModel());
            pstmt.setString(3, car.getMake());
            pstmt.setInt(4, car.getYear());
            pstmt.setInt(5, car.getCapacity());
            pstmt.setString(6, car.getFuelType().toString()); // Enum conversion
            pstmt.setBoolean(7, car.isAvailability());
            pstmt.setString(8, car.getDriverID() != null ? car.getDriverID().toString() : null); // Nullable foreign key
            pstmt.setDate(9, Date.valueOf(car.getLastServiceDate()));
            pstmt.setDate(10, Date.valueOf(car.getInsuranceExpiryDate()));
            pstmt.setDouble(11, car.getMileage());
            pstmt.setString(12, car.getStatus().toString()); // Enum conversion
            pstmt.setString(13, car.getCarID().toString()); // Using UUID

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a car by ID
    public boolean deleteCar(String carID) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY)) {

            pstmt.setString(1, carID);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
    

