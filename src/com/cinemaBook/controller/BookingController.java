package com.cinemaBook.controller;

import com.cinemaBook.model.Screenings;
import com.cinemaBook.view.BookingView;

public class BookingController {
    private BookingView view;
    private Screenings screenings;

    public BookingController(BookingView view, Screenings screenings) {
        this.view = view;
        this.screenings = screenings;
    }

    public void display() {
        view.display(screenings);
    }
}
