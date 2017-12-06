package com.cinemaBook.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This model contains information about a given booking
 */
public class Booking {
    private Customer customer;
    private Screening screening;
    private ArrayList<Integer[]> reservedSeats;

    public Booking(Customer customer, Screening screening, ArrayList<Integer[]> reservedSeats) {
        this.customer = customer;
        this.screening = screening;
        this.reservedSeats = reservedSeats;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Screening getScreening() {
        return screening;
    }

    public ArrayList<Integer[]> getReservedSeats() {
        return reservedSeats;
    }
}
