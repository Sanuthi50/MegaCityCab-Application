package com.mycompany.b.l.db;

import com.mycompany.b.l.db.Car.FuelType;
import com.mycompany.b.l.db.Car.Status;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarDB {

    private static final Logger LOG = Logger.getLogger(CarDB.class.getName());

    // Use the Singleton DatabaseConnection instance
    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();

    public void addCar(int carID, String licensePlate, String model, String make, int year, int capacity, 
                       String fuelType, boolean availability, LocalDate lastServiceDate, 
                       LocalDate insuranceExpiryDate, double mileage, String status, int driverID,double price,Double discount,Double tax) {
        String query = "INSERT INTO cars (CarID, LicensePlate, Model, Make, Year, Capacity, FuelType, " +
                       "Availability, LastServiceDate, InsuranceExpiryDate, Mileage, Status, DriverID,Price,Discount,Tax) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
        
        try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, carID);
            pstmt.setString(2, licensePlate);
            pstmt.setString(3, model);
            pstmt.setString(4, make);
            pstmt.setInt(5, year);
            pstmt.setInt(6, capacity);
            pstmt.setString(7, fuelType);
            pstmt.setBoolean(8, availability);
            pstmt.setObject(9, lastServiceDate);
            pstmt.setObject(10, insuranceExpiryDate);
            pstmt.setDouble(11, mileage);
            pstmt.setString(12, status);
            pstmt.setInt(13, driverID);
            pstmt.setDouble(14, price);
            pstmt.setDouble(15, discount);
            pstmt.setDouble(16, tax);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error adding car: " + e.getMessage(), e);
        }
    }

    public Car getCarById(int carID) {
        String query = "SELECT * FROM cars WHERE CarID = ?";
        Car car = null;
        
        try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, carID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                car = new Car(
                    rs.getInt("CarID"),
                    rs.getString("LicensePlate"),
                    rs.getString("Model"),
                    rs.getString("Make"),
                    rs.getInt("Year"),
                    rs.getInt("Capacity"),
                    FuelType.fromDbValue(rs.getString("FuelType")),
                    rs.getBoolean("Availability"),
                    rs.getDate("LastServiceDate").toLocalDate(),
                    rs.getDate("InsuranceExpiryDate").toLocalDate(),
                    rs.getBigDecimal("Mileage"),
                    Status.fromDbValue(rs.getString("Status")),
                    rs.getInt("DriverID"),
                    rs.getDouble("Price"),
                    rs.getDouble("Discount"),
                    rs.getDouble("Tax")
                );
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error getting car by ID: " + e.getMessage(), e);
        }
        
        return car;
    }

    public List<Car> getAllCars() {
        String query = "SELECT * FROM cars";
        List<Car> carList = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                LocalDate lastServiceDate = (rs.getDate("LastServiceDate") != null) ? rs.getDate("LastServiceDate").toLocalDate() : null;
                LocalDate insuranceExpiryDate = (rs.getDate("InsuranceExpiryDate") != null) ? rs.getDate("InsuranceExpiryDate").toLocalDate() : null;
                BigDecimal mileage = (rs.getBigDecimal("Mileage") != null) ? rs.getBigDecimal("Mileage") : BigDecimal.ZERO;
                Integer driverID = rs.getObject("DriverID", Integer.class);
                double price = rs.getDouble("Price");
                if (rs.wasNull()) {
                    price = 0.0;
                }
                  double discount = rs.getDouble("Discount");
                if (rs.wasNull()) {
                    discount = 0.0;
                }
                  double tax = rs.getDouble("Tax");
                if (rs.wasNull()) {
                    tax = 0.0;
                }

                FuelType fuelType = (rs.getString("FuelType") != null) ? FuelType.fromDbValue(rs.getString("FuelType")) : FuelType.Petrol;
                Status status = (rs.getString("Status") != null) ? Status.fromDbValue(rs.getString("Status")) : Status.AVAILABLE;

                carList.add(new Car(
                    rs.getInt("CarID"),
                    rs.getString("LicensePlate"),
                    rs.getString("Model"),
                    rs.getString("Make"),
                    rs.getInt("Year"),
                    rs.getInt("Capacity"),
                    fuelType,
                    rs.getBoolean("Availability"),
                    lastServiceDate,
                    insuranceExpiryDate,
                    mileage,
                    status,
                    driverID,
                    price,
                    discount,
                    tax
                ));
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error getting all cars: " + e.getMessage(), e);
            throw new RuntimeException("Error getting all cars", e);
        }
        return carList;
    }

   public void updateCar(Integer carID, String licensePlate, String model, String make, Integer year, Integer capacity, 
                      String fuelType, Boolean availability, LocalDate lastServiceDate, 
                      LocalDate insuranceExpiryDate, Double mileage, String status, Integer driverID, 
                      Double price, Double discount, Double tax) { // Use Wrapper types
    StringBuilder query = new StringBuilder("UPDATE cars SET ");
    List<Object> parameters = new ArrayList<>();
    boolean hasFieldsToUpdate = false;

    if (licensePlate != null) { query.append("LicensePlate = ?, "); parameters.add(licensePlate); hasFieldsToUpdate = true; }
    if (model != null) { query.append("Model = ?, "); parameters.add(model); hasFieldsToUpdate = true; }
    if (make != null) { query.append("Make = ?, "); parameters.add(make); hasFieldsToUpdate = true; }
    if (year != null) { query.append("Year = ?, "); parameters.add(year); hasFieldsToUpdate = true; }
    if (capacity != null) { query.append("Capacity = ?, "); parameters.add(capacity); hasFieldsToUpdate = true; }
    if (fuelType != null) { query.append("FuelType = ?, "); parameters.add(fuelType); hasFieldsToUpdate = true; }
    if (availability != null) { query.append("Availability = ?, "); parameters.add(availability); hasFieldsToUpdate = true; }
    if (lastServiceDate != null) { query.append("LastServiceDate = ?, "); parameters.add(lastServiceDate); hasFieldsToUpdate = true; }
    if (insuranceExpiryDate != null) { query.append("InsuranceExpiryDate = ?, "); parameters.add(insuranceExpiryDate); hasFieldsToUpdate = true; }
    if (mileage != null) { query.append("Mileage = ?, "); parameters.add(mileage); hasFieldsToUpdate = true; }
    if (status != null) { query.append("Status = ?, "); parameters.add(status); hasFieldsToUpdate = true; }
    if (driverID != null) { query.append("DriverID = ?, "); parameters.add(driverID); hasFieldsToUpdate = true; }
    if (price != null) { query.append("Price = ?, "); parameters.add(price); hasFieldsToUpdate = true; }
    if (discount != null) { query.append("Discount = ?, "); parameters.add(discount); hasFieldsToUpdate = true; }
    if (tax != null) { query.append("Tax = ?, "); parameters.add(tax); hasFieldsToUpdate = true; }

    if (!hasFieldsToUpdate) {
        LOG.warning("No fields provided for update.");
        return;
    }

    query.setLength(query.length() - 2); // Remove trailing ", "
    query.append(" WHERE CarID = ?");
    parameters.add(carID);

    try (Connection conn = dbConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

        for (int i = 0; i < parameters.size(); i++) {
            pstmt.setObject(i + 1, parameters.get(i));
        }

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            LOG.info("Car updated successfully.");
        } else {
            LOG.warning("No car found with CarID: " + carID);
        }
    } catch (SQLException e) {
        LOG.log(Level.SEVERE, "Error updating car: " + e.getMessage(), e);
    }
}

    public void deleteCar(int carID) {
        String query = "DELETE FROM cars WHERE CarID = ?";
        
        try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, carID);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error deleting car: " + e.getMessage(), e);
        }
    }

    public Car searchCar(String licensePlate) {
        String query = "SELECT * FROM cars WHERE LicensePlate = ?";
        Car foundCar = null;

        try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                foundCar = new Car(
                    rs.getInt("CarID"),
                    rs.getString("LicensePlate"),
                    rs.getString("Model"),
                    rs.getString("Make"),
                    rs.getInt("Year"),
                    rs.getInt("Capacity"),
                    FuelType.fromDbValue(rs.getString("FuelType")),
                    rs.getBoolean("Availability"),
                    rs.getDate("LastServiceDate").toLocalDate(),
                    rs.getDate("InsuranceExpiryDate").toLocalDate(),
                    rs.getBigDecimal("Mileage"),
                    Status.fromDbValue(rs.getString("Status")),
                    rs.getInt("DriverID"),
                    rs.getDouble("Price"),
                    rs.getDouble("Discount"),
                    rs.getDouble("Tax")
                );
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error searching car: " + e.getMessage(), e);
        }
        return foundCar;
    }
       public List<Car> FetchAvailableCars() {
    String query = "SELECT * FROM cars WHERE Status = 'Available'";
    List<Car> availableCars = new ArrayList<>();

    try (Connection conn = dbConnection.getConnection(); // Use Singleton connection
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            // Handle potential null values for dates and other fields
            LocalDate lastServiceDate = rs.getDate("LastServiceDate") != null 
                ? rs.getDate("LastServiceDate").toLocalDate() 
                : null;
            LocalDate insuranceExpiryDate = rs.getDate("InsuranceExpiryDate") != null 
                ? rs.getDate("InsuranceExpiryDate").toLocalDate() 
                : null;

            Car foundCar = new Car(
                rs.getInt("CarID"),
                rs.getString("LicensePlate"),
                rs.getString("Model"),
                rs.getString("Make"),
                rs.getInt("Year"),
                rs.getInt("Capacity"),
                FuelType.fromDbValue(rs.getString("FuelType")),
                rs.getBoolean("Availability"),
                lastServiceDate,
                insuranceExpiryDate,
                rs.getBigDecimal("Mileage") != null ? rs.getBigDecimal("Mileage") : BigDecimal.ZERO,
                Status.fromDbValue(rs.getString("Status")),
                rs.getInt("DriverID"),
                rs.getDouble("Price"),
                rs.getDouble("Discount"),
                rs.getDouble("Tax")
            );
            availableCars.add(foundCar);
        }
    } catch (SQLException e) {
        LOG.log(Level.SEVERE, "Error fetching available cars: " + e.getMessage(), e);
        // Optionally, rethrow as a custom exception or return an empty list
    }
    return availableCars;
}}
