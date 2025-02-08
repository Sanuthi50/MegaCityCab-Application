package com.mycompany.b.l.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.b.l.db.Driver;
import com.mycompany.b.l.db.DriverDB;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("driver")
public class DriverServices {

    private static final Logger logger = Logger.getLogger(DriverServices.class.getName());
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create(); 
    private final DriverDB driverDB = new DriverDB();

    // Get all drivers
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrivers() {
        try {
            List<Driver> drivers = driverDB.getAllDrivers();

            if (drivers.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No drivers found\"}")
                        .build();
            }

            return Response.ok(gson.toJson(drivers)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching drivers", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error while fetching drivers\"}")
                    .build();
        }
    }

    // Get driver by ID
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriver(@PathParam("id") int id) {
        try {
            Driver driver = driverDB.getDriverById(id);

            if (driver == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Driver not found with ID: " + id + "\"}")
                        .build();
            }

            return Response.ok(gson.toJson(driver)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching driver with ID: " + id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error while fetching driver\"}")
                    .build();
        }
    }

    // Add a new driver
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDriver(String json) {
        try {
            Driver driver = gson.fromJson(json, Driver.class);

            // Validate driver name
            if (driver.getName() == null || driver.getName().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Driver name is required\"}")
                        .build();
            }

            // Validate other required fields if needed
            if (driver.getLicenseNumber() == null || driver.getLicenseNumber().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Driver license number is required\"}")
                        .build();
            }

            boolean isAdded = driverDB.addDriver(driver);

            if (isAdded) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Driver added successfully\"}")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"Failed to add driver\"}")
                        .build();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding driver", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error while adding driver\"}")
                    .build();
        }
    }

    // Update an existing driver
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDriver(String json) {
        try {
            Driver driver = gson.fromJson(json, Driver.class);

            // Validate driver ID for updating
            if (driver.getDriverID() == 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Driver ID is required for updating\"}")
                        .build();
            }

            boolean isUpdated = driverDB.updateDriver(driver);

            if (isUpdated) {
                return Response.ok("{\"message\": \"Driver updated successfully\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Driver not found or update failed\"}")
                        .build();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating driver", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error while updating driver\"}")
                    .build();
        }
    }

    // Delete a driver by ID
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDriver(@PathParam("id") int id) {
        try {
            boolean isDeleted = driverDB.deleteDriver(id);

            if (isDeleted) {
                return Response.ok("{\"message\": \"Driver deleted successfully\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Driver not found or could not be deleted\"}")
                        .build();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting driver with ID: " + id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error while deleting driver\"}")
                    .build();
        }
    }
}
