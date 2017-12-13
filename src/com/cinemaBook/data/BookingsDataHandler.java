package com.cinemaBook.data;

import com.cinemaBook.model.Booking;
import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Seat;
import com.cinemaBook.utils.ConvertReservedSeats;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This singleton data handler has the responsibility to manage all connection between the database in regards to bookings
 */
public class BookingsDataHandler extends DataHandler {
    // Store a reference to the dataHandler
    private static final BookingsDataHandler instance = new BookingsDataHandler();

    /**
     * Create a connection via the database
     */
    public BookingsDataHandler() {
        super();
    }

    // Returns a reference to the screeningsDataHandler
    public static final BookingsDataHandler getInstance() { return instance; }

    /**
     * This method returns all bookings that have been made
     */
    public ArrayList<Booking> getBookings() {
        try {
            String query = "SELECT * FROM Bookings";
            ResultSet rs = this.createStatement().executeQuery(query);

            ArrayList<Booking> bookings = new ArrayList<>();

            while (rs.next()) {
                // Get associated screening
                Screening screening = ScreeningsDataHandler.getInstance().getScreenings(rs.getInt("screening_id")).get(0);

                // Get the reserved seats in proper format
                ArrayList<Seat> reservedSeats = ConvertReservedSeats.convertReservedSeatsStringToArray(rs.getString("reserved_seats"));

                // Get the associated customer
                Customer customer = this.getCustomer(rs.getInt("customer_id"));

                // Create a booking model with the fetched information
                Booking booking = new Booking(customer, screening, reservedSeats);

                // Store its id as well
                booking.setId(rs.getInt("booking_id"));

                // Add the booking to the arraylist
                bookings.add(booking);
            }

            // Return the bookings
            return bookings;
        } catch (Exception e) {
            throw new Error("Error while getting bookings. Error: " + e.getMessage());
        }
    }

    /**
     * This method is made in order to get a list of bookings made by a customer
     * @param customer A customer model
     * @return an arrayList of bookings made by a customer - Will be null if no customer exist
     */
    public ArrayList<Booking> getBookingsByCustomer(Customer customer) {
        int customer_id = CustomerDataHandler.getInstance().getCustomerId(customer);

        // If no customer id exist, then there will be no associated bookings..
        if (customer_id == -1) {
            return null;
        } else {
            try {
                // Execute query to get all bookings for the customer
                String query = "SELECT * FROM Bookings WHERE customer_id = '" + customer_id + "';";
                ResultSet rs = this.createStatement().executeQuery(query);

                ArrayList<Booking> bookings = new ArrayList<>();

                // Loop over bookings and add them to the array
                while (rs.next()) {
                    // Get the associated screening
                    Screening screening = ScreeningsDataHandler.getInstance().getScreenings(rs.getInt("screening_id")).get(0);

                    // Get the reserved seats and format them into an array usable in the code.
                    ArrayList<Seat> reservedSeats = ConvertReservedSeats.convertReservedSeatsStringToArray(rs.getString("reserved_seats"));

                    // Create a booking model with the fetched information
                    Booking booking = new Booking(customer, screening, reservedSeats);

                    // Store its id as well
                    booking.setId(rs.getInt("booking_id"));

                    // Add the booking to the list of user bookings
                    bookings.add(booking);
                }

                return bookings;
            } catch (Exception e) {
                throw new Error("Couldn't fetch bookings for customer with id=" + customer_id + ". " + e.getMessage());
            }
        }
    }

    /**
     * This function submits a booking to the database based on the booking object passed.
     *
     * In other words, it creates a customer if he/she doesn't exist yet, while also updating the seatAssignment for the
     * given screening
     */
    public void submitBooking(Booking booking) {
        // Insert the customer into the database
        int customer_id = CustomerDataHandler.getInstance().insertCustomer(booking.getCustomer());

        // Get the screening ID
        int screening_id = booking.getScreening().getId();

        // Update the reserved seats in the database
        SeatDataHandler.getInstance().insertSeatReservation(screening_id, booking.getReservedSeats());

        // Store the booking
        insertBooking(customer_id, screening_id, booking);
    }

