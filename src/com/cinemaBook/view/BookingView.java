package com.cinemaBook.view;

import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screenings;
import com.cinemaBook.view.bookingViews.CustomerInputView;
import com.cinemaBook.view.bookingViews.ScreeningSelectionView;
import com.cinemaBook.view.bookingViews.SeatSelectionView;

import javax.swing.*;
import java.awt.*;
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

    public void display(Screenings screenings, String currentView, Function<Integer, Void> onScreeningSelected, int screeningId, Function<Void, Void> onCancel, Function<Customer, Void> onCustomerSubmit) {

        cl.show(this, currentView);

        switch (currentView) {
            case ScreeningSelection:
                screeningSelectionView.display(screenings, id -> {
                    onScreeningSelected.apply(id);
                    return null;
                });
                break;
            case CustomerInput:
                customerInputView.display(screenings.find(s -> s.getId() == screeningId), e -> {
                    onCancel.apply(null);
                    return null;
                },customer -> {
                    onCustomerSubmit.apply(customer);
                    return null;
                });
                break;
            case SeatSelection:
                seatSelectionView.display(screenings.find(s -> s.getId() == screeningId), e -> {
                    onCancel.apply(null);
                    return null;
                });
                break;
            default:
                throw new RuntimeException("How did you even get here?");
        }
    }
}
