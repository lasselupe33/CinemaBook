package com.cinemaBook.controller;

import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screenings;
import com.cinemaBook.view.BookingView;

public class BookingController {
    private final static String ScreeningSelection = "ScreeningSelectionView";
    private final static String CustomerInput = "CustomerInputView";
    private final static String SeatSelection = "SeatSelectionView";

    private BookingView view;
    private Screenings screenings;
    private String currentView;
    private int screeningId;
    private Customer customer;

    public BookingController(BookingView view, Screenings screenings) {
        this.view = view;
        this.screenings = screenings;
        this.currentView = ScreeningSelection;
        this.screeningId = -1;
    }

    public void reset() {
        currentView = ScreeningSelection;
        screeningId = -1;
        customer = null;
        display();
    }

    public void display() {
        view.display(screenings, currentView, id -> {
            this.screeningId = id;
            this.currentView = CustomerInput;
            display();
            return null;
        }, screeningId, e -> {
            reset();
            return null;
        }, customer -> {
            this.customer = customer;
            this.currentView = SeatSelection;
            display();
            return null;
        });
    }
}
