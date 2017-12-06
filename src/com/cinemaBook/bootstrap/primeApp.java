package com.cinemaBook.bootstrap;

import com.cinemaBook.globals.DataHandler;
import com.cinemaBook.globals.Router;
import com.cinemaBook.globals.ViewTypes;
import com.cinemaBook.model.Screening;

import java.util.ArrayList;

public class primeApp {
    public static void main(String[] args) {
        if (true) {
            // Create database mock up
            MockDatabase mockUp = new MockDatabase();
            mockUp.createDatabaseMockUp();
        }

        Screening test = DataHandler.getInstance().getScreenings().get(0);
        System.out.println(test.getStartTime());

        // default to ScreeningsView
        if (false) {
            Router.getInstance().updateLocation(ViewTypes.ScreeningsView);
        }
    }
}
