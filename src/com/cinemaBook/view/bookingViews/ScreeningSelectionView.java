package com.cinemaBook.view.bookingViews;

import com.cinemaBook.utils.DateFormatter;
import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Screenings;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.function.Function;

public class ScreeningSelectionView extends JComponent{
    private String filter;

    public ScreeningSelectionView() {
        super();
        filter = "";
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void display(Screenings screenings, Function<Screening, Void> success) {
        removeAll();

        JTextField filterField = new JTextField(filter);
        //filter.setMaximumSize(new Dimension(getWidth(), 30));
        filterField.addActionListener(e -> {
            System.out.println(filterField.getText());
        });

        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                filter = filterField.getText();
                display(screenings, success);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                filter = filterField.getText();
                display(screenings, success);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                filter = filterField.getText();
                display(screenings, success);
            }
        });

        add(filterField);

        filterField.requestFocus();

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
        screenings.getScreenings().stream().filter(screening -> screening.getFilm().getName().toLowerCase().contains(filter.toLowerCase())).forEach(screening -> {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(screening.getStartTime());
            tableModel.addRow(new Object[]{
                screening.getFilm().getName(),
                new DateFormatter(screening.getStartTime()).str(),
                screening.getAuditorium().getName(),
                Integer.toString(screening.getFilm().getMinAge()),
                screening.getSeatAssignment().getAmountOfAvailableSeats()
            });
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
