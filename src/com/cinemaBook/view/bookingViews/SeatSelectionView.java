package com.cinemaBook.view.bookingViews;

import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Screenings;
import com.cinemaBook.model.Seat;
import com.cinemaBook.view.AuditoriumView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

public class SeatSelectionView extends JComponent{
    private ArrayList<Seat> seats;

    public SeatSelectionView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        seats = new ArrayList<>();
    }

    public void display(Screening screening, Function<Void, Void> onCancel, Function<ArrayList<Seat>, Void> onSubmit) {
        removeAll();

        JLabel filmLabel = new JLabel(screening.getFilm().getName());

        add(filmLabel);

        AuditoriumView auditoriumView = new AuditoriumView();

        auditoriumView.display(screening.getAuditorium(), screening.getSeatAssignment(), seats);

        add(auditoriumView);

        JPanel navigationPanel = new JPanel(new FlowLayout());

        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> {
            onCancel.apply(null);
        });

        navigationPanel.add(cancelButton);

        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            onSubmit.apply(seats);
        });

        navigationPanel.add(nextButton);

        navigationPanel.setSize(getWidth(), 40);

        add(navigationPanel);
    }
}
