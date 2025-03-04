package com.mycompany.b.l.db;


import com.mycompany.b.l.db.Booking.Status;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
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
public class BookingDAOTest {

    private BookingDAO bookingDAO;
    private static final Logger LOGGER = Logger.getLogger(CarDBTest.class.getName());

    public BookingDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        // Initialize any resources needed for the entire test class
    }

    @AfterClass
    public static void tearDownClass() {
        // Clean up resources after all tests are done
    }

    @Before
    public void setUp() {
        bookingDAO = new BookingDAO();
        // Add test data to the database if needed
    }

    @After
    public void tearDown() {
        // Clean up the database after each test
        bookingDAO.deleteBooking(1); // Example: Delete a test booking
    }

    /**
     * Test of addBooking method, of class BookingDAO.
     */
      @Test
    public void testAddBooking() {
        System.out.println("addBooking");

        int customerID = 1;
        String pickupLocation = "New York";
        String dropLocation = "Los Angeles";
        double price = 100.0;
        double discount = 10.0;
        double tax = 5.0;
        Timestamp bookingDate = new Timestamp(System.currentTimeMillis());
        Status status = Status.Completed; // Use the enum
        int carID = 86;
        int driverID = 12;

        // Add the booking and get the generated ID
        int bookingId = bookingDAO.addBooking(customerID, pickupLocation, dropLocation, price, discount, tax, bookingDate, status, carID, driverID);
        assertTrue(bookingId > 0); // Ensure the ID is valid

        // Retrieve the booking to verify it was added
        Booking result = bookingDAO.getBookingById(bookingId); 
        assertNotNull(result);
        assertEquals(customerID, result.getCustomerId());
        assertEquals(pickupLocation, result.getPickupLocation());
        assertEquals(dropLocation, result.getDropLocation());
        assertEquals(status, result.getStatus()); // Verify the enum status
    }
    /**
     * Test of getBookingById method, of class BookingDAO.
     */
   @Test
    public void testGetBookingById() {
        System.out.println("getBookingById");

        // Use an existing booking ID from the database
        int existingBookingId = 12; // Replace with a known booking ID that exists in your database

        // Retrieve the booking by ID
        Booking result = bookingDAO.getBookingById(existingBookingId);

        // Assert that the booking is not null
        assertNotNull(result);

        // Validate specific fields of the booking
        assertEquals(existingBookingId, result.getBookingId()); // Ensure the correct booking is retrieved
        assertEquals("123 Main St", result.getPickupLocation()); // Validate pickup location
        assertEquals("456 Elm St", result.getDropLocation()); // Validate dropoff location
        // Add more assertions as needed to validate other fields
    }

    /**
     * Test of getAllBookings method, of class BookingDAO.
     */
    
      @Test
        public void testGetAllBookings() {
        LOGGER.info("Starting testGetAllBookings method");

        // Ensure instance is initialized
        LOGGER.info("Checking if BookingDAO instance is initialized");
        assertNotNull("BookingDAO instance should not be null", bookingDAO);
        LOGGER.info("BookingDAO instance is initialized successfully");

        // Retrieve the list of cars
        LOGGER.info("Retrieving list of bookings from the database");
        List<Booking> bookings = bookingDAO.getAllBookings();

        // Log the number of cars retrieved
        if (bookings != null) {
            LOGGER.info("Number of cars retrieved: " + bookings.size());
            if (bookings.size() > 0) {
                LOGGER.info("First car in the list: " + bookings.get(0).toString()); 
            } else {
                LOGGER.info("Retrieved booking list is empty");
            }
        } else {
            LOGGER.info("Retrieved booking list is null");
        }

        // Assertions
        LOGGER.info("Performing assertions on the retrieved car list");
        assertNotNull("Car list should not be null", bookings);
        assertTrue("Car list should not be empty", bookings.size() > 0);

        LOGGER.info("testGetAllCars method completed successfully");
        }

    /**
     * Test of updateBooking method, of class BookingDAO.
     */
    @Test
    public void testUpdateBooking() {
        System.out.println("updateBooking");
        // Add a test booking
        bookingDAO.addBooking(1, "New York", "Los Angeles", 100.0, 10.0, 5.0, new Timestamp(System.currentTimeMillis()), Status.Completed, 1, 1);

        // Update the booking
        bookingDAO.updateBooking(7, 1, "Chicago", "Miami", 200.0, 20.0, 10.0, new Timestamp(System.currentTimeMillis()), "Confirmed", 86, 12);

        // Retrieve the updated booking
        Booking result = bookingDAO.getBookingById(7);
        assertNotNull(result);
        assertEquals("Chicago", result.getPickupLocation());
        assertEquals("Miami", result.getDropLocation());
    }

    /**
     * Test of deleteBooking method, of class BookingDAO.
     */
    @Test
    public void testDeleteBooking() {
        System.out.println("deleteBooking");
        // Add a test booking
        bookingDAO.addBooking(1, "New York", "Los Angeles", 100.0, 10.0, 5.0, new Timestamp(System.currentTimeMillis()),Status.Pending, 80, 12);

        // Delete the booking
        bookingDAO.deleteBooking(1);

        // Verify the booking is deleted
        Booking result = bookingDAO.getBookingById(1);
        assertNull(result);
    }

    /**
     * Test of searchBookingsByCustomerID method, of class BookingDAO.
     */
  @Test
    public void testSearchBookingsByCustomerID() {
        System.out.println("searchBookingsByCustomerID");

        // Define the customer ID to test
        int customerId = 1; // Replace with the customer ID you want to test

        // Search bookings by customer ID
        List<Booking> result = bookingDAO.searchBookingsByCustomerID(customerId);

        // Assert that the result is not null
        assertNotNull(result);

        // Verify that all returned bookings belong to the specified customer ID
        for (Booking booking : result) {
            assertEquals(customerId, booking.getCustomerId());
        }

        // Print the number of bookings found (optional)
        System.out.println("Found " + result.size() + " bookings for customer ID: " + customerId);
    }
    /**
     * Test of getBookingsByDriverId method, of class BookingDAO.
     */
  @Test
public void testGetBookingsByDriverId() {
    System.out.println("getBookingsByDriverId");

    // Use a known driver ID that exists in the database
    int driverId = 1; // Replace with a driver ID that exists in your database

    // Retrieve bookings by driver ID
    List<Booking> result = bookingDAO.getBookingsByDriverId(driverId);

    // Assert that the result is not null
    assertNotNull(result);

    // If you don't know the exact number of records, you can:
    // 1. Check if the list is empty or not
    // 2. Validate that all returned bookings belong to the specified driver ID

    if (result.isEmpty()) {
        System.out.println("No bookings found for driverId=" + driverId);
    } else {
        System.out.println("Found " + result.size() + " bookings for driverId=" + driverId);

        // Validate that all bookings belong to the specified driver ID
        for (Booking booking : result) {
            assertTrue(Objects.equals(driverId, booking.getDriverId())); 
        }
    }
}

    /**
     * Test of getBookingsWithNullDriverId method, of class BookingDAO.
     */
    @Test
    public void testGetBookingsWithNullDriverId() {
        System.out.println("getBookingsWithNullDriverId");
        // Add a test booking with no driver ID
        bookingDAO.addBooking(1, "New York", "Los Angeles", 100.0, 10.0, 5.0, new Timestamp(System.currentTimeMillis()),Status.Completed, 122, 12);

        // Retrieve bookings with null driver ID
        List<Booking> result = bookingDAO.getBookingsWithNullDriverId();
        assertNotNull(result);
        assertTrue(result.size() >= 1);
    }

    /**
     * Test of getBookingsByCustomerIdOrderByBookingDate method, of class BookingDAO.
     */
    @Test
    public void testGetBookingsByCustomerIdOrderByBookingDate() {
        System.out.println("getBookingsByCustomerIdOrderByBookingDate");

        // Retrieve bookings by customer ID ordered by booking date
        int customerId = 10; 
        List<Booking> result = bookingDAO.getBookingsByCustomerIdOrderByBookingDate(customerId);

        // Assert that the result is not null
        assertNotNull(result);

        // If the result is not empty, verify that the bookings are ordered by bookingDate
        if (!result.isEmpty()) {
            for (int i = 0; i < result.size() - 1; i++) {
                Timestamp currentBookingDate = result.get(i).getBookingDate();
                Timestamp nextBookingDate = result.get(i + 1).getBookingDate();

                // Assert that the current booking date is before or equal to the next booking date
                assertTrue(currentBookingDate.equals(nextBookingDate) || currentBookingDate.before(nextBookingDate));
            }
        } else {
            System.out.println("No bookings found for customer ID: " + customerId);
        }
    }

    /**
     * Test of getBookingsByStatus method, of class BookingDAO.
     */
   @Test
    public void testGetBookingsByStatus() {
        System.out.println("getBookingsByStatus");

        // Define the status to test
        Booking.Status status = Booking.Status.Pending; // Replace with the status you want to test

        // Retrieve bookings by status
        List<Booking> result = bookingDAO.getBookingsByStatus(status.name());

        // Assert that the result is not null
        assertNotNull(result);

        // Verify that all returned bookings have the correct status
        for (Booking booking : result) {
            assertEquals(status, booking.getStatus());
        }

        // Print the number of bookings found (optional)
        System.out.println("Found " + result.size() + " bookings with status: " + status);
    }

    /**
     * Test of getBookingsByCarId method, of class BookingDAO.
     */
    @Test
