package com.cinemaBook.bootstrap;

import com.cinemaBook.controller.BookingController;
import com.cinemaBook.controller.EditBookingController;
import com.cinemaBook.data.BookingsDataHandler;
import com.cinemaBook.data.ScreeningsDataHandler;
import com.cinemaBook.model.*;
import com.cinemaBook.utils.MockDatabase;
import com.cinemaBook.view.BookingView;
import com.cinemaBook.view.EditBookingsView;
import com.cinemaBook.view.MainView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        if (true) {
            // Create database mock up
            MockDatabase mockUp = new MockDatabase();
            mockUp.createDatabaseMockUp();
        }

        // EXAMPLE OF GETTING SCREENINGS
        if (false) {
            // To get all screenings pass "-1"
            ArrayList<Screening> screenings = ScreeningsDataHandler.getInstance().getScreenings(-1);

            // Pass the id of a screening to get an ArrayList<Screening> only containing said screening
            Screening screening = ScreeningsDataHandler.getInstance().getScreenings(2).get(0); // Use .get(0) to get the desired screening)
        }

        // EXAMPLE OF GETTING ALL BOOKINGS
        if (false) {
            ArrayList<Booking> bookings = BookingsDataHandler.getInstance().getBookings();
        }

        // EXAMPLE OF GETTING BOOKINGS BY CUSTOMER
        if (false) {
            Customer customer = new Customer("Lasse Agersten", "123", "hej@gmail.com");

            ArrayList<Booking> bookings = BookingsDataHandler.getInstance().getBookingsByCustomer(customer); // will return null if no bookings are found
        }

        // EXAMPLE OF SUBMITTING A BOOKING
        if (false) {
            // Create a user to connect to booking to
            Customer customer = new Customer("Test navn", "+45 12345678", "test@gmail.com");

            // Fetch the screening the booking is related to
            Screening screening = ScreeningsDataHandler.getInstance().getScreenings(1).get(0);

            // Create a list of new seats to reserve instead
            ArrayList<Seat> reservedSeats = new ArrayList<>();
            reservedSeats.add(new Seat(1, 1, true));
            reservedSeats.add(new Seat(1, 2, true));

            // Create the booking
            Booking booking = new Booking(customer, screening, reservedSeats);

            // Submit the booking
            BookingsDataHandler.getInstance().submitBooking(booking);
        }

        // EXAMPLE OF UPDATING A BOOKING
        if (false) {
            // Create a list of new seats to reserve instead
            ArrayList<Seat> newReservedSeats = new ArrayList<>();
            newReservedSeats.add(new Seat(6, 5, true));
            newReservedSeats.add(new Seat(6, 6, true));

            // Pass the booking id along with the new reserved seats
            BookingsDataHandler.getInstance().updateBooking(1, newReservedSeats);
        }

        // EXAMPLE OF DELETING A BOOKING
        if (false) {
            // Pass the booking id of the booking to be deleted
            BookingsDataHandler.getInstance().deleteBooking(2);
        }

        // Getting the tabPane from the main view
        JTabbedPane tabPane = MainView.getInstance().getTabPane();

        // Setting up Booking View
        Screenings screenings = new Screenings();


        // Setting up Edit Booking View
        Bookings bookings = new Bookings();

        EditBookingsView editBookingsView = new EditBookingsView();

        EditBookingController editBookingController = new EditBookingController(editBookingsView, bookings, screenings);

        editBookingController.display();


        BookingView bookingView = new BookingView();


        BookingController bookingController = new BookingController(bookingView, screenings);

        bookingController.display();

        // Add Views to the tabPane
        tabPane.addTab("Book", bookingView);

        //tabPane.addTab("Edit booking", );
        tabPane.addTab("Edit booking", editBookingsView);

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabPane.getSelectedIndex() == 0) {
                    bookingController.reset();
                } else {
                    editBookingController.reset();
                }
            }
        };

        tabPane.addChangeListener(changeListener);

    }
}
