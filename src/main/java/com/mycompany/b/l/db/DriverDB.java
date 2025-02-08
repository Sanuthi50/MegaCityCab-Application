package com.mycompany.b.l.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverDB {
    private static final Logger logger = Logger.getLogger(DriverDB.class.getName());
    private static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycab";
    private static final String USER = "root";
    private static final String PASS = "Admin";
    static final String INSERT_QUERY = "INSERT INTO drivers (Name, NIC, LicenseNumber, LicenseExpiryDate, PhoneNumber, Address, Email, DateOfBirth, Gender, Availability, YearsOfExperience, Rating, LastTripDate, EmergencyContact, Salary, AssignedCarID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query ="SELECT * FROM megacitycab.drivers";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                drivers.add(mapResultSetToDriver(rs));
            }
            logger.info("Fetched " + drivers.size() + " drivers from DB");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching drivers", e);
        }
        return drivers;
    }

    public Driver getDriverById(int driverID) {
        String query = "SELECT * FROM drivers WHERE DriverID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, driverID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDriver(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching driver by ID", e);
        }
        return null;
    }

  public boolean addDriver(Driver driver) {
    // Validate that the driver's name is not null or empty
    if (driver.getName() == null || driver.getName().trim().isEmpty()) {
        System.out.println("Error: Driver name is missing.");
        return false; // Prevent insertion if Name is missing
    }
    
    System.out.println("Inserting driver with name: " + driver.getName()); // Log name for debugging
    
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY)) {

        // Set values for each parameter in the SQL query
        pstmt.setString(1, driver.getName());
        pstmt.setString(2, driver.getNic());
        pstmt.setString(3, driver.getLicenseNumber());
        
        // Handle LicenseExpiryDate (Check for null)
        if (driver.getLicenseExpiryDate() != null) {
            pstmt.setDate(4, java.sql.Date.valueOf(driver.getLicenseExpiryDate()));
        } else {
            pstmt.setNull(4, java.sql.Types.DATE);  // Set NULL if the date is null
        }

        pstmt.setString(5, driver.getPhoneNumber());
        pstmt.setString(6, driver.getAddress());
        pstmt.setString(7, driver.getEmail());

        // Handle DateOfBirth (Check for null)
        if (driver.getDateOfBirth() != null) {
            pstmt.setDate(8, java.sql.Date.valueOf(driver.getDateOfBirth()));
        } else {
            pstmt.setNull(8, java.sql.Types.DATE);  // Set NULL if the date is null
        }

        pstmt.setString(9, driver.getGender() != null ? driver.getGender().toString() : null);
        pstmt.setBoolean(10, driver.isAvailability());
        pstmt.setInt(11, driver.getYearsOfExperience());
        pstmt.setDouble(12, driver.getRating());
        pstmt.setDate(13, driver.getLastTripDate() != null ? java.sql.Date.valueOf(driver.getLastTripDate()) : null);
        pstmt.setString(14, driver.getEmergencyContact());
        pstmt.setDouble(15, driver.getSalary());
        pstmt.setInt(16, driver.getAssignedCarID());

        // Execute the update query and return whether the insertion was successful
        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
        logger.log(Level.SEVERE, "Error adding new driver", e);
        return false;
    }
}

    public boolean updateDriver(Driver driver) {
        String query = "UPDATE drivers SET Name = ?, NIC = ?, LicenseNumber = ?, LicenseExpiryDate = ?, PhoneNumber = ?, Address = ?, Email = ?, DateOfBirth = ?, Gender = ?, Availability = ?, YearsOfExperience = ?, Rating = ?, LastTripDate = ?, EmergencyContact = ?, Salary = ?, AssignedCarID = ? WHERE DriverID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            setDriverParams(pstmt, driver, true);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating driver", e);
            return false;
        }
    }

    public boolean deleteDriver(int driverID) {
        String query = "DELETE FROM drivers WHERE DriverID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, driverID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting driver", e);
            return false;
        }
    }

    private Driver mapResultSetToDriver(ResultSet rs) throws SQLException {
        return new Driver(
                rs.getInt("DriverID"),
                rs.getString("Name"),
                rs.getString("NIC"),
                rs.getString("LicenseNumber"),
                rs.getDate("LicenseExpiryDate") != null ? rs.getDate("LicenseExpiryDate").toLocalDate() : null,
                rs.getString("PhoneNumber"),
                rs.getString("Address"),
                rs.getString("Email"),
                rs.getDate("DateOfBirth") != null ? rs.getDate("DateOfBirth").toLocalDate() : null,
                Driver.Gender.valueOf(rs.getString("Gender")),
                rs.getInt("AssignedCarID"),
                rs.getBoolean("Availability"),
                rs.getInt("YearsOfExperience"),
                rs.getDouble("Rating"),
                rs.getDate("LastTripDate") != null ? rs.getDate("LastTripDate").toLocalDate() : null,
                rs.getString("EmergencyContact"),
                rs.getDouble("Salary")
        );
    }

    private void setDriverParams(PreparedStatement pstmt, Driver driver, boolean includeId) throws SQLException {
        pstmt.setString(1, driver.getName());
        pstmt.setString(2, driver.getNic());
        pstmt.setString(3, driver.getLicenseNumber());
        pstmt.setDate(4, driver.getLicenseExpiryDate() != null ? Date.valueOf(driver.getLicenseExpiryDate()) : null);
        pstmt.setString(5, driver.getPhoneNumber());
        pstmt.setString(6, driver.getAddress());
        pstmt.setString(7, driver.getEmail());
        pstmt.setDate(8, driver.getDateOfBirth() != null ? Date.valueOf(driver.getDateOfBirth()) : null);
        pstmt.setString(9, driver.getGender() != null ? driver.getGender().toString() : null);
        pstmt.setBoolean(10, driver.isAvailability());
        pstmt.setInt(11, driver.getYearsOfExperience());
        pstmt.setDouble(12, driver.getRating());
        pstmt.setDate(13, driver.getLastTripDate() != null ? Date.valueOf(driver.getLastTripDate()) : null);
        pstmt.setString(14, driver.getEmergencyContact());
        pstmt.setDouble(15, driver.getSalary());
        pstmt.setInt(16, driver.getAssignedCarID());

        if (includeId) {
            pstmt.setInt(17, driver.getDriverID());
        }
    }
}
