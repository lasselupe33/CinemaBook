package com.cinemaBook.controller;

import com.cinemaBook.model.*;
import com.cinemaBook.view.EditBookingsView;

public class EditBookingController {
    private final static String EditBooking = "EditBooking";

    private EditBookingsView view;
    private Bookings bookings;

    private Booking selectedBooking;


    public EditBookingController(EditBookingsView view, Bookings bookings) {
        this.view = view;
        this.bookings = bookings;
    }

    public void reset() {
        selectedBooking = null;
        bookings = new Bookings();
        display();
    }

    public Bookings getBookings() {
        return bookings;
    }


    public void display() {
        view.display(this);

    }


    public void setSelectedBooking(Booking selectedBooking) {
        this.selectedBooking = selectedBooking;
    }

    public Booking getSelectedBooking() {
        return selectedBooking;
    }

    public void deleteBooking() {
        if (selectedBooking != null) {
            selectedBooking.delete();
            reset();
        }
    }



    public void editBooking() {

    }

}
