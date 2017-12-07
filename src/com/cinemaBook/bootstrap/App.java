package com.cinemaBook.bootstrap;

import com.cinemaBook.controller.BookingController;
import com.cinemaBook.globals.DataHandler;
import com.cinemaBook.model.*;
import com.cinemaBook.view.BookingView;
import com.cinemaBook.view.MainView;

import javax.swing.*;
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
            ArrayList<Screening> screenings = DataHandler.getInstance().getScreenings(-1);

            // Pass the id of a screening to get an ArrayList<Screening> only containing said screening
            Screening screening = DataHandler.getInstance().getScreenings(2).get(0); // Use .get(0) to get the desired screening)
        }

        // EXAMPLE OF GETTING BOOKINGS BY CUSTOMER
        if (false) {
            Customer customer = new Customer("Lasse Agersten", "123", "hej@gmail.com");

            ArrayList<Booking> bookings = DataHandler.getInstance().getBookingsByCustomer(customer); // will return null if no bookings are found
        }

        // EXAMPLE OF SUBMITTING A BOOKING
        if (false) {
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
        if (false) {
            // Create a list of new seats to reserve instead
            ArrayList<Seat> newReservedSeats = new ArrayList<>();
            newReservedSeats.add(new Seat(6, 5, true));
            newReservedSeats.add(new Seat(6, 6, true));

            // Pass the booking id along with the new reserved seats
            DataHandler.getInstance().updateBooking(1, newReservedSeats);
        }

        // EXAMPLE OF DELETING A BOOKING
        if (false) {
            // Pass the booking id of the booking to be deleted
            DataHandler.getInstance().deleteBooking(2);
        }

        // Getting data from the database
        DataHandler dataHandler = DataHandler.getInstance();
        ArrayList<Screening> screeningList = dataHandler.getScreenings(-1);

        System.out.println(screeningList);

        // Getting the tabPane from the main view
        JTabbedPane tabPane = MainView.getInstance().getTabPane();

        // Setting up Booking View
        Screenings screenings = new Screenings(screeningList);

        BookingView bookingView = new BookingView();

        BookingController bookingController = new BookingController(bookingView, screenings);

        bookingController.display();

        // Add Views to the tabPane
        tabPane.addTab("Book", bookingView);

        //tabPane.addTab("Edit booking", );

    }
}
