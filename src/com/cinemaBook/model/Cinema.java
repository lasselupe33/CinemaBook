package com.cinemaBook.model;

import java.util.ArrayList;

public class Cinema {
    private String name;
    private ArrayList<Auditorium> auditoriums;
    private ArrayList<Screening> screenings;

    /**
     * Set the name of the cinema
     */
    public Cinema(ArrayList<Auditorium> auditoriums, ArrayList<Screening> screenings) {
        this.name = "Test biograf";
        this.auditoriums = new ArrayList<>();
        this.screenings = new ArrayList<>();
    }
}
