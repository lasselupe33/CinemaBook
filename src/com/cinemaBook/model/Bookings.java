package com.cinemaBook.model;

import com.cinemaBook.globals.DataHandler;

import java.util.ArrayList;

public class Bookings {
    private ArrayList<Booking> bookings;

    public Bookings() {
        this.bookings = DataHandler.getInstance().getBookings();
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }



}
