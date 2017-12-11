package com.cinemaBook.view.bookingViews;

import com.cinemaBook.controller.BookingController;
import com.cinemaBook.globals.DateFormatter;
import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Screenings;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.function.Function;

public class ScreeningSelectionView extends JComponent{
    public ScreeningSelectionView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void display(Screenings screenings, Function<Screening, Void> success) {
        removeAll();

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Title");
        tableModel.addColumn("Time");
        tableModel.addColumn("Auditorium");
        tableModel.addColumn("Minimum age");
        tableModel.addColumn("Seats left");

        screenings.getScreenings().sort(new Comparator<Screening>() {
            @Override
            public int compare(Screening screening, Screening t1) {
                return screening.getStartTime().compareTo(t1.getStartTime());
            }
        });
        screenings.getScreenings().forEach(screening -> {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(screening.getStartTime());
            tableModel.addRow(new Object[]{screening.getFilm().getName(), new DateFormatter(screening.getStartTime()).str(), screening.getAuditorium().getName(), Integer.toString(screening.getFilm().getMinAge()), screening.getSeatAssignment().getAmountOfAvailableSeats()});
        });

        JTable table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {//This line prevents double events
                    success.apply(screenings.getScreenings().get(e.getFirstIndex()));
                }
            }
        });

        add(new JScrollPane(table));
    }
}
