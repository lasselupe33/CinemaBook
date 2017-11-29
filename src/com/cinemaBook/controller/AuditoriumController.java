package com.cinemaBook.controller;

import com.cinemaBook.view.AuditoriumView;
import com.cinemaBook.view.MainPane;

public class AuditoriumController extends Controller {
    private AuditoriumView view;

    public AuditoriumController(AuditoriumView view) {
        this.view = view;
    }

    public void initialize() {
        MainPane.getInstance().swapComponent(view);
    }
}
