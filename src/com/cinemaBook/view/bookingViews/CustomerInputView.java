package com.cinemaBook.view.bookingViews;

import com.cinemaBook.model.Screening;

import javax.swing.*;
import java.awt.*;

public class CustomerInputView extends JComponent{
    public CustomerInputView() {
        super();
        setLayout(new FlowLayout());
    }

    public void display(Screening screening) {

        JTextField textField = new JTextField();

        add(textField);
    }
}
