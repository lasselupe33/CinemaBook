package com.cinemaBook.view.bookingViews;

import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screening;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.Function;

public class CustomerInputView extends JComponent{
    public CustomerInputView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void display(Screening screening, Function<Void, Void> onCancel, Function<Customer, Void> onSubmit) {
        removeAll();

        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Title");
        tableModel.addColumn("Time");
        tableModel.addColumn("Auditorium");
        tableModel.addColumn("Minimum age");
        tableModel.addColumn("Seats left");

        tableModel.addRow(new Object[]{screening.getFilm().getName(), screening.getStartTime(), screening.getAuditorium().getName(), Integer.toString(screening.getFilm().getMinAge()), screening.getSeatAssignment().getAmountOfAvailableSeats()});

        JTable table = new JTable(tableModel);

        add(table);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel nameLabel = new JLabel("Name:");

        panel.add(nameLabel);

        JTextField nameField = new JTextField();

        panel.add(nameField);


        JLabel phoneLabel = new JLabel("Phone:");

        panel.add(phoneLabel);

        JTextField phoneField = new JTextField();

        panel.add(phoneField);


        JLabel mailLabel = new JLabel("Email:");

        panel.add(mailLabel);

        JTextField mailField = new JTextField();

        panel.add(mailField);

        add(panel);

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.LINE_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        navigationPanel.add(Box.createHorizontalGlue());

        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> {
            onCancel.apply(null);
        });

        navigationPanel.add(cancelButton);

        JButton nextButton = new JButton("Submit");

        nextButton.addActionListener(e -> {
            Customer customer = new Customer(nameField.getText(), phoneField.getText(), mailField.getText());
            onSubmit.apply(customer);
        });

        navigationPanel.add(nextButton);

        add(navigationPanel);
    }
}
