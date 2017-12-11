package com.cinemaBook.model;

import java.util.ArrayList;
import java.util.function.Function;

public class Screenings {
    private ArrayList<Screening> screenings;

    public Screenings(ArrayList<Screening> screenings) {
        this.screenings = screenings;
    }

    public ArrayList<Screening> getScreenings() {
        return screenings;
    }

    public Screening find(Function<Screening, Boolean> func) {
        for (Screening screening: screenings) {
            if (func.apply(screening)) {
                return screening;
            }
        }
        return null;
    }
}