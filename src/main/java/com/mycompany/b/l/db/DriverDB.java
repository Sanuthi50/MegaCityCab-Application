package com.mycompany.b.l.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverDB {
    private static final Logger logger = Logger.getLogger(DriverDB.class.getName());
    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    static final String INSERT_QUERY = "INSERT INTO drivers (Name, NIC, LicenseNumber, LicenseExpiryDate, PhoneNumber, Address, Email, DateOfBirth, Gender, Availability, YearsOfExperience, Rating, LastTripDate, EmergencyContact, Salary, AssignedCarID, Username, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Singleton instance of DriverDB
    private static DriverDB instance;

    // Private constructor to prevent instantiation
    public DriverDB() {}

    // Public method to get the single instance of DriverDB
    public static DriverDB getInstance() {
        if (instance == null) {
            synchronized (DriverDB.class) {
                if (instance == null) {
                    instance = new DriverDB();
                }
            }
        }
        return instance;
    }
     public Driver getDriverByCarId(int carID) {
        String query = "SELECT * FROM drivers WHERE AssignedCarID = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, carID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDriver(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching driver by car ID: " + carID, e);
        }
        return null;
    }

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM megacitycab.drivers";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                drivers.add(mapResultSetToDriver(rs));
            }
            logger.info("Fetched " + drivers.size() + " drivers from DB");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching drivers: " + e.getMessage(), e);
        }
        return drivers;
    }

    public Driver getDriverById(int driverID) {
        String query = "SELECT * FROM drivers WHERE DriverID = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, driverID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDriver(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching driver by ID: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean addDriver(Driver driver) {
        if (driver.getName() == null || driver.getName().trim().isEmpty()) {
            logger.warning("Driver name is missing. Cannot add driver.");
            return false;
        }

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY)) {

            setDriverParams(pstmt, driver, false);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Driver added successfully: " + driver.getName());
                return true;
            } else {
                logger.warning("Failed to add driver: " + driver.getName());
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding driver: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateDriver(Driver driver) {
        String query = "UPDATE drivers SET Name = ?, NIC = ?, LicenseNumber = ?, "
                + "LicenseExpiryDate = ?, PhoneNumber = ?, Address = ?, Email = ?, DateOfBirth = ?, Gender = ?, "
                + "Availability = ?, YearsOfExperience = ?, Rating = ?, LastTripDate = ?, EmergencyContact = ?,"
                + " Salary = ?, AssignedCarID = ?, Username = ?, Password = ? WHERE DriverID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the PreparedStatement
            setDriverParams(pstmt, driver, true);

            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Driver updated successfully: " + driver.getDriverID());
                return true;
            } else {
                logger.warning("Failed to update driver: " + driver.getDriverID());
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating driver: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean deleteDriver(int driverID) {
        String query = "DELETE FROM drivers WHERE DriverID = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, driverID);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info("Driver deleted successfully: " + driverID);
                return true;
            } else {
                logger.warning("Failed to delete driver: " + driverID);
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting driver: " + e.getMessage(), e);
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
                rs.getString("Gender") != null ? Driver.Gender.valueOf(rs.getString("Gender")) : null,
                rs.getBoolean("Availability"),
                rs.getInt("YearsOfExperience"),
                rs.getDouble("Rating"),
                rs.getDate("LastTripDate") != null ? rs.getDate("LastTripDate").toLocalDate() : null,
                rs.getString("EmergencyContact"),
                rs.getDouble("Salary"),
                rs.getObject("AssignedCarID", Integer.class),
                rs.getString("Username"),
                rs.getString("Password")
        );
    }

    private void setDriverParams(PreparedStatement pstmt, Driver driver, boolean includeId) throws SQLException {
        // Check for null or empty values in username and password
        if (driver.getUsername() == null || driver.getUsername().trim().isEmpty() ||
            driver.getPassword() == null || driver.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be null or empty.");
        }

        // Set parameters for the PreparedStatement
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
        pstmt.setObject(16, driver.getAssignedCarID());
        pstmt.setString(17, driver.getUsername());
        pstmt.setString(18, driver.getPassword());

        // If ID should be included, assign it at index 19
        if (includeId) {
            pstmt.setInt(19, driver.getDriverID());
        }
    }
    public Driver authenticateDriver(String username, String password) {
    Driver driver = null;
    String sql = "SELECT * FROM drivers WHERE Username = ? AND Password = ?";
    
    try (Connection conn = dbConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
         
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            driver = new Driver();
            driver.setDriverID(rs.getInt("DriverID"));
            driver.setUsername(rs.getString("Username"));
            driver.setPassword(rs.getString("Password"));
            driver.setName(rs.getString("Name"));
            driver.setNic(rs.getString("NIC"));
            driver.setLicenseNumber(rs.getString("LicenseNumber"));
            driver.setLicenseExpiryDate(rs.getDate("LicenseExpiryDate") != null ? rs.getDate("LicenseExpiryDate").toLocalDate() : null);
            driver.setPhoneNumber(rs.getString("PhoneNumber"));
            driver.setAddress(rs.getString("Address"));
            driver.setEmail(rs.getString("Email"));
            driver.setDateOfBirth(rs.getDate("DateOfBirth") != null ? rs.getDate("DateOfBirth").toLocalDate() : null);
            driver.setGender(rs.getString("Gender") != null ? Driver.Gender.valueOf(rs.getString("Gender")) : null);
            driver.setAssignedCarID(rs.getObject("AssignedCarID", Integer.class));
            driver.setAvailability(rs.getBoolean("Availability"));
            driver.setYearsOfExperience(rs.getInt("YearsOfExperience"));
            driver.setRating(rs.getDouble("Rating"));
            driver.setLastTripDate(rs.getDate("LastTripDate") != null ? rs.getDate("LastTripDate").toLocalDate() : null);
            driver.setEmergencyContact(rs.getString("EmergencyContact"));
            driver.setSalary(rs.getDouble("Salary"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
        return driver;
    }

    public List<Driver> getDriversByAvailability(boolean availability) {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers WHERE Availability = ? AND AssignedCarID IS NULL";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setBoolean(1, availability);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    drivers.add(mapResultSetToDriver(rs));
                }
                logger.info("Fetched " + drivers.size() + " drivers with availability: " + availability + " and no assigned car.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching drivers by availability: " + e.getMessage(), e);
        }
        return drivers;
    }
}