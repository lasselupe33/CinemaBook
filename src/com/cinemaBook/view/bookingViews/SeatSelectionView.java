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

    private AuditoriumView auditoriumView;

    public SeatSelectionView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        seats = new ArrayList<>();
    }

    private void reset() {
        seats = new ArrayList<>();
    }

    public void display(Screening screening, Function<Void, Void> onCancel, Function<ArrayList<Seat>, Void> onSubmit) {
        removeAll();

        JLabel filmLabel = new JLabel(screening.getFilm().getName());

        add(filmLabel);

        auditoriumView = new AuditoriumView(seat -> {
            seats.add(seat);
            auditoriumView.display(screening.getAuditorium(), screening.getSeatAssignment(), seats);
            return null;
        }, index -> {
            seats.remove((int) index);
            auditoriumView.display(screening.getAuditorium(), screening.getSeatAssignment(), seats);
            return null;
        });

        auditoriumView.display(screening.getAuditorium(), screening.getSeatAssignment(), seats);

        add(auditoriumView);

        JPanel navigationPanel = new JPanel(new FlowLayout());

        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> {
            reset();
            onCancel.apply(null);
        });

        navigationPanel.add(cancelButton);

        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            onSubmit.apply(seats);
            reset();
        });

        navigationPanel.add(nextButton);

        navigationPanel.setSize(getWidth(), 30);

        add(navigationPanel);
    }
}
