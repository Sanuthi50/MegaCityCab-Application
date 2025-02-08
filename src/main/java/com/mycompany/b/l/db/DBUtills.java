/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import db.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author User
 */
public class DBUtills {
  

public class CustomerDAO {

    static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycab";
    static final String USER = "root";
    static final String PASS = "Admin";

    // Query to fetch all customers
    static final String SELECT_ALL_QUERY = "SELECT * FROM Customers";

    // Query to get a customer by ID
    static final String SELECT_BY_ID_QUERY = "SELECT * FROM Customers WHERE CustomerID = ?";

    // Query to add a new customer
    static final String INSERT_QUERY = "INSERT INTO Customers (CustomerID, Username, PasswordHash, Name, Address, NIC, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    // Query to update an existing customer
    static final String UPDATE_QUERY = "UPDATE Customers SET Username = ?, PasswordHash = ?, Name = ?, Address = ?, NIC = ?, PhoneNumber = ?, Email = ? WHERE CustomerID = ?";

    // Query to delete a customer by ID
    static final String DELETE_QUERY = "DELETE FROM Customers WHERE CustomerID = ?";

    // Method to get all customers
    public List<Customer> getAllCustomers() throws ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY)) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("CustomerID"),
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
    public Customer getCustomerById(String customerID) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_QUERY)) {

            pstmt.setString(1, customerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getString("CustomerID"),
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

            pstmt.setString(1, customer.getCustomerID());
            pstmt.setString(2, customer.getUsername());
            pstmt.setString(3, customer.getPasswordHash());
            pstmt.setString(4, customer.getName());
            pstmt.setString(5, customer.getAddress());
            pstmt.setString(6, customer.getNic());
            pstmt.setString(7, customer.getPhoneNumber());
            pstmt.setString(8, customer.getEmail());

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
            pstmt.setString(8, customer.getCustomerID());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a customer by ID
    public boolean deleteCustomer(String customerID) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY)) {

            pstmt.setString(1, customerID);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

}