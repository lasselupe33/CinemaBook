package com.cinemaBook.view;


import com.cinemaBook.controller.EditBookingController;
<<<<<<< HEAD
import com.cinemaBook.controller.EditBookingController;
import com.cinemaBook.globals.DateFormatter;
=======
import com.cinemaBook.utils.DateFormatter;
>>>>>>> 017f898f69d5c68c9a324649c9d0f537c8b5c4f2
import com.cinemaBook.model.Booking;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


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




