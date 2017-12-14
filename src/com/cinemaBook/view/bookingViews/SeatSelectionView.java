package com.cinemaBook.view.bookingViews;

import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Seat;
import com.cinemaBook.model.SeatAssignment;
import com.cinemaBook.utils.DateFormatter;
import com.cinemaBook.view.AuditoriumView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    public void reset() {
        seats = new ArrayList<>();
    }

    private SeatAssignment removeDuplicateSeats(SeatAssignment assignment, ArrayList<Seat> seats) {
        SeatAssignment newAssignment = new SeatAssignment(assignment.getSeats());

        seats.forEach(seat -> {
            newAssignment.setSeat(seat.getRow(), seat.getColumn(), false);
        });

        return newAssignment;
    }

    public void display(Screening screening, ArrayList<Seat> initSeats, Function<Void, Void> cancel, Function<ArrayList<Seat>, Void> success) {
        removeAll();

        seats = initSeats;

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Title");
        tableModel.addColumn("Time");
        tableModel.addColumn("Auditorium");

        tableModel.addRow(new Object[]{
            screening.getFilm().getName(),
            new DateFormatter(screening.getStartTime()).str(),
            screening.getAuditorium().getName(),
        });

        JTable table = new JTable(tableModel);

        add(table);

        SeatAssignment assignment = removeDuplicateSeats(screening.getSeatAssignment(), seats);

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
            cancel.apply(null);
        });

        navigationPanel.add(cancelButton);

        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            success.apply(seats);
            reset();
        });

        navigationPanel.add(nextButton);

        navigationPanel.setSize(getWidth(), 30);

        add(navigationPanel);
    }
}
