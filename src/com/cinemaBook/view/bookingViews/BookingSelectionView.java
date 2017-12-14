package com.cinemaBook.view.bookingViews;

import com.cinemaBook.controller.EditBookingController;
import com.cinemaBook.model.Booking;
import com.cinemaBook.utils.DateFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookingSelectionView extends JComponent {

    public BookingSelectionView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    public void display(EditBookingController c) {
        removeAll();

        // Create table
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

        // Set listener on table
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                c.setSelectedBooking(c.getBookings().getBookings().get(e.getFirstIndex()));
            }
        });

        // Create the naviagtion panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.LINE_AXIS));

        // Add button to edit film
        JButton editFilmButton = new JButton("Edit film");

        editFilmButton.addActionListener(e -> {
            c.editFilm();
        });

        navigationPanel.add(editFilmButton);

        // Add button to edit seat selection
        JButton editSeatsButton = new JButton("Edit seats");

        editSeatsButton.addActionListener(e -> {
            c.editSeats();
        });

        navigationPanel.add(editSeatsButton);

        // Add button to edit customer info
        JButton editCustomerButton = new JButton("Edit Customer information");

        editCustomerButton.addActionListener(e -> {
            c.editCustomer();
        });

        navigationPanel.add(editCustomerButton);

        // Add button to delete booking
        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            if (c.getSelectedBooking() != null) {
                c.deleteBooking();
            }
        });

        navigationPanel.add(deleteButton);

        // Add navigation panel
        add(navigationPanel);
    }
}
