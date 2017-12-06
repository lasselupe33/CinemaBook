package com.cinemaBook.bootstrap;

import com.cinemaBook.controller.BookingController;
import com.cinemaBook.globals.DataHandler;
import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Screenings;
import com.cinemaBook.view.BookingView;
import com.cinemaBook.view.MainView;

import javax.swing.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        if (false) {
            // Create database mock up
            MockDatabase mockUp = new MockDatabase();
            mockUp.createDatabaseMockUp();
        }

        // Getting data from the database
        DataHandler dataHandler = DataHandler.getInstance();
        ArrayList<Screening> screeningList = dataHandler.getScreenings();

        // Getting the tabPane from the main view
        JTabbedPane tabPane = MainView.getInstance().getTabPane();

        // Setting up Booking View
        Screenings screenings = new Screenings(screeningList);

        BookingView bookingView = new BookingView();

        BookingController bookingController = new BookingController(bookingView, screenings);

        bookingController.display();

        // Add Views to the tabPane
        tabPane.addTab("Book", bookingView);

        //tabPane.addTab("Edit booking", );

    }
}
