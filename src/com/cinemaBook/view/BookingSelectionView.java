package com.cinemaBook.view;

import com.cinemaBook.model.Screenings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookingSelectionView extends JComponent{
    public BookingSelectionView() {
        super();
        setLayout(new FlowLayout());
    }

    public void display(Screenings screenings) {

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Title");
        tableModel.addColumn("Time");
        tableModel.addColumn("Auditorium");
        tableModel.addColumn("Minimum age");

        screenings.getScreenings().forEach(screening -> {
            tableModel.addRow(new Object[]{screening.getFilm().getName(), screening.getStartTime(), screening.getAuditorium().getName(), Integer.toString(screening.getFilm().getMinAge())});
        });

        JTable table = new JTable(tableModel);

        add(table);
    }
}
