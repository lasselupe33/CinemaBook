package com.cinemaBook.controller;

import com.cinemaBook.globals.DataHandler;
import com.cinemaBook.globals.State;
import com.cinemaBook.model.Screening;
import com.cinemaBook.view.MainPane;
import com.cinemaBook.view.ScreeningsView;
import com.cinemaBook.view.View;

import java.util.ArrayList;

public class ScreeningsController extends Controller {
    private ArrayList<Screening> screenings;
    private ScreeningsView view;

    /**
     * Sets up the ScreeningsController with the needed view and models
     */
    public ScreeningsController() {
        this.screenings = DataHandler.getInstance().getScreenings(-1);
    }

    public void initialize() {
        // Create the view
        this.view = new ScreeningsView();

        // Swap to the this view
        MainPane.getInstance().swapComponent(view);
    }

    public void setSelectedScreening(int id) {
        State.selectedScreening = this.screenings.get(id);
    }
}
