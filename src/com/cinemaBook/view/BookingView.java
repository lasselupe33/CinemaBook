package com.cinemaBook.view;

import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Screenings;

import javax.swing.*;
import java.util.ArrayList;

public class BookingView extends JComponent {

    public void display(Screenings screenings) {
        new BookingSelectionView().display(screenings);
    }
}
