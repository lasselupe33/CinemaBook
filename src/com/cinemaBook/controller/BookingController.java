package com.cinemaBook.controller;

import com.cinemaBook.model.*;
import com.cinemaBook.view.BookingView;

import javax.swing.*;
import java.util.ArrayList;

public class BookingController {
    private final static String ScreeningSelection = "ScreeningSelectionView";
    private final static String CustomerInput = "CustomerInputView";
    private final static String SeatSelection = "SeatSelectionView";

    private BookingView view;
    private Screenings screenings;
    private String currentView;
    private Screening selectedScreening;
    private ArrayList<Seat> seats;

    public BookingController(BookingView view, Screenings screenings) {
        this.view = view;
        this.screenings = screenings;
        this.currentView = ScreeningSelection;
    }

    public void reset() {
        currentView = ScreeningSelection;
        seats = new ArrayList<>();
        screenings = new Screenings();
        view.reset();
        display();
    }

    public Screenings getScreenings() {
        return screenings;
    }

    public String getCurrentView() {
        return currentView;
    }

    public Screening getSelectedScreening() {
        return selectedScreening;
    }

    public void onScreeningSelected(Screening screening) {
        this.selectedScreening = screening;
        this.currentView = SeatSelection;
        display();
    }

    public void onCustomerSubmit(Customer customer) {
        try {
            new Booking(customer, selectedScreening, seats).addToDB();
        } catch (Error e) {
            JOptionPane.showMessageDialog(view,
                    "Error creating booking",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        reset();
        display();
    }

    public void onSeatSubmit(ArrayList<Seat> seats) {
        this.seats = seats;
        this.currentView = CustomerInput;
        display();
    }

    public void display() {
        view.display(this);
    }
}
