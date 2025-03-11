package com.mycompany.b.l.resources;

import com.mycompany.b.l.db.Driver;
import com.mycompany.b.l.db.DriverDB;
import com.mycompany.b.l.db.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mycompany.b.l.db.Customer;
import com.mycompany.b.l.db.CustomerDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;

@Path("drivers")
public class DriverServices {
    private static final Logger logger = Logger.getLogger(DriverServices.class.getName());
    private static final Gson gson = GsonUtil.getGson(); // Use global Gson instance
    private final DriverDB driverDB = new DriverDB(); // Driver database handler

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrivers() {
        try {
            List<Driver> drivers = driverDB.getAllDrivers();
            logger.log(Level.INFO, "Fetched {0} drivers", drivers.size());
            return drivers.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No drivers found\"}")
                    .build()
                    : Response.ok(gson.toJson(drivers)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching drivers", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriver(@PathParam("id") int id) {
        try {
            Driver driver = driverDB.getDriverById(id);
            return driver == null ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Driver not found\"}")
                    .build()
                    : Response.ok(gson.toJson(driver)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching driver", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDriver(String json) {
        try {
            logger.info("Incoming JSON: " + json);
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            if (jsonObject == null || !jsonObject.has("name") || !jsonObject.has("licenseNumber") || !jsonObject.has("licenseExpiryDate")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Missing required fields\"}")
                        .build();
            }
            Driver driver = gson.fromJson(json, Driver.class);
            if (driver.getName().trim().isEmpty() || driver.getLicenseNumber().trim().isEmpty() || driver.getLicenseExpiryDate() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Invalid driver details\"}")
                        .build();
            }
            return driverDB.addDriver(driver) ? Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Driver added successfully\"}")
                    .build()
                    : Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to add driver\"}")
                    .build();
        } catch (JsonSyntaxException e) {
            logger.log(Level.SEVERE, "JSON syntax error", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid JSON format\"}")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding driver", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

   @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDriver(@PathParam("id") int driverID, String json) {
        try {
            logger.info("Received JSON: " + json);
            logger.info("Path ID: " + driverID);

            Driver driver = gson.fromJson(json, Driver.class);
            if (driver.getDriverID() != driverID) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"ID mismatch\"}")
                        .build();
            }
            return driverDB.updateDriver(driver) ? Response.ok("{\"message\": \"Driver updated successfully\"}").build()
                    : Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Driver not found\"}")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating driver", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDriver(@PathParam("id") int id) {
        try {
            return driverDB.deleteDriver(id) ? Response.ok("{\"message\": \"Driver deleted successfully\"}").build()
                    : Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Driver not found\"}")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting driver", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }
     @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Driver driver) {
        DriverDB driverDAO = new DriverDB();
        Driver authenticatedDriver = driverDAO.authenticateDriver(driver.getUsername(), driver.getPassword());

        if (authenticatedDriver != null) {
            javax.json.JsonObject jsonResponse = Json.createObjectBuilder()
                .add("id", authenticatedDriver.getDriverID()) 
                .add("message", "Login successful")
                .build();

            return Response.ok(jsonResponse)
                .header("Access-Control-Allow-Origin", "*")
                .build();
        } else {
            javax.json.JsonObject errorResponse = Json.createObjectBuilder()
                .add("error", "Invalid username or password")
                .build();

            return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorResponse)
                .header("Access-Control-Allow-Origin", "*")
                .build();
        }
    }

    @OPTIONS
    @Path("login")
    public Response preflight() {
        return Response.ok()
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "POST, OPTIONS")
            .header("Access-Control-Allow-Headers", "Content-Type")
            .build();
    }
}