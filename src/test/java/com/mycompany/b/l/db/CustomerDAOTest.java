package com.mycompany.b.l.db;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the CustomerDAO class.
 */
public class CustomerDAOTest {

    private CustomerDAO customerDAO;

    public CustomerDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        // Initialize any resources needed for the entire test class
    }

    @AfterClass
    public static void tearDownClass() {
        // Clean up any resources used by the test class
    }

    @Before
    public void setUp() {
        // Initialize the CustomerDAO instance before each test
        customerDAO = new CustomerDAO();
    }

    @After
    public void tearDown() {
        // Clean up after each test
    }

    /**
     * Test of getAllCustomers method, of class CustomerDAO.
     */
    @Test
    public void testGetAllCustomers() throws Exception {
        System.out.println("getAllCustomers");
         CustomerDAO instance = new CustomerDAO();
        // Fetch all customers
        List<Customer> result = instance.getAllCustomers();

        // Assert that the list is not null
          assertNotNull("List of drivers should not be null", result);

        // If the database is not empty, assert that the list is not empty
        if (!result.isEmpty()) {
            assertFalse(result.isEmpty());
        }
        
        for (Customer customer: result) {
            System.out.println(customer);
        }
    }
     

    /**
     * Test of getCustomerById method, of class CustomerDAO.
     */
    @Test
    public void testGetCustomerById() throws Exception {
        System.out.println("getCustomerById");

        // Fetch a customer by ID (replace "1" with a valid customer ID from your database)
        int customerID = 5;
        Customer customer = customerDAO.getCustomerById(customerID);

        // Assert that the customer is not null
        assertNotNull(customer);

        // Assert that the customer ID matches the expected value
        assertEquals(customerID, customer.getCustomerID());
    }

    /**
     * Test of addCustomer method, of class CustomerDAO.
     
    @Test
    public void testAddCustomer() throws Exception {
        System.out.println("addCustomer");

        // Create a new customer
        Customer customer = new Customer(
                23, // CustomerID
                "testuser", // Username
                "hashedpassword", // PasswordHash
                "Test User", // Name
                "123 Test St", // Address
                "123456789", // NIC
                "1234567890", // PhoneNumber
                "test@example.com", // Email
                LocalDateTime.now(), // RegistrationDate
                0 // TotalBookings
        );

        // Add the customer to the database
        boolean result = customerDAO.addCustomer(customer);

        // Assert that the customer was added successfully
        assertTrue(result);

        // Fetch the customer to verify it was added
        Customer addedCustomer = customerDAO.getCustomerById(23);
        assertNotNull(addedCustomer);
        assertEquals("testuser", addedCustomer.getUsername());
    }

    /**
     * Test of updateCustomer method, of class CustomerDAO.
     */
    @Test
    public void testUpdateCustomer() throws Exception {
        System.out.println("updateCustomer");

        // Fetch an existing customer (replace "1" with a valid customer ID)
       int customerID = 1;
        Customer customer = customerDAO.getCustomerById(customerID);
        assertNotNull(customer);

        // Update the customer's details
        customer.setName("Updated Name");
        customer.setEmail("updated@example.com");

        // Update the customer in the database
        boolean result = customerDAO.updateCustomer(customer);

        // Assert that the update was successful
        assertTrue(result);

        // Fetch the customer again to verify the update
        Customer updatedCustomer = customerDAO.getCustomerById(customerID);
        assertEquals("Updated Name", updatedCustomer.getName());
        assertEquals("updated@example.com", updatedCustomer.getEmail());
    }

    /**
     * Test of deleteCustomer method, of class CustomerDAO.
     
    @Test
    public void testDeleteCustomer() throws Exception {
        System.out.println("deleteCustomer");

        // Add a customer to delete (replace with a valid customer ID)
        int customerID = 8;

        // Delete the customer
        boolean result = customerDAO.deleteCustomer(customerID);

        // Assert that the deletion was successful
        assertTrue(result);

        // Fetch the customer to verify it was deleted
        Customer deletedCustomer = customerDAO.getCustomerById(customerID);
        assertNull(deletedCustomer);
    }

    /**
     * Test of isUsernameExists method, of class CustomerDAO.
     */
    @Test
    public void testIsUsernameExists() throws Exception {
        System.out.println("isUsernameExists");

        // Check if a username exists (replace "testuser" with a valid username)
        String username = "jane_smith";
        boolean result = customerDAO.isUsernameExists(username);

        // Assert that the result is as expected
        assertTrue(result); // or assertFalse if the username does not exist
    }

    /**
     * Test of isEmailExists method, of class CustomerDAO.
     */
    @Test
    public void testIsEmailExists() throws Exception {
        System.out.println("isEmailExists");

        // Check if an email exists (replace "test@example.com" with a valid email)
        String email = "jane@example.com";
        boolean result = customerDAO.isEmailExists(email);

        // Assert that the result is as expected
        assertTrue(result); // or assertFalse if the email does not exist
    }

    /**
     * Test of searchCustomers method, of class CustomerDAO.
     */
    @Test
    public void testSearchCustomers() throws Exception {
        System.out.println("searchCustomers");

        // Search for customers by a term (replace "Test" with a valid search term)
        String searchTerm = "Test";
        List<Customer> customers = customerDAO.searchCustomers(searchTerm);

        // Assert that the list is not null
        assertNotNull(customers);

        // If the search term matches any customers, assert that the list is not empty
        if (!customers.isEmpty()) {
            assertFalse(customers.isEmpty());
        }
    }
}