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

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                c.setSelectedBooking(c.getBookings().getBookings().get(e.getFirstIndex()));
            }
        });

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.LINE_AXIS));





        JButton editFilmButton = new JButton("Edit film");

        editFilmButton.addActionListener(e -> {
            // stuff to do when editing film
        });

        navigationPanel.add(editFilmButton);


        JButton editSeatsButton = new JButton("Edit seats");

        editSeatsButton.addActionListener(e -> {
            c.editSeats();
        });

        navigationPanel.add(editSeatsButton);




        JButton editCustomerButton = new JButton("Edit Customer information");

        editCustomerButton.addActionListener(e -> {
            c.editCustomer();
        });

        navigationPanel.add(editCustomerButton);





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
