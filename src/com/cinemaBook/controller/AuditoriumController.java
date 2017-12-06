package com.cinemaBook.controller;

import com.cinemaBook.view.AuditoriumView;
import com.cinemaBook.view.MainPane;

public class AuditoriumController extends Controller {
    private AuditoriumView view;

    public AuditoriumController() {
        super();
    }

    public void initialize() {
        this.view = new AuditoriumView();
        MainPane.getInstance().swapComponent(view);
    }
}
