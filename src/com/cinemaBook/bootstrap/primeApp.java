package com.cinemaBook.bootstrap;

import com.cinemaBook.globals.Router;
import com.cinemaBook.globals.ViewTypes;

public class primeApp {
    public static void main(String[] args) {
        if (false) {
            // Create database mock up
            MockDatabase mockUp = new MockDatabase();
            mockUp.createDatabaseMockUp();
        }

        // default to ScreeningsView
        Router router = Router.getInstance();
        router.updateLocation(ViewTypes.ScreeningsView);
    }
}
