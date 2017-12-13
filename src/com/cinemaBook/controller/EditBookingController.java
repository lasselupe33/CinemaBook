package com.cinemaBook.controller;

import com.cinemaBook.data.CustomerDataHandler;
import com.cinemaBook.model.*;
import com.cinemaBook.view.EditBookingsView;

import java.util.ArrayList;

public class EditBookingController {
    private final static String BookingSelection = "BookingSelectionView";
    private final static String ScreeningSelection = "ScreeningSelectionView";
    private final static String CustomerInput = "CustomerInputView";
    private final static String SeatSelection = "SeatSelectionView";

    private EditBookingsView view;
    private Bookings bookings;
    private Screenings screenings;

    private String currentView;
    private Booking selectedBooking;


    public EditBookingController(EditBookingsView view, Bookings bookings, Screenings screenings) {
        this.view = view;
        this.bookings = bookings;
        this.screenings = screenings;
        this.currentView = BookingSelection;
    }

    public void reset() {
        currentView = BookingSelection;
        selectedBooking = null;
        bookings = new Bookings();
        display();
    }

    public Bookings getBookings() {
        return bookings;
    }

    public String getCurrentView() {
        return currentView;
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

    public void display() {
        view.display(this);
    }

    public void editSeats() {
        this.currentView = SeatSelection;
        display();
    }

    public void submitSeats(ArrayList<Seat> seats) {
        try {
            this.selectedBooking.setReservedSeats(seats);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        reset();
    }

    public void editCustomer() {
        this.currentView = CustomerInput;
        display();
    }

    public void submitCustomer(Customer customer) {
        CustomerDataHandler.getInstance().editCustomer(selectedBooking.getCustomer(), customer);
        reset();
    }

}
