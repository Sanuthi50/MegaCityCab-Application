package com.mycompany.b.l.db;

import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DriverDBTest {
    
    public DriverDBTest() {}

    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    @Test
    public void testGetAllDrivers() {
        System.out.println("getAllDrivers");
        DriverDB instance = new DriverDB();

        List<Driver> result = instance.getAllDrivers();
        assertNotNull("List of drivers should not be null", result);

        for (Driver driver : result) {
            System.out.println(driver);
        }
    }

    @Test
    public void testGetDriverById() {
        System.out.println("getDriverById");

        int driverID = 1;
        DriverDB instance = new DriverDB();

        Driver result = instance.getDriverById(driverID);
        assertNotNull("Driver should be found", result);
    }
/*
    @Test
    public void testDeleteDriver() {
        System.out.println("deleteDriver");

        int driverID = 21;
        DriverDB instance = new DriverDB();
        boolean result = instance.deleteDriver(driverID);

        assertTrue("Driver should be deleted successfully", result);
    }

    @Test
    public void testAddDriver() {
        System.out.println("addDriver");

        Driver driver = new Driver(
            0,
            "Taylor Swift",
            "19222399V",
            "C21344",
            LocalDate.of(2028, 12, 20),
            "0765678765",
            "1288, Main2 St, USA",
            "Taylor@gmail.com",
            LocalDate.of(1985, 8, 15),
            Driver.Gender.Female,
            8,
            true,
            5,
            4.50,
            LocalDate.of(2025, 2, 1),
            "0712987645679",
            320000.0
        );

        System.out.println("Driver name from test: " + driver.getName());

        DriverDB instance = new DriverDB();
        boolean result = instance.addDriver(driver);
        assertTrue("Driver should be added successfully", result);
    }
*/
    @Test
    public void testUpdateDriver() {
        System.out.println("updateDriver");
        
        DriverDB driverDB = new DriverDB();  
        int driverID = 4;
        
       Driver driver = new Driver(
            driverID,
            "Updated Name",
            "665555V",
            "AB1234567",
            LocalDate.of(2025, 12, 31),
            "077736366726",
            "123 New Street, City",
            "updatedemail@example.com",
            LocalDate.of(1985, 5, 12),
            Driver.Gender.Male,
            8,
            true,
            10,
            4.5,
            LocalDate.of(2025, 1, 25),
            "911",
            50000.00
        );


        boolean result = driverDB.updateDriver(driver);
        assertTrue("Driver update failed", result);

        Driver updatedDriver = driverDB.getDriverById(driverID);
        assertNotNull("Updated driver not found", updatedDriver);

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
