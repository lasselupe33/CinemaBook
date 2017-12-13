package com.cinemaBook.view;


import com.cinemaBook.controller.EditBookingController;
import com.cinemaBook.controller.EditBookingController;
import com.cinemaBook.globals.DateFormatter;
import com.cinemaBook.model.Booking;
import com.cinemaBook.model.Bookings;
import com.cinemaBook.model.Screenings;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.function.Function;


public class EditBookingsView extends JComponent {
    private JPanel panel;

    private CardLayout cl;

    JButton button;

    public EditBookingsView() {
        super();
        cl = new CardLayout();
        setLayout(cl);

    }



    public void display(EditBookingController controller) {

        cl.show(this, controller.getCurrentView());

        switch (controller.getCurrentView()) {
            case ScreeningSelection:
                screeningSelectionView.display(controller.getScreenings(), screening -> {
                    controller.onScreeningSelected(screening.getId());
                    return null;
                });
                break;
            case SeatSelection:
                seatSelectionView.display(controller.getSelectedScreening(), v -> {
                    controller.reset();
                    return null;
                }, seats -> {
                    controller.onSeatSubmit(seats);
                    return null;
                });
                break;
            case CustomerInput:
                customerInputView.display(v -> {
                    controller.reset();
                    return null;
                }, customer -> {
                    controller.onCustomerSubmit(customer);
                    return null;
                });
                break;
            default:
                throw new RuntimeException("How did you even get here?");
        }
    }


}




