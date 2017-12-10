package com.cinemaBook.view;

import com.cinemaBook.controller.BookingController;
import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screenings;
import com.cinemaBook.model.Seat;
import com.cinemaBook.view.bookingViews.CustomerInputView;
import com.cinemaBook.view.bookingViews.ScreeningSelectionView;
import com.cinemaBook.view.bookingViews.SeatSelectionView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

public class BookingView extends JComponent {
    private final static String ScreeningSelection = "ScreeningSelectionView";
    private final static String CustomerInput = "CustomerInputView";
    private final static String SeatSelection = "SeatSelectionView";

    private CardLayout cl;

    private ScreeningSelectionView screeningSelectionView;
    private CustomerInputView customerInputView;
    private SeatSelectionView seatSelectionView;

    public BookingView() {
        super();
        cl = new CardLayout();
        setLayout(cl);

        screeningSelectionView = new ScreeningSelectionView();
        add(screeningSelectionView, ScreeningSelection);

        customerInputView = new CustomerInputView();
        add(customerInputView, CustomerInput);

        seatSelectionView = new SeatSelectionView();
        add(seatSelectionView, SeatSelection);
    }

    public void display(BookingController controller) {

        cl.show(this, controller.getCurrentView());

        switch (controller.getCurrentView()) {
            case ScreeningSelection:
                screeningSelectionView.display(controller);
                break;
            case CustomerInput:
                customerInputView.display(controller);
                break;
            case SeatSelection:
                seatSelectionView.display(controller);
                break;
            default:
                throw new RuntimeException("How did you even get here?");
        }
    }
}
