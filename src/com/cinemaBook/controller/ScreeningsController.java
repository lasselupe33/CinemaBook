package com.cinemaBook.controller;

import com.cinemaBook.model.Screening;
import com.cinemaBook.view.MainPane;
import com.cinemaBook.view.ScreeningsView;
import com.cinemaBook.view.View;

public class ScreeningsController extends Controller {
    private Screening model;
    private ScreeningsView view;

    /**
     * Sets up the ScreeningsController with the corresponding model and view.
     * @param model
     * @param view
     */
    public ScreeningsController(Screening model, View view) {
        this.model = model;
        this.view = (ScreeningsView) view;
    }

    public void initialize() {
        MainPane.getInstance().swapComponent(view);
    }
}
