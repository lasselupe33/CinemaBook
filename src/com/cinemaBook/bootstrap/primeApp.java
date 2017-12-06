package com.cinemaBook.bootstrap;

import com.cinemaBook.globals.DataHandler;
import com.cinemaBook.globals.Router;
import com.cinemaBook.globals.ViewTypes;
import com.cinemaBook.model.Booking;
import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Seat;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

public class primeApp {
    public static void main(String[] args) {
        if (true) {
            // Create database mock up
            MockDatabase mockUp = new MockDatabase();
            mockUp.createDatabaseMockUp();
        }

        // EXAMPLE OF GETTING SCREENINGS
        if (true) {
            // To get all screenings pass "-1"
            ArrayList<Screening> screenings = DataHandler.getInstance().getScreenings(-1);

            // Pass the id of a screening to get an ArrayList<Screening> only containing said screening
            Screening screening = DataHandler.getInstance().getScreenings(2).get(0); // Use .get(0) to get the desired screening)
        }

        // EXAMPLE OF GETTING BOOKINGS BY CUSTOMER
        if (true) {
            Customer customer = new Customer("Lasse Agersten", "123", "hej@gmail.com");

            ArrayList<Booking> bookings = DataHandler.getInstance().getBookingsByCustomer(customer); // will return null if no bookings are found
        }

        // EXAMPLE OF SUBMITTING A BOOKING
        if (true) {
            // Create a user to connect to booking to
            Customer customer = new Customer("Test navn", "+45 12345678", "test@gmail.com");

            // Fetch the screening the booking is related to
            Screening screening = DataHandler.getInstance().getScreenings(1).get(0);

            // Create a list of new seats to reserve instead
            ArrayList<Seat> reservedSeats = new ArrayList<>();
            reservedSeats.add(new Seat(1, 1, true));
            reservedSeats.add(new Seat(1, 2, true));

            // Create the booking
            Booking booking = new Booking(customer, screening, reservedSeats);

            // Submit the booking
            DataHandler.getInstance().submitBooking(booking);
        }

        // EXAMPLE OF UPDATING A BOOKING
        if (true) {
            // Create a list of new seats to reserve instead
            ArrayList<Seat> newReservedSeats = new ArrayList<>();
            newReservedSeats.add(new Seat(6, 5, true));
            newReservedSeats.add(new Seat(6, 6, true));

            // Pass the booking id along with the new reserved seats
            DataHandler.getInstance().updateBooking(1, newReservedSeats);
        }

        // EXAMPLE OF DELETING A BOOKING
        if (true) {
            // Pass the booking id of the booking to be deleted
            DataHandler.getInstance().deleteBooking(2);
        }

        // default to ScreeningsView
        if (false) {
            Router.getInstance().updateLocation(ViewTypes.ScreeningsView);
        }
    }
}