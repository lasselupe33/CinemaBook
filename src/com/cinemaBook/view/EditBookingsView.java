package com.cinemaBook.view;


import com.cinemaBook.controller.EditBookingController;
import com.cinemaBook.utils.DateFormatter;
import com.cinemaBook.model.Booking;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class EditBookingsView extends JComponent {
    private JPanel panel;

    JButton button;

    public EditBookingsView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

         }

        public void display(EditBookingController c) {
        removeAll();

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Navn");
        tableModel.addColumn("Email");
        tableModel.addColumn("Tlf");
        tableModel.addColumn("Film");
        tableModel.addColumn("Tidspunkt");
        tableModel.addColumn("Antal Sæder");

        for (Booking booking: c.getBookings().getBookings()) {
            tableModel.addRow(new Object[]{
                booking.getCustomer().getName(),
                booking.getCustomer().getEmail(),
                booking.getCustomer().getPhone(),
                booking.getScreening().getFilm().getName(),
                new DateFormatter(booking.getScreening().getStartTime()).str(),
                booking.getReservedSeats().size(),
            });
        }

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table));

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    c.setSelectedBooking(c.getBookings().getBookings().get(e.getFirstIndex()));
                }
            }
        });

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.LINE_AXIS));

        JButton editButton = new JButton("Edit");

        editButton.addActionListener(e -> {
            // stuff to do when editing
        });

        navigationPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            if (c.getSelectedBooking() != null) {
                c.deleteBooking();
            }
            //delete stuff
        });

        navigationPanel.add(deleteButton);

        add(navigationPanel);
    }

}




