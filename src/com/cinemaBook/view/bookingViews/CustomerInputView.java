package com.cinemaBook.view.bookingViews;

import com.cinemaBook.controller.BookingController;
import com.cinemaBook.globals.DateFormatter;
import com.cinemaBook.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.function.Function;

public class CustomerInputView extends JComponent{
    public CustomerInputView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void display(Function<Void, Void> cancel, Function<Customer, Void> success) {
        removeAll();

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
            cancel.apply(null);
        });

        navigationPanel.add(cancelButton);

        JButton nextButton = new JButton("Submit");

        nextButton.addActionListener(e -> {
            Customer customer = new Customer(nameField.getText(), phoneField.getText(), mailField.getText());
            success.apply(customer);
        });

        navigationPanel.add(nextButton);

        add(navigationPanel);
    }
}
