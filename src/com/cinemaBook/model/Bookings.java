package com.cinemaBook.model;

import com.cinemaBook.data.BookingsDataHandler;

import java.util.ArrayList;

public class Bookings {
    private ArrayList<Booking> bookings;

    public Bookings() {
        this.bookings = BookingsDataHandler.getInstance().getBookings();
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }



}
