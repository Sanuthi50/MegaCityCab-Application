package com.mycompany.b.l.db;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for CarDB class.
 */
public class CarDBTest {
    
    private static final Logger LOGGER = Logger.getLogger(CarDBTest.class.getName());
    private CarDB instance;
    
    @BeforeClass
    public static void setUpClass() {
        LOGGER.info("Starting CarDB Tests...");
    }
    
    @AfterClass
    public static void tearDownClass() {
        LOGGER.info("Finished CarDB Tests.");
    }
    
    @Before
    public void setUp() {
        LOGGER.fine("Setting up test instance");
        instance = new CarDB();
    }
    
    @After
    public void tearDown() {
        LOGGER.fine("Tearing down test instance");
        instance = null;
    }

     @Test
    public void testAddCar() {
        LOGGER.info("Testing addCar method");
       instance.addCar(0,"QCN762","Civic","Honda",2022,5,"Petrol",true,LocalDate.now(),LocalDate.now().plusYears(1),15000,"Available",12);
        Car result = instance.searchCar("QCN762");
        LOGGER.log(Level.FINE, "Added car with license: {0}", "QCN762");
        assertNotNull("Car should be added and found in database", result);
        if (result != null) {
            LOGGER.log(Level.FINE, "Found car: {0} {1}", new Object[]{result.getMake(), result.getModel()});
        }
    }



  @Test
    public void testUpdateCar() {
    LOGGER.info("Starting testUpdateCar method for carID: 88");

    // Log the initial state of the car before update
    Car initialCar = instance.getCarById(88);
    if (initialCar != null) {
        LOGGER.info("Initial car state before update: " + initialCar.toString());
    } else {
        LOGGER.info("Car with carID: 88 not found before update.");
    }

    // Update the car with carID = 88
    LOGGER.info("Updating car with carID: 88");
    instance.updateCar(
        88,                  // carID (int)
        "PUL73",          // licensePlate (String)
        "Civic",            // model (String)
        "Jeep",            // make (String)
        null,               // year (Integer)
        null,               // capacity (Integer)
        null,           // fuelType (String)
        null,               // availability (Boolean)
        LocalDate.now(),    // lastServiceDate (LocalDate)
        LocalDate.now().plusYears(1), // insuranceExpiryDate (LocalDate)
        null,               // mileage (Double)
        null,               // status (String)
        null                // driverID (Integer)
    );

    // Retrieve the updated car
    LOGGER.info("Retrieving updated car with carID: 88");
    Car updatedCar = instance.getCarById(88);

    // Log the updated state of the car
    if (updatedCar != null) {
        LOGGER.info("Updated car state: " + updatedCar.toString());
    } else {
        LOGGER.info("Car with carID: 88 not found after update.");
    }

    // Assertions to verify the update
    LOGGER.info("Verifying update assertions for carID: 88");
    assertNotNull(updatedCar); // Ensure the car exists
    assertEquals("Jeep", updatedCar.getMake());
    assertEquals("Civic", updatedCar.getModel());
    assertEquals("PUL73", updatedCar.getLicensePlate());
    
    assertEquals(LocalDate.now(), updatedCar.getLastServiceDate());
    assertEquals(LocalDate.now().plusYears(1), updatedCar.getInsuranceExpiryDate());

    LOGGER.info("Update test passed for carID: 88");
}

    @Test
    public void testDeleteCar() {
        LOGGER.info("Testing deleteCar method");
        instance.deleteCar(104);
        Car deletedCar = instance.getCarById(104);
        assertNull("Car should be deleted", deletedCar);
    }

    @Test
    public void testSearchCar() {
        LOGGER.info("Testing searchCar method");
        Car foundCar = instance.searchCar("JKL1001");
        assertNotNull("Car should be found", foundCar);
    }

    @Test
    public void testGetCarById() {
        LOGGER.info("Testing getCarById method");
        Car retrievedCar = instance.getCarById(84);
        assertNotNull("Car should be retrieved", retrievedCar);
    }

    @Test
public void testGetAllCars() {
    LOGGER.info("Starting testGetAllCars method");

    // Ensure instance is initialized
    LOGGER.info("Checking if CarDB instance is initialized");
    assertNotNull("CarDB instance should not be null", instance);
    LOGGER.info("CarDB instance is initialized successfully");

    // Retrieve the list of cars
    LOGGER.info("Retrieving list of cars from the database");
    List<Car> cars = instance.getAllCars();

    // Log the number of cars retrieved
    if (cars != null) {
        LOGGER.info("Number of cars retrieved: " + cars.size());
        if (cars.size() > 0) {
            LOGGER.info("First car in the list: " + cars.get(0).toString()); // Log details of the first car
        } else {
            LOGGER.info("Retrieved car list is empty");
        }
    } else {
        LOGGER.info("Retrieved car list is null");
    }

    // Assertions
    LOGGER.info("Performing assertions on the retrieved car list");
    assertNotNull("Car list should not be null", cars);
    assertTrue("Car list should not be empty", cars.size() > 0);

    LOGGER.info("testGetAllCars method completed successfully");
}
}