public void testGetBookingsByCarId() {
    System.out.println("getBookingsByCarId");
    
    // Add test bookings for car ID 1
    bookingDAO.addBooking(1, "New York", "Los Angeles", 100.0, 10.0, 5.0, new Timestamp(System.currentTimeMillis()), Status.Completed, 86, 12);
    bookingDAO.addBooking(2, "Chicago", "Miami", 200.0, 20.0, 10.0, new Timestamp(System.currentTimeMillis()), Status.Completed, 86, 12);

    // Retrieve bookings by car ID
    List<Booking> result = bookingDAO.getBookingsByCarId(86);
    
    // Assert that the result is not null
    assertNotNull(result);
    
    // Assert that the result is not empty (if you expect at least one booking)
    assertFalse(result.isEmpty());
    
    // Verify that all bookings have the correct carId
    for (Booking booking : result) {
        assertTrue(Objects.equals(86, booking.getCarId())); // Ensure all bookings have carId = 1
    }
}

    /**
     * Test of getBookingsByDateRange method, of class BookingDAO.
     */
    @Test
    public void testGetBookingsByDateRange() {
        System.out.println("getBookingsByDateRange");

        // Define the date range
        Timestamp startDate = new Timestamp(System.currentTimeMillis() - 10000); // 10 seconds ago
        Timestamp endDate = new Timestamp(System.currentTimeMillis() + 10000); // 10 seconds from now

        // Add test bookings within the date range
        bookingDAO.addBooking(1, "New York", "Los Angeles", 100.0, 10.0, 5.0, new Timestamp(System.currentTimeMillis()), Status.Completed, 80, 4);
        bookingDAO.addBooking(2, "Chicago", "Miami", 200.0, 20.0, 10.0, new Timestamp(System.currentTimeMillis()),Status.Confirmed, 80, 4);

        // Retrieve bookings by date range
        List<Booking> result = bookingDAO.getBookingsByDateRange(startDate, endDate);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result is not empty (if you expect at least one booking within the range)
        assertFalse(result.isEmpty());

        // Verify that all bookings fall within the specified date range
        for (Booking booking : result) {
            assertTrue("Booking date should be after or equal to startDate", 
                       booking.getBookingDate().compareTo(startDate) >= 0);
            assertTrue("Booking date should be before or equal to endDate", 
                       booking.getBookingDate().compareTo(endDate) <= 0);
        }
    }

    /**
     * Test of countBookingsByStatus method, of class BookingDAO.
     */
        @Test
      public void testGetBookingsByCustomerIdAndStatus() {
          System.out.println("getBookingsByCustomerIdAndStatus");

          // Define the customer ID and status for the test
          int customerId = 10;
          Booking.Status status = Booking.Status.Completed; // Use the enum value

          // Retrieve bookings by customer ID and status
          List<Booking> result = bookingDAO.getBookingsByCustomerIdAndStatus(customerId, status.name());

          // Assert that the result is not null and not empty
          assertNotNull(result);
          assertFalse(result.isEmpty());

          // Optionally, validate the content of the bookings
          for (Booking booking : result) {
              assertEquals(customerId, booking.getCustomerId());
              assertEquals(status, booking.getStatus()); // Compare enum values
          }
      }

