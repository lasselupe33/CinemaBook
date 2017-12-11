package com.cinemaBook.model;

import com.cinemaBook.globals.DataHandler;

import java.util.ArrayList;
import java.util.function.Function;

public class Bookings {
    private ArrayList<Booking> bookings;

    public Bookings() {
        this.bookings = DataHandler.getInstance().getBookings();
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public Booking find(Function<Booking, Boolean> func) {
        for (Booking booking: bookings) {
            if (func.apply(booking)) {
                return booking;
            }
        }
        return null;
    }
}
