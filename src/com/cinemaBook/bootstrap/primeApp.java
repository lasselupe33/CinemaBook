package com.cinemaBook.bootstrap;

import com.cinemaBook.globals.DataHandler;
import com.cinemaBook.globals.Router;
import com.cinemaBook.globals.ViewTypes;
import com.cinemaBook.model.Booking;
import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screening;

import java.util.ArrayList;
import java.util.HashMap;

public class primeApp {
    public static void main(String[] args) {
        if (false) {
            // Create database mock up
            MockDatabase mockUp = new MockDatabase();
            mockUp.createDatabaseMockUp();
        }

        // EXAMPLE OF BOOKING SUBMISSION
        if (true) {
            ArrayList<Integer[]> reservations = new ArrayList<>();

            Integer[] reservation = new Integer[2];
            reservation[0] = 5;
            reservation[1] = 3;
            reservations.add(reservation);

            Integer[] reservation2 = new Integer[2];
            reservation2[0] = 6;
            reservation2[1] = 4;
            reservations.add(reservation2);

            Booking booking = new Booking(new Customer("Lasse Agersten", "123", "hej@gmail.com"), DataHandler.getInstance().getScreenings().get(0), reservations);
            DataHandler.getInstance().submitBooking(booking);
        }
        // END EXAMPLE

        // default to ScreeningsView
        if (false) {
            Router.getInstance().updateLocation(ViewTypes.ScreeningsView);
        }
    }
}