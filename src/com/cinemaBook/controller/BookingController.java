package com.cinemaBook.controller;

import com.cinemaBook.globals.DataHandler;
import com.cinemaBook.model.Booking;
import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screenings;
import com.cinemaBook.model.Seat;
import com.cinemaBook.view.BookingView;

import java.util.ArrayList;

public class BookingController {
    private final static String ScreeningSelection = "ScreeningSelectionView";
    private final static String CustomerInput = "CustomerInputView";
    private final static String SeatSelection = "SeatSelectionView";

    private BookingView view;
    private Screenings screenings;
    private String currentView;
    private int screeningId;
    private ArrayList<Seat> seats;
    private Customer customer;

    public BookingController(BookingView view, Screenings screenings) {
        this.view = view;
        this.screenings = screenings;
        this.currentView = ScreeningSelection;
        this.screeningId = -1;
    }

    private void reset() {
        currentView = ScreeningSelection;
        screeningId = -1;
        customer = null;
        seats = new ArrayList<>();
        screenings = new Screenings(DataHandler.getInstance().getScreenings(-1));
        display();
    }

    public void display() {
        view.display(screenings, currentView, id -> {
            this.screeningId = id;
            this.currentView = SeatSelection;
            display();
            return null;
        }, screeningId, e -> {
            reset();
            return null;
        }, customer -> {
            this.customer = customer;
            System.out.println(customer);
            System.out.println(seats);
            System.out.println(screenings.find(s -> s.getId() == screeningId));
            try {
                DataHandler.getInstance().submitBooking(new Booking(customer, screenings.find(s -> s.getId() == screeningId), seats));
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
            reset();
            display();
            return null;
        }, seats -> {
            this.seats = seats;
            this.currentView = CustomerInput;
            display();
            return null;
        });
    }
}
