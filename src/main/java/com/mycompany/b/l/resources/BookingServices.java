/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.b.l.resources;

/**
 *
 * @author User
 */


import com.mycompany.b.l.db.Booking;
import com.mycompany.b.l.db.BookingDAO;
import com.mycompany.b.l.db.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("bookings")
public class BookingServices {
    private static final Logger logger = Logger.getLogger(BookingServices.class.getName());
    private static final Gson gson = GsonUtil.getGson(); // Use global Gson instance
    private final BookingDAO bookingDAO = new BookingDAO(); // Booking database handler

    // Add a new booking
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(String json) {
        try {
            logger.info("Incoming JSON: " + json);
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            if (jsonObject == null || !jsonObject.has("customerID") || !jsonObject.has("pickupLocation") ||
                !jsonObject.has("dropLocation") || !jsonObject.has("price") || !jsonObject.has("status") ||
                !jsonObject.has("carID")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Missing required fields\"}")
                        .build();
            }

            Booking booking = gson.fromJson(json, Booking.class);
            int bookingId = bookingDAO.addBooking(
                booking.getCustomerId(),
                booking.getPickupLocation(),
                booking.getDropLocation(),
                booking.getPrice(),
                booking.getDiscount(),
                booking.getTax(),
                booking.getBookingDate(),
                booking.getStatus(),
                booking.getCarId(),
                booking.getDriverId()
            );

            if (bookingId != -1) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Booking added successfully\", \"bookingId\": " + bookingId + "}")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"Failed to add booking\"}")
                        .build();
            }
        } catch (JsonSyntaxException e) {
            logger.log(Level.SEVERE, "JSON syntax error", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid JSON format\"}")
                    .build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding booking", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get a booking by ID
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingById(@PathParam("id") int id) {
        try {
            Booking booking = bookingDAO.getBookingById(id);
            return booking == null ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Booking not found\"}")
                    .build()
                    : Response.ok(gson.toJson(booking)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching booking", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get all bookings
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookings() {
        try {
            List<Booking> bookings = bookingDAO.getAllBookings();
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Update a booking
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(@PathParam("id") int bookingID, String json) {
        try {
            Booking booking = gson.fromJson(json, Booking.class);
            if (booking.getBookingId() != bookingID) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"ID mismatch\"}")
                        .build();
            }

            bookingDAO.updateBooking(
                bookingID,
                booking.getCustomerId(),
                booking.getPickupLocation(),
                booking.getDropLocation(),
                booking.getPrice(),
                booking.getDiscount(),
                booking.getTax(),
                booking.getBookingDate(),
                booking.getStatus().name(),
                booking.getCarId(),
                booking.getDriverId()
            );

            return Response.ok("{\"message\": \"Booking updated successfully\"}").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating booking", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Delete a booking by ID
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("id") int id) {
        try {
            bookingDAO.deleteBooking(id);
            return Response.ok("{\"message\": \"Booking deleted successfully\"}").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting booking", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Search bookings by customer ID
    @GET
    @Path("search/customer/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBookingsByCustomerID(@PathParam("customerId") int customerId) {
        try {
            List<Booking> bookings = bookingDAO.searchBookingsByCustomerID(customerId);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found for this customer\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching bookings by customer ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by driver ID
    @GET
    @Path("driver/{driverId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByDriverId(@PathParam("driverId") int driverId) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByDriverId(driverId);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found for this driver\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by driver ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings with null driver ID
    @GET
    @Path("null-driver")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsWithNullDriverId() {
        try {
            List<Booking> bookings = bookingDAO.getBookingsWithNullDriverId();
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings with null driver found\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings with null driver ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by customer ID ordered by booking date
    @GET
    @Path("customer/{customerId}/ordered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByCustomerIdOrderByBookingDate(@PathParam("customerId") int customerId) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByCustomerIdOrderByBookingDate(customerId);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found for this customer\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by customer ID ordered by date", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by status
    @GET
    @Path("status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByStatus(@PathParam("status") String status) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByStatus(status);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found with this status\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by status", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by car ID
    @GET
    @Path("car/{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByCarId(@PathParam("carId") int carId) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByCarId(carId);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found for this car\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by car ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by date range
    @GET
    @Path("date-range")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByDateRange(
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) {
        try {
            Timestamp start = Timestamp.valueOf(startDate);
            Timestamp end = Timestamp.valueOf(endDate);
            List<Booking> bookings = bookingDAO.getBookingsByDateRange(start, end);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found in this date range\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by date range", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Count bookings by status
    @GET
    @Path("count/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countBookingsByStatus(@PathParam("status") String status) {
        try {
            int count = bookingDAO.countBookingsByStatus(status);
            return Response.ok("{\"count\": " + count + "}").build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error counting bookings by status", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by customer ID and status
    @GET
    @Path("customer/{customerId}/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByCustomerIdAndStatus(
            @PathParam("customerId") int customerId,
            @PathParam("status") String status) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByCustomerIdAndStatus(customerId, status);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found for this customer and status\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by customer ID and status", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by driver ID and status
    @GET
    @Path("driver/{driverId}/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByDriverIdAndStatus(
            @PathParam("driverId") int driverId,
            @PathParam("status") String status) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByDriverIdAndStatus(driverId, status);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found for this driver and status\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by driver ID and status", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by car ID and status
    @GET
    @Path("car/{carId}/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByCarIdAndStatus(
            @PathParam("carId") int carId,
            @PathParam("status") String status) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByCarIdAndStatus(carId, status);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found for this car and status\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by car ID and status", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by customer ID and driver ID
    @GET
    @Path("customer/{customerId}/driver/{driverId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByCustomerIdAndDriverId(
            @PathParam("customerId") int customerId,
            @PathParam("driverId") int driverId) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByCustomerIdAndDriverId(customerId, driverId);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found for this customer and driver\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by customer ID and driver ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }

    // Get bookings by customer ID, car ID, and driver ID
    @GET
    @Path("customer/{customerId}/car/{carId}/driver/{driverId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsByCustomerIdAndCarIdAndDriverId(
            @PathParam("customerId") int customerId,
            @PathParam("carId") int carId,
            @PathParam("driverId") int driverId) {
        try {
            List<Booking> bookings = bookingDAO.getBookingsByCustomerIdAndCarIdAndDriverId(customerId, carId, driverId);
            return bookings.isEmpty() ? Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No bookings found for this customer, car, and driver\"}")
                    .build()
                    : Response.ok(gson.toJson(bookings)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching bookings by customer ID, car ID, and driver ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }
}
