package com.cinemaBook.view;

import com.cinemaBook.model.Screenings;

import javax.swing.*;
import java.awt.*;

public class BookingView extends JComponent {
    public BookingView() {
        super();
        setLayout(new FlowLayout());
    }

    public void display(Screenings screenings) {
        removeAll();
        BookingSelectionView bookingSelectionView = new BookingSelectionView();
        add(bookingSelectionView);
        bookingSelectionView.display(screenings);
    }
}
