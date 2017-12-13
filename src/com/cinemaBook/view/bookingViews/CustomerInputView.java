package com.cinemaBook.view.bookingViews;

import com.cinemaBook.model.Customer;

import javax.swing.*;
import java.util.function.Function;

public class CustomerInputView extends JComponent{
    public CustomerInputView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void display(Customer customer, Function<Void, Void> cancel, Function<Customer, Void> success) {
        removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel nameLabel = new JLabel("Name:");

        panel.add(nameLabel);

        JTextField nameField = new JTextField(customer.getName());

        panel.add(nameField);


        JLabel phoneLabel = new JLabel("Phone:");

        panel.add(phoneLabel);

        JTextField phoneField = new JTextField(customer.getPhone());

        panel.add(phoneField);


        JLabel mailLabel = new JLabel("Email:");

        panel.add(mailLabel);

        JTextField mailField = new JTextField(customer.getEmail());

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
            Customer updatedCustomer = new Customer(nameField.getText(), phoneField.getText(), mailField.getText());
            success.apply(updatedCustomer);
        });

        navigationPanel.add(nextButton);

        add(navigationPanel);
    }
}
