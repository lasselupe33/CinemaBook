package com.cinemaBook.view.bookingViews;

import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screening;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class CustomerInputView extends JComponent{
    public CustomerInputView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void display(Screening screening, Function<Void, Void> onCancel, Function<Customer, Void> onSubmit) {
        removeAll();

        JLabel filmLabel = new JLabel(screening.getFilm().getName());

        add(filmLabel);


        JLabel nameLabel = new JLabel("Name:");

        add(nameLabel);

        JTextField nameField = new JTextField();

        add(nameField);


        JLabel phoneLabel = new JLabel("Phone:");

        add(phoneLabel);

        JTextField phoneField = new JTextField();

        add(phoneField);


        JLabel mailLabel = new JLabel("Email:");

        add(mailLabel);

        JTextField mailField = new JTextField();

        add(mailField);

        JPanel navigationPanel = new JPanel(new FlowLayout());

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
