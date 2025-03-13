package com.mycompany.b.l.db;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DriverDBTest {
     private static final Logger logger = Logger.getLogger(DriverDBTest.class.getName());

    
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
     public void testGetDriverByCarId() {
        System.out.println("getDriverByCarId");
        DriverDB instance = new DriverDB();
        int carID = 78; // Replace with a valid car ID from your test database
        Driver result = instance.getDriverByCarId(carID);
        assertNotNull(result); // Ensure a driver is returned
        assertEquals(Integer.valueOf(carID), result.getAssignedCarID()); // Ensure the driver is assigned to the correct car
    }

    @Test
    public void testGetDriverById() {
        System.out.println("getDriverById");

        int driverID = 5;
        DriverDB instance = new DriverDB();

        Driver result = instance.getDriverById(driverID);
        assertNotNull("Driver should be found", result);
    }

    /*@Test
    public void testDeleteDriver() {
        System.out.println("deleteDriver");

        int driverID = 30;
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
            "19008899V",
            "C90990",
            LocalDate.of(2028, 12, 20),
            "076560075",
            "1288, Main2 St, USA",
            "TaylorSwift@gmail.com",
            LocalDate.of(1985, 8, 15),
            Driver.Gender.Female,
            true,
            5,
            4.50,
            LocalDate.of(2025, 2, 1),
            "07129879",
            32000.00,
            75,
            "taylor_swift",  // username
            "password123"     // password
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
            true,
            10,
            4.5,
            LocalDate.of(2025, 1, 25),
            "911",
            50000.00,
            76,
            "updated_username",  // username
            "updated_password"   // password
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
        assertEquals(Integer.valueOf(76), updatedDriver.getAssignedCarID());
        assertEquals("updated_username", updatedDriver.getUsername());
        assertEquals("updated_password", updatedDriver.getPassword());
    }
       @Test
    public void testGetDriversByAvailability() {
        logger.info("Running testGetDriversByAvailability");

        // Arrange
        DriverDB driverDB = DriverDB.getInstance();

        // Act
        List<Driver> availableDrivers = driverDB.getDriversByAvailability(true);

        // Assert
        assertNotNull(availableDrivers); // Ensure list is not null

        if (availableDrivers.isEmpty()) {
            logger.info("No drivers found with availability=true and AssignedCarID=NULL.");
        } else {
            logger.info("Total drivers found: " + availableDrivers.size());

            for (Driver driver : availableDrivers) {
                // Log driver details for debugging
                logger.info(String.format(
                        "DriverID: %d | Availability: %b | AssignedCarID: %s",
                        driver.getDriverID(),
                        driver.isAvailability(),
                        driver.getAssignedCarID() != null ? driver.getAssignedCarID().toString() : "NULL"
                ));

                // Ensure driver has expected values
                assertTrue(driver.isAvailability());
                assertNull(driver.getAssignedCarID());
            }
        }
    }

         @Test
    public void testAuthenticateDriver_Successful() {
        System.out.println("testAuthenticateDriver_Successful");

        // Arrange
        DriverDB driverdb = new DriverDB();
        String username = "user3"; // Replace with a valid username from your database
        String password = "p3"; // Replace with the corresponding password hash

        // Act
        Driver result = driverdb.authenticateDriver(username, password);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
        // Add more assertions to validate other fields if needed
    }
    

    @Test
    public void testAuthenticateDriver_Failed() {
        System.out.println("testAuthenticateDriver_Failed");

        // Arrange
       DriverDB driverdb = new DriverDB();
        String username = "nonExistentUser"; // Use a username that doesn't exist
        String password = "wrongPasswordHash"; // Use an incorrect password hash

        // Act
        Driver result = driverdb.authenticateDriver(username, password);

        // Assert
        assertNull(result); // Expect null because authentication should fail
    }

}