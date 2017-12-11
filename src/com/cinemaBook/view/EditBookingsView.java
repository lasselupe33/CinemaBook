package com.cinemaBook.view;


import com.cinemaBook.model.Bookings;
import com.cinemaBook.model.Screenings;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.Function;


public class EditBookingsView extends JComponent {
    private JPanel panel;

    JButton button;

    public EditBookingsView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Navn");
        tableModel.addColumn("Email");
        tableModel.addColumn("Tlf");
        tableModel.addColumn("Film");
        tableModel.addColumn("Tidspunkt");
        tableModel.addColumn("Antal Sæder");
        tableModel.addColumn("Edit");
        tableModel.addColumn("Delete");

        /*
        * For testing
         */
        button = new JButton();
        button.setText("Hello World");

    }

}




