package com.mycompany.b.l.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycab?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "Admin";

    // Query to fetch all customers
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Customers";

    // Query to get a customer by ID
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Customers WHERE CustomerID = ?";

    // Query to add a new customer
    private static final String INSERT_QUERY = "INSERT INTO Customers (CustomerID, Username, PasswordHash, Name, Address, NIC, PhoneNumber, Email, RegistrationDate, TotalBookings) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Query to update an existing customer
    private static final String UPDATE_QUERY = "UPDATE Customers SET Username = ?, PasswordHash = ?, Name = ?, Address = ?, NIC = ?, PhoneNumber = ?, Email = ?, RegistrationDate = ?, TotalBookings = ? WHERE CustomerID = ?";

    // Query to delete a customer by ID
    private static final String DELETE_QUERY = "DELETE FROM Customers WHERE CustomerID = ?";

    // Query to check if username exists
    private static final String CHECK_USERNAME_QUERY = "SELECT COUNT(*) FROM Customers WHERE Username = ?";

    // Query to check if email exists
    private static final String CHECK_EMAIL_QUERY = "SELECT COUNT(*) FROM Customers WHERE Email = ?";

    // Method to get all customers
    public List<Customer> getAllCustomers() throws ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY)) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("Username"),
                        rs.getString("PasswordHash"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("NIC"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getTimestamp("RegistrationDate").toLocalDateTime(),
                        rs.getInt("TotalBookings")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Method to get a customer by ID
    public Customer getCustomerById(int customerID) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_QUERY)) {

            pstmt.setInt(1,customerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("CustomerID"),
                            rs.getString("Username"),
                            rs.getString("PasswordHash"),
                            rs.getString("Name"),
                            rs.getString("Address"),
                            rs.getString("NIC"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Email"),
                            rs.getTimestamp("RegistrationDate").toLocalDateTime(),
                            rs.getInt("TotalBookings")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if customer not found
    }

    // Method to add a new customer
    public boolean addCustomer(Customer customer) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY)) {

            pstmt.setInt(1, customer.getCustomerID());
            pstmt.setString(2, customer.getUsername());
            pstmt.setString(3, customer.getPasswordHash());
            pstmt.setString(4, customer.getName());
            pstmt.setString(5, customer.getAddress());
            pstmt.setString(6, customer.getNic());
            pstmt.setString(7, customer.getPhoneNumber());
            pstmt.setString(8, customer.getEmail());
            pstmt.setTimestamp(9, java.sql.Timestamp.valueOf(customer.getRegistrationDate()));
            pstmt.setInt(10, customer.getTotalBookings());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing customer
    public boolean updateCustomer(Customer customer) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, customer.getUsername());
            pstmt.setString(2, customer.getPasswordHash());
            pstmt.setString(3, customer.getName());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getNic());
            pstmt.setString(6, customer.getPhoneNumber());
            pstmt.setString(7, customer.getEmail());
            pstmt.setTimestamp(8, java.sql.Timestamp.valueOf(customer.getRegistrationDate()));
            pstmt.setInt(9, customer.getTotalBookings());
            pstmt.setInt(10, customer.getCustomerID());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a customer by ID
    public boolean deleteCustomer(int customerID) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY)) {

            pstmt.setInt(1, customerID);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to check if username already exists
    public boolean isUsernameExists(String username) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(CHECK_USERNAME_QUERY)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to check if email already exists
    public boolean isEmailExists(String email) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(CHECK_EMAIL_QUERY)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to search customers by name, email, or phone number
    public List<Customer> searchCustomers(String searchTerm) throws ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");

        String SEARCH_QUERY = "SELECT * FROM Customers WHERE Name LIKE ? OR Email LIKE ? OR PhoneNumber LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(SEARCH_QUERY)) {

            pstmt.setString(1, "%" + searchTerm + "%");
            pstmt.setString(2, "%" + searchTerm + "%");
            pstmt.setString(3, "%" + searchTerm + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(new Customer(
                            rs.getInt("CustomerID"),
                            rs.getString("Username"),
                            rs.getString("PasswordHash"),
                            rs.getString("Name"),
                            rs.getString("Address"),
                            rs.getString("NIC"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Email"),
                            rs.getTimestamp("RegistrationDate").toLocalDateTime(),
                            rs.getInt("TotalBookings")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}