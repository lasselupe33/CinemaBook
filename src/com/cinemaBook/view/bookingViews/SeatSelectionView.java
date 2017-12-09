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

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Title");
        tableModel.addColumn("Time");
        tableModel.addColumn("Auditorium");
        tableModel.addColumn("Minimum age");
        tableModel.addColumn("Seats left");

        tableModel.addRow(new Object[]{screening.getFilm().getName(), screening.getStartTime(), screening.getAuditorium().getName(), Integer.toString(screening.getFilm().getMinAge()), screening.getSeatAssignment().getAmountOfAvailableSeats()});

        JTable table = new JTable(tableModel);

        add(table);

        // Add auditorium view
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

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.LINE_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        navigationPanel.add(Box.createHorizontalGlue());

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
