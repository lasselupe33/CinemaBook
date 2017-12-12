package com.cinemaBook.model;

import com.cinemaBook.globals.DataHandler;

import java.util.ArrayList;
import java.util.function.Function;

public class Screenings {
    private ArrayList<Screening> screenings;

    public Screenings() {
        this.screenings = DataHandler.getInstance().getScreenings(-1);
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