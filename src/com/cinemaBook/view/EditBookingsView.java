package com.cinemaBook.view;


import com.cinemaBook.controller.EditBookingController;
import com.cinemaBook.view.bookingViews.BookingSelectionView;
import com.cinemaBook.view.bookingViews.CustomerInputView;
import com.cinemaBook.view.bookingViews.ScreeningSelectionView;
import com.cinemaBook.view.bookingViews.SeatSelectionView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class EditBookingsView extends JComponent {
    private final static String BookingSelection = "BookingSelectionView";
    private final static String ScreeningSelection = "ScreeningSelectionView";
    private final static String CustomerInput = "CustomerInputView";
    private final static String SeatSelection = "SeatSelectionView";

    private BookingSelectionView bookingSelectionView;
    private ScreeningSelectionView screeningSelectionView;
    private CustomerInputView customerInputView;
    private SeatSelectionView seatSelectionView;

    private CardLayout cl;

    public EditBookingsView() {
        super();
        cl = new CardLayout();
        setLayout(cl);

        bookingSelectionView = new BookingSelectionView();
        add(bookingSelectionView, BookingSelection);

        screeningSelectionView = new ScreeningSelectionView();
        add(screeningSelectionView, ScreeningSelection);

        customerInputView = new CustomerInputView();
        add(customerInputView, CustomerInput);

        seatSelectionView = new SeatSelectionView();
        add(seatSelectionView, SeatSelection);
    }



    public void display(EditBookingController controller) {

        cl.show(this, controller.getCurrentView());

        switch (controller.getCurrentView()) {
            case BookingSelection:
                bookingSelectionView.display(controller);
                break;
            case ScreeningSelection:
                screeningSelectionView.display(controller.getScreenings(), screening -> {
                    controller.selectFilmSeats(screening);
                    return null;
                });
                break;
            case SeatSelection:
                if (controller.selectingFilm) {
                    seatSelectionView.display(controller.getSelectedScreening(), new ArrayList<>(), v -> {
                        controller.reset();
                        return null;
                    }, seats -> {
                        controller.submitUpdatedBooking(seats);
                        return null;
                    });
                } else {
                    seatSelectionView.display(controller.getSelectedBooking().getScreening(), controller.getSelectedBooking().getReservedSeats(), v -> {
                        controller.reset();
                        return null;
                    }, seats -> {
                        controller.submitSeats(seats);
                        return null;
                    });
                }
                break;
            case CustomerInput:
                customerInputView.display(controller.getSelectedBooking().getCustomer(), v -> {
                    controller.reset();
                    return null;
                }, customer -> {
                    controller.submitCustomer(customer);
                    return null;
                });
                break;
            default:
                throw new RuntimeException("How did you even get here?");
        }
    }


}




