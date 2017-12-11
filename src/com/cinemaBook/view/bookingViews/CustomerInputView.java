package com.cinemaBook.view.bookingViews;

import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screening;

import javax.swing.*;
import java.util.function.Function;

public class CustomerInputView extends JComponent{
    public CustomerInputView() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void display(Screening screening, Function<Customer, Void> onSubmit) {
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


        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            Customer customer = new Customer(nameField.getText(), phoneField.getText(), mailField.getText());
            onSubmit.apply(customer);
        });

        add(submitButton);
    }
}