    /**
     * When updating a booking, the old seats will be cleared from the seatAssignment and afterwards the new will be
     * added.
     *
     * Furthermore the booking entry will be updated as well.
     *
     * @param booking_id
     * @param newReservedSeats
     */
    public void updateBooking(int booking_id, ArrayList<Seat> newReservedSeats) {
        try {
            // First, get the list of old seats
            ResultSet rs = this.createStatement().executeQuery("SELECT screening_id, reserved_seats FROM Bookings WHERE booking_id = '" + booking_id + "';");
            rs.next();
            ArrayList<Seat> oldReservedSeats = ConvertReservedSeats.convertReservedSeatsStringToArray(rs.getString("reserved_seats"));

            // Delete old seats
            int screening_id = rs.getInt("screening_id");
            SeatDataHandler.getInstance().deleteSeatReservation(screening_id, oldReservedSeats);

            // Now add the list of new seats
            SeatDataHandler.getInstance().insertSeatReservation(screening_id, newReservedSeats);

            // Finally update the booking with the new seats
            String reservedSeatsString = ConvertReservedSeats.convertReservedSeatsToString(newReservedSeats);
            this.createStatement().executeUpdate("UPDATE Bookings SET reserved_seats = '" + reservedSeatsString + "' WHERE booking_id = '" + booking_id + "';");
        } catch (Exception e) {
            throw new Error("Failed to update booking: " + e.getMessage());
        }
    }

    /**
     * When called this deletes a booking along with its reserved seats.
     */
    public void deleteBooking(int booking_id) {
        try {
            // First, get the list of seats to delete.
            ResultSet rs = this.createStatement().executeQuery("SELECT screening_id, reserved_seats FROM Bookings WHERE booking_id = '" + booking_id + "';");
            rs.next();
            ArrayList<Seat> seatsToBeDeleted = ConvertReservedSeats.convertReservedSeatsStringToArray(rs.getString("reserved_seats"));

            // Delete the old seats
            int screening_id = rs.getInt("screening_id");
            SeatDataHandler.getInstance().deleteSeatReservation(screening_id, seatsToBeDeleted);

            // Now delete the booking!
            this.createStatement().executeUpdate("DELETE FROM Bookings WHERE booking_id = '" + booking_id + "';");
        } catch (Exception e) {
            throw new Error("Failed to delete booking! " + e.getMessage());
        }
    }

    /**
     * This function inserts a booking into the booking table
     * @param customer_id The id of the customer
     * @param screening_id The id of the screening
     * @param booking The bookingModel
     */
    private void insertBooking(int customer_id, int screening_id, Booking booking) {
        HashMap<String, String> bookingMap = new HashMap<>();
        bookingMap.put("customer_id", "" + customer_id);
        bookingMap.put("screening_id", "" + screening_id);
        bookingMap.put("reserved_seats", ConvertReservedSeats.convertReservedSeatsToString(booking.getReservedSeats()));
        this.insertData("Bookings", bookingMap);

        try {
            // Create a new statement to get the id of the newly inserted booking
            ResultSet idResultSet = this.createStatement().executeQuery("SELECT LAST_INSERT_ID() id");
            idResultSet.next();

            // Update the booking model with the newly created id
            booking.setId(idResultSet.getInt("id"));
        } catch (Exception e) {
            throw new Error("Failed to set the id of the booking.." + e.getMessage());
        }
    }

    /**
     * This method returns a customer model
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    private Customer getCustomer(int customerId) {
        try {
            String query = "SELECT * FROM Customers WHERE customer_id=" + customerId;
            ResultSet rs = this.createStatement().executeQuery(query);
            rs.next();

            // Create and return the customer
            String name = rs.getString("customer_name");
            String phone = rs.getString("customer_phone");
            String email = rs.getString("customer_email");

            return new Customer(name, phone, email);
        } catch (Exception e) {
            throw new Error("Error while attempting to fetch customer. Error: " + e.getMessage());
        }
    }
}