@Test
public void testGetBookingsByDriverIdAndStatus() {
    System.out.println("getBookingsByDriverIdAndStatus");
    int driverId = 12;
    Booking.Status status = Booking.Status.Completed;
    BookingDAO instance = new BookingDAO();
    
    // Call the method to get the bookings
    List<Booking> result = instance.getBookingsByDriverIdAndStatus(driverId, status.name());
    
    // Assert that the result is not null
    assertNotNull(result);
    
    // If you know the expected number of bookings, you can check that
    // For example, if you expect at least one booking:
    assertFalse(result.isEmpty());
    
    // If you know specific details about the bookings, you can check those as well
    // For example, check that the first booking has the correct driver ID and status
    if (!result.isEmpty()) {
        Booking firstBooking = result.get(0);
        assertTrue(Objects.equals(driverId, firstBooking.getDriverId()));
        assertEquals(status, firstBooking.getStatus());
    }
}
    /**
     * Test of getBookingsByCarIdAndStatus method, of class BookingDAO.
     */
      @Test
    public void testGetBookingsByCarIdAndStatus() {
        System.out.println("getBookingsByCarIdAndStatus");
        int carId = 86;
        Booking.Status status = Booking.Status.Pending;
        BookingDAO instance = new BookingDAO();

        // Call the method under test
        List<Booking> result = instance.getBookingsByCarIdAndStatus(carId, status.name());

        // Assert that the result is not null
        assertNotNull(result);

        // Verify that all returned bookings have the correct carId and status
        for (Booking booking : result) {
            assertTrue(Objects.equals(carId, booking.getCarId()));
            assertEquals(status, booking.getStatus()); 
        }

        // If you expect no bookings to be returned, you can add a specific check for that
        if (result.isEmpty()) {
            System.out.println("No bookings found for carId: " + carId + " and status: " + status);
        } else {
            System.out.println("Found " + result.size() + " bookings for carId: " + carId + " and status: " + status);
        }
    }

    /**
     * Test of getBookingsByCustomerIdAndDriverId method, of class BookingDAO.
     */
    @Test
public void testGetBookingsByCustomerIdAndDriverId() {
    System.out.println("getBookingsByCustomerIdAndDriverId");
    int customerId = 10;
    int driverId = 86;
    BookingDAO instance = new BookingDAO();
    
    // Call the method to get the bookings
    List<Booking> result = instance.getBookingsByCustomerIdAndDriverId(customerId, driverId);
    
    // Assert that the result is not null
    assertNotNull(result);
    
    // If you don't know how many records exist, you can check the following:
    // 1. If the list is empty, it means no bookings were found for the given IDs.
    // 2. If the list is not empty, you can validate the contents of the bookings.
    
    if (result.isEmpty()) {
        System.out.println("No bookings found for customerId=" + customerId + " and driverId=" + driverId);
    } else {
        System.out.println("Found " + result.size() + " bookings for customerId=" + customerId + " and driverId=" + driverId);
        
        // Optionally, validate the contents of the bookings
        for (Booking booking : result) {
            assertEquals(customerId, booking.getCustomerId());
            assertTrue(Objects.equals(driverId, booking.getDriverId()));
        }
    }
}
    /**
     * Test of getBookingsByCustomerIdAndCarIdAndDriverId method, of class BookingDAO.
     */

   

    @Test
    public void testGetBookingsByCustomerIdAndCarIdAndDriverId() {
        System.out.println("getBookingsByCustomerIdAndCarIdAndDriverId");

        // Test input
        int customerId = 10; // int
        Integer carId = 86; // Integer
        Integer driverId = 12; // Integer

        // Create an instance of BookingDAO
        BookingDAO instance = new BookingDAO();

        // Call the method under test
        List<Booking> result = instance.getBookingsByCustomerIdAndCarIdAndDriverId(customerId, carId, driverId);

        // Assert that the result is not null and not empty
        assertNotNull(result);
        assertFalse(result.isEmpty());

        // Verify that all bookings match the given customerId, carId, and driverId
        for (Booking booking : result) {
            assertTrue(Objects.equals(customerId, booking.getCustomerId())); // int vs Integer
            assertTrue(Objects.equals(carId, booking.getCarId())); // Integer vs Integer
            assertTrue(Objects.equals(driverId, booking.getDriverId())); // Integer vs Integer
        }
    }
}