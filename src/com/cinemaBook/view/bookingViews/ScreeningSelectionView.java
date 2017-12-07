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
        setLayout(new FlowLayout());
    }

    public void display(Screenings screenings, Function<Integer, Void> func) {

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Title");
        tableModel.addColumn("Time");
        tableModel.addColumn("Auditorium");
        tableModel.addColumn("Minimum age");

        screenings.getScreenings().forEach(screening -> {
            tableModel.addRow(new Object[]{screening.getFilm().getName(), screening.getStartTime(), screening.getAuditorium().getName(), Integer.toString(screening.getFilm().getMinAge())});
        });

        JTable table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                System.out.println(listSelectionEvent.getFirstIndex());
                func.apply(listSelectionEvent.getFirstIndex());
            }
        });

        add(table);
    }
}
