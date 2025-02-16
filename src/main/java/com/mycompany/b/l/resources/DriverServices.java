package com.mycompany.b.l.resources;

import com.mycompany.b.l.db.LocalDateAdapter;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mycompany.b.l.db.Driver;
import com.mycompany.b.l.db.DriverDB;
import com.mycompany.b.l.db.GsonUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("drivers")
public class DriverServices {
    private static final Logger logger = Logger.getLogger(DriverServices.class.getName());
    private static final Gson gson = GsonUtil.getGson(); // Use the global Gson instance

    private final DriverDB driverDB = new DriverDB();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrivers() {
        try {
            List<Driver> drivers = driverDB.getAllDrivers();
            logger.log(Level.INFO, "Fetched {0} drivers from the database", drivers.size());
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDriver(String json) {
        try {
            // Log the incoming JSON for debugging
            logger.info("Incoming JSON: " + json);

            // Deserialize the JSON into a Driver object using the global Gson instance
            Driver driver = gson.fromJson(json, Driver.class);

            // Validate the driver fields
            if (driver.getName() == null || driver.getName().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Driver name is required\"}")
                        .build();
            }

            if (driver.getLicenseNumber() == null || driver.getLicenseNumber().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Driver license number is required\"}")
                        .build();
            }

            if (driver.getLicenseExpiryDate() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Driver license expiry date is required\"}")
                        .build();
            }

            // Attempt to add the driver to the database
            boolean isAdded = driverDB.addDriver(driver);
            return isAdded ? Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Driver added successfully\"}")
                        .build()
                    : Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"Failed to add driver\"}")
                        .build();
        } catch (JsonSyntaxException e) {
            logger.log(Level.SEVERE, "JSON syntax error: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid JSON format\"}")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding driver", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error while adding driver\"}")
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDriver(@PathParam("id") int driverID, String json) {
        try {
            // Deserialize JSON into a Driver object
            Driver driver = gson.fromJson(json, Driver.class);
            driver.setDriverID(driverID); // Set the driver ID from URL path

            // Call the updateDriver method
            boolean isUpdated = driverDB.updateDriver(driver);

            // Return appropriate response
            return isUpdated 
                    ? Response.ok("{\"message\": \"Driver updated successfully\"}").build()
                    : Response.status(Response.Status.NOT_FOUND)
                              .entity("{\"error\": \"Driver not found or update failed\"}")
                              .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating driver", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error while updating driver\"}")
                    .build();
        }
}


    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDriver(@PathParam("id") int id) {
        try {
            boolean isDeleted = driverDB.deleteDriver(id);
            return isDeleted ? Response.ok("{\"message\": \"Driver deleted successfully\"}").build()
                    : Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Driver not found or could not be deleted\"}")
                        .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting driver with ID: " + id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error while deleting driver\"}")
                    .build();
        }
    }
}