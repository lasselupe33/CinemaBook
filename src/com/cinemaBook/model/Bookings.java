package com.cinemaBook.model;

import java.util.ArrayList;
import java.util.function.Function;

public class Bookings {
    private ArrayList<Booking> bookings;

    public Bookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
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
