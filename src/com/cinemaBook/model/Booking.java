package com.cinemaBook.model;

/**
 * This model contains information about a given booking
 */
public class Booking {
    private int id;
    private Customer customer;
    private Screening screening;

    public Booking(int bookingId, Customer customer, Screening screening) {
        this.id = bookingId;
        this.screening = screening;
        this.customer = customer;
    }
}
