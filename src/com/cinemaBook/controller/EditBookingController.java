package com.cinemaBook.controller;

import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screenings;
import com.cinemaBook.model.Seat;
import com.cinemaBook.model.SeatAssignment;
import com.cinemaBook.view.EditBookingsView;

import java.util.ArrayList;

public class EditBookingController {
    private final static String EditBooking = "EditBooking";

    private EditBookingsView view;
    private Screenings screenings;
    private int screeningId;
    private ArrayList<Seat> seats;
    private Customer customer;
    private SeatAssignment seatAssignment;

    public EditBookingController(EditBookingsView view) {
        this.view = view;
    }




}
