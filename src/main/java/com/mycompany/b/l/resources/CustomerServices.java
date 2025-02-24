package com.mycompany.b.l.resources;

import com.mycompany.b.l.db.Customer;
import com.mycompany.b.l.db.CustomerDAO;
import com.mycompany.b.l.db.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("customers")
public class CustomerServices {
    private static final Logger logger = Logger.getLogger(CustomerServices.class.getName());
    private static final Gson gson = GsonUtil.getGson(); // Use global Gson instance
    private final CustomerDAO customerDAO = new CustomerDAO(); // Customer database handler

    // Get all customers
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            logger.log(Level.INFO, "Fetched {0} customers", customers.size());
            return customers.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No customers found\"}")
                    .build()
                    : Response.ok(gson.toJson(customers)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching customers", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get a customer by ID
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") int id) {
        try {
            Customer customer = customerDAO.getCustomerById(id);
            return customer == null ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Customer not found\"}")
                    .build()
                    : Response.ok(gson.toJson(customer)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching customer", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Add a new customer
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(String json) {
        try {
            logger.info("Incoming JSON: " + json);
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            if (jsonObject == null || !jsonObject.has("username") || !jsonObject.has("passwordHash") || !jsonObject.has("name")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Missing required fields\"}")
                        .build();
            }
            Customer customer = gson.fromJson(json, Customer.class);
            if (customer.getUsername().trim().isEmpty() || customer.getPasswordHash().trim().isEmpty() || customer.getName().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Invalid customer details\"}")
                        .build();
            }
            return customerDAO.addCustomer(customer) ? Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Customer added successfully\"}")
                    .build()
                    : Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to add customer\"}")
                    .build();
        } catch (JsonSyntaxException e) {
            logger.log(Level.SEVERE, "JSON syntax error", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid JSON format\"}")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding customer", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Update an existing customer
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") int customerID, String json) {
        try {
            Customer customer = gson.fromJson(json, Customer.class);
            if (customer.getCustomerID() != customerID) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"ID mismatch\"}")
                        .build();
            }
            return customerDAO.updateCustomer(customer) ? Response.ok("{\"message\": \"Customer updated successfully\"}").build()
                    : Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Customer not found\"}")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating customer", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Delete a customer by ID
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("id") int id) {
        try {
            return customerDAO.deleteCustomer(id) ? Response.ok("{\"message\": \"Customer deleted successfully\"}").build()
                    : Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Customer not found\"}")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting customer", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Search customers by name, email, or phone number
    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCustomers(@QueryParam("term") String searchTerm) {
        try {
            List<Customer> customers = customerDAO.searchCustomers(searchTerm);
            return customers.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No customers found\"}")
                    .build()
                    : Response.ok(gson.toJson(customers)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching customers", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Check if username exists
    @GET
    @Path("check-username")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isUsernameExists(@QueryParam("username") String username) {
        try {
            boolean exists = customerDAO.isUsernameExists(username);
            return Response.ok("{\"exists\": " + exists + "}").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking username", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Check if email exists
    @GET
    @Path("check-email")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isEmailExists(@QueryParam("email") String email) {
        try {
            boolean exists = customerDAO.isEmailExists(email);
            return Response.ok("{\"exists\": " + exists + "}").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking email", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }
}