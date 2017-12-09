package com.cinemaBook.view.bookingViews;

import com.cinemaBook.model.Screenings;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.Function;

public class ScreeningSelectionView extends JComponent{
    public ScreeningSelectionView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void display(Screenings screenings, Function<Integer, Void> onSubmit) {
        removeAll();

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Title");
        tableModel.addColumn("Time");
        tableModel.addColumn("Auditorium");
        tableModel.addColumn("Minimum age");
        tableModel.addColumn("Seats left");

        screenings.getScreenings().forEach(screening -> {
            tableModel.addRow(new Object[]{screening.getFilm().getName(), screening.getStartTime(), screening.getAuditorium().getName(), Integer.toString(screening.getFilm().getMinAge()), screening.getSeatAssignment().getAmountOfAvailableSeats()});
        });

        JTable table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                onSubmit.apply(screenings.getScreenings().get(listSelectionEvent.getFirstIndex()).getId());
            }
        });

        add(new JScrollPane(table));
    }
}
