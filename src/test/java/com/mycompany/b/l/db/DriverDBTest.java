/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.b.l.db;

import com.mycompany.b.l.db.DriverDB;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class DriverDBTest {
    
public DriverDBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllDrivers method, of class DriverDB.
     */
   @Test
public void testGetAllDrivers() {
    System.out.println("getAllDrivers");
    DriverDB instance = new DriverDB();

    List<Driver> result = instance.getAllDrivers();

    // Assert the result is not null
    assertNotNull("List of drivers should not be null", result);

    // Print each driver using the toString() method
    for (Driver driver : result) {
        System.out.println(driver);  // This will automatically call driver.toString()
    }
}


    /**
     * Test of getDriverById method, of class DriverDB.
     */
    @Test
    public void testGetDriverById() {
        System.out.println("getDriverById");

        int driverID = 1; // Replace with actual ID if needed
        DriverDB instance = new DriverDB();

        Driver result = instance.getDriverById(driverID);

        assertNotNull("Driver should be found", result);
    }

    /**
     * Test of deleteDriver method, of class DriverDB.
     
    @Test
    public void testDeleteDriver() {
        System.out.println("deleteDriver");

        int driverID = 21; // valid ID from database

        DriverDB instance = new DriverDB();
        boolean result = instance.deleteDriver(driverID);

        assertTrue("Driver should be deleted successfully", result);
    }

    /**
     * Test of addDriver method, of class DriverDB.
     
     @Test
    public void testAddDriver() {
    System.out.println("addDriver");

    // Driver object creation without the driverID (since it's auto-incremented)
    Driver driver = new Driver(
        0, // Driver ID (auto-incremented)
        "Taylor Swift", // Name
        "19222399V", // NIC
        "C21344", // License Number
        LocalDate.of(2028, 12, 20), // License expiry date
        "0765678765", // Phone Number
        "1288, Main2 St,usa", // Address
        "Taylor@gmail.com", // Email
        LocalDate.of(1985, 8, 15), // Date of Birth
        Driver.Gender.Male, // Gender
        8, // Assigned Car ID
        true, // Availability
        5, // Years of Experience
        4.50, // Rating
        LocalDate.of(2025, 2, 1), // Last Trip Date
        "0712987645679", // Emergency contact
        320000.0 // Salary
    );
    

    // Log the driver's name to make sure it's correctly set
    System.out.println("Driver name from test: " + driver.getName()); // Verify the name is not null or empty

    DriverDB instance = new DriverDB();
    boolean result = instance.addDriver(driver);

    // Assert that the driver is added successfully
    assertTrue("Driver should be added successfully", result);
}

    /**
     * Test of updateDriver method, of class DriverDB.
     */
    @Test
    public void testUpdateDriver() {
        System.out.println("updateDriver");
        
        DriverDB driverDB = new DriverDB();  
        
        int driverID = 4;  // Valid driver ID
        
        // Corrected Driver object creation
        Driver driver = new Driver(
             "Updated Name", 
            "665555V", 
            "AB1234567", 
            LocalDate.of(2025, 12, 31), 
            "077736366726", 
            "123 New Street, City", 
            "updatedemail@example.com", 
            LocalDate.of(1985, 5, 12),  
            Driver.Gender.Male,
            true, driverID, 
            4.50, 
            LocalDate.of(2025, 1, 25), 
            "911", 
            50000.0,
                8
        );

        // Updating driver details
        driver.setDriverID(driverID);
        driver.setName("Updated Name");
        driver.setNic("665555V");
        driver.setLicenseNumber("AB1234567");
        driver.setLicenseExpiryDate(LocalDate.of(2025, 12, 31));
        driver.setPhoneNumber("077736366726");
        driver.setAddress("123 New Street, City");
        driver.setEmail("updatedemail@example.com");
        driver.setDateOfBirth(LocalDate.of(1985, 5, 12));
        driver.setGender(Driver.Gender.Male);
        driver.setAvailability(true);
        driver.setYearsOfExperience(10);
        driver.setRating(4.5);
        driver.setLastTripDate(LocalDate.of(2025, 1, 25));
        driver.setEmergencyContact("911");
        driver.setSalary(50000.00);
        driver.setAssignedCarID(8);

        // Perform update
        boolean result = driverDB.updateDriver(driver);
        assertTrue("Driver update failed", result);

        // Fetch the updated driver
        Driver updatedDriver = driverDB.getDriverById(driverID);
        assertNotNull("Updated driver not found", updatedDriver);

        // Verify updated fields
        assertEquals("Updated Name", updatedDriver.getName());
        assertEquals("665555V", updatedDriver.getNic());
        assertEquals("AB1234567", updatedDriver.getLicenseNumber());
        assertEquals(LocalDate.of(2025, 12, 31), updatedDriver.getLicenseExpiryDate());
        assertEquals("077736366726", updatedDriver.getPhoneNumber());
        assertEquals("123 New Street, City", updatedDriver.getAddress());
        assertEquals("updatedemail@example.com", updatedDriver.getEmail());
        assertEquals(LocalDate.of(1985, 5, 12), updatedDriver.getDateOfBirth());
        assertEquals(Driver.Gender.Male, updatedDriver.getGender());
        assertTrue(updatedDriver.isAvailability());
        assertEquals(10, updatedDriver.getYearsOfExperience());
        assertEquals(4.5, updatedDriver.getRating(), 0.01);
        assertEquals(LocalDate.of(2025, 1, 25), updatedDriver.getLastTripDate());
        assertEquals("911", updatedDriver.getEmergencyContact());
        assertEquals(50000.00, updatedDriver.getSalary(), 0.01);
        assertEquals(8, updatedDriver.getAssignedCarID());
    }
    
}
