package com.cinemaBook.controller;

import com.cinemaBook.model.Screenings;
import com.cinemaBook.view.BookingView;

public class BookingController {
    final static String ScreeningSelection = "ScreeningSelectionView";
    final static String CustomerInput = "CustomerInputView";
    final static String SeatSelection = "SeatSelectionView";

    private BookingView view;
    private Screenings screenings;
    private String currentView;
    private int screeningId;

    public BookingController(BookingView view, Screenings screenings) {
        this.view = view;
        this.screenings = screenings;
        this.currentView = ScreeningSelection;
        this.screeningId = -1;
    }

    public void display() {
        view.display(screenings, currentView, id -> {
            this.screeningId = id;
            this.currentView = CustomerInput;
            display();
            return null;
        }, screeningId);
    }
}
