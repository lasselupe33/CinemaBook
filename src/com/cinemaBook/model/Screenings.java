package com.cinemaBook.model;

import java.util.ArrayList;

public class Screenings {
    private ArrayList<Screening> screenings;

    public Screenings(ArrayList<Screening> screenings) {
        this.screenings = screenings;
    }

    public ArrayList<Screening> getScreenings() {
        return screenings;
    }
}