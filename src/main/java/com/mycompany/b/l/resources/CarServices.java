package com.mycompany.b.l.resources;

import com.mycompany.b.l.db.Car;
import com.mycompany.b.l.db.CarDB;
import com.mycompany.b.l.db.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("cars")
public class CarServices {
    private static final Logger logger = Logger.getLogger(CarServices.class.getName());
    private static final Gson gson = GsonUtil.getGson(); // Use global Gson instance
    private final CarDB carDB = new CarDB(); // Car database handler

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars() {
        try {
            List<Car> cars = carDB.getAllCars();
            logger.log(Level.INFO, "Fetched {0} cars", cars.size());
            return cars.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No cars found\"}")
                    .build()
                    : Response.ok(gson.toJson(cars)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching cars", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("id") int id) {
        try {
            Car car = carDB.getCarById(id);
            return car == null ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Car not found\"}")
                    .build()
                    : Response.ok(gson.toJson(car)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching car with ID: " + id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCar(String json) {
        try {
            logger.info("Incoming JSON: " + json);

            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            if (jsonObject == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Invalid JSON format\"}")
                        .build();
            }

            List<String> requiredFields = Arrays.asList(
                "carID", "licensePlate", "model", "make", "year", "capacity", 
                "fuelType", "availability", "lastServiceDate", "insuranceExpiryDate", 
                "mileage", "status", "driverID", "price", "discount", "tax"
            );

            for (String field : requiredFields) {
                if (!jsonObject.has(field)) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("{\"error\": \"Missing required field: " + field + "\"}")
                            .build();
                }
            }

            Car car = gson.fromJson(json, Car.class);

            if (car.getLicensePlate().trim().isEmpty() ||
                car.getModel().trim().isEmpty() ||
                car.getMake().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Invalid car details\"}")
                        .build();
            }

            carDB.addCar(
                car.getCarID(), car.getLicensePlate(), car.getModel(), car.getMake(),
                car.getYear(), car.getCapacity(), car.getFuelType().toString(), 
                car.isAvailability(), car.getLastServiceDate(), car.getInsuranceExpiryDate(), 
                car.getMileage().doubleValue(), car.getStatus().toString(), car.getDriverID(),
                car.getPrice(), car.getDiscount(), car.getTax()
            );

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Car added successfully\"}")
                    .build();

        } catch (JsonSyntaxException e) {
            logger.log(Level.SEVERE, "JSON syntax error", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid JSON format\"}")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding car", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCar(@PathParam("id") int carID, String json) {
        try {
            Car car = gson.fromJson(json, Car.class);
            if (car.getCarID() != carID) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"ID mismatch\"}")
                        .build();
            }

            carDB.updateCar(carID, car.getLicensePlate(), car.getModel(), car.getMake(), car.getYear(), car.getCapacity(),
                            car.getFuelType().toString(), car.isAvailability(), car.getLastServiceDate(), car.getInsuranceExpiryDate(),
                            car.getMileage().doubleValue(), car.getStatus().toString(), car.getDriverID(), car.getPrice(),car.getDiscount(), car.getTax());

            return Response.ok("{\"message\": \"Car updated successfully\"}").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating car with ID: " + carID, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCar(@PathParam("id") int id) {
        try {
            carDB.deleteCar(id);
            return Response.ok("{\"message\": \"Car deleted successfully\"}").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting car with ID: " + id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCar(@QueryParam("licensePlate") String licensePlate) {
        try {
            Car car = carDB.searchCar(licensePlate);
            return car == null ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Car not found\"}")
                    .build()
                    : Response.ok(gson.toJson(car)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching car with license plate: " + licensePlate, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    @GET
    @Path("available") // Endpoint path: /cars/available
    @Produces(MediaType.APPLICATION_JSON) // Response type is JSON
    public Response getAvailableCarsForDropdown() {
        try {
            // Fetch available cars using your method
            List<Car> availableCars = new CarDB().FetchAvailableCars();

            // Log the number of cars fetched
            logger.log(Level.INFO, "Fetched {0} available cars", availableCars.size());

            // Transform the list of cars into a simplified format for the dropdown
            List<CarDropdownDTO> dropdownCars = new ArrayList<>();
            for (Car car : availableCars) {
                String displayText = car.getLicensePlate() + " - " + car.getModel();
                dropdownCars.add(new CarDropdownDTO(car.getCarID(), displayText));
            }

            // Return response based on whether cars were found
            if (dropdownCars.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No available cars found\"}")
                        .build();
            } else {
                return Response.ok(dropdownCars).build(); // Return the simplified list as JSON
            }
        } catch (Exception e) {
            // Log the error and return an internal server error response
            logger.log(Level.SEVERE, "Error fetching available cars", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }
}