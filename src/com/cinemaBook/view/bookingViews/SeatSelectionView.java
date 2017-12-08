package com.cinemaBook.view.bookingViews;

import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Screenings;
import com.cinemaBook.view.AuditoriumView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.Function;

public class SeatSelectionView extends JComponent{
    public SeatSelectionView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void display(Screening screening) {
        removeAll();

        JLabel filmLabel = new JLabel(screening.getFilm().getName());

        add(filmLabel);

        AuditoriumView auditoriumView = new AuditoriumView();

        auditoriumView.display(screening.getAuditorium(), screening.getSeatAssignment());

        add(auditoriumView);
    }
}
