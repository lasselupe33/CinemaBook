package com.cinemaBook.model;

import com.cinemaBook.globals.DataHandler;

import java.util.ArrayList;

/**
 * This model contains information about a given booking
 */
public class Booking {
    private int id;
    private Customer customer;
    private Screening screening;
    private ArrayList<Seat> reservedSeats;

    public Booking(Customer customer, Screening screening, ArrayList<Seat> reservedSeats) {
        this.customer = customer;
        this.screening = screening;
        this.reservedSeats = reservedSeats;
    }

    public void setId(int id) { this.id = id; }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Screening getScreening() {
        return screening;
    }

    public ArrayList<Seat> getReservedSeats() {
        return reservedSeats;
    }

    public void addToDB() {
        DataHandler.getInstance().submitBooking(this);
    }

    public void delete() {
        DataHandler.getInstance().deleteBooking(id);
    }
}
