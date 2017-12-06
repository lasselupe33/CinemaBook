package com.cinemaBook.view;

import com.cinemaBook.model.Screenings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookingSelectionView extends JComponent{
    public void display(Screenings screenings) {

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Title");
        tableModel.addColumn("Time");
        tableModel.addColumn("Auditorium");
        tableModel.addColumn("Minimum age");

        screenings.getScreenings().forEach(screening ->
            tableModel.addRow(new Object[] {screening.getFilmPlaying().getName(), screening.getStartTime(), screening.getAuditorium().getName(), screening.getFilmPlaying().getMinAge()})
        );

        JTable table = new JTable();

        add(table);
    }
}
