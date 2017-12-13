package com.cinemaBook.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cinemaBook.data.BookingsDataHandler;
import com.cinemaBook.model.*;
import com.cinemaBook.utils.MockDatabase;
import com.cinemaBook.view.BookingView;
import org.junit.Test;

import java.util.ArrayList;

public class BookingControllerTest {
    public BookingControllerTest() {
        MockDatabase mockUp = new MockDatabase();
        mockUp.createDatabaseMockUp();
    }

    private BookingController createBookingController() {
        BookingView view = new BookingView();
        Screenings screenings = new Screenings();
        return new BookingController(view, screenings);
    }

    private ArrayList<Seat> generateSeats(Auditorium auditorium, SeatAssignment assignment) {
        int cols = auditorium.getColumns();
        int rows = auditorium.getRows();

        ArrayList<Seat> seats = new ArrayList<>();

        for (int i = 0; i < (int) (Math.random() * 10); i++) {
            int col = (int) (Math.random() * (double)cols);
            int row = (int) (Math.random() * (double)rows);

            Seat seat = new Seat(row, col, true);

            if (!assignment.isSeatReserved(row, col) && !seatInList(seats, seat)) {
                seats.add(seat);
            }
        }

        return seats;
    }

    private boolean seatInList(ArrayList<Seat> seats, Seat seat) {
        for (Seat otherSeat: seats) {
            if (otherSeat.getRow() == seat.getRow() && otherSeat.getColumn() == otherSeat.getColumn()) {
                return true;
            }
        }
        return false;
    }

    private boolean seatsEquivalent(ArrayList<Seat> seats1, ArrayList<Seat> seats2) {
        for(Seat seat: seats1){
            if (!seatInList(seats2, seat)) {
                return false;
            }
        }
        return true;
    }

    private boolean screeningEquivalent(Screening screening1, Screening screening2) {
        return screening1.getId() == screening2.getId();
    }

    @Test
    public void onScreeningSelected() {
        BookingController bookingController = createBookingController();

        Screening screening = bookingController.getScreenings().getScreenings().get(0);

        bookingController.onScreeningSelected(screening);

        assertEquals(screening, bookingController.getSelectedScreening());
        assertEquals("SeatSelectionView", bookingController.getCurrentView());
    }

    @Test
    public void onSeatSubmit() {
        BookingController bookingController = createBookingController();

        bookingController.onScreeningSelected(bookingController.getScreenings().getScreenings().get(0));

        ArrayList<Seat> seats = generateSeats(
            bookingController.getSelectedScreening().getAuditorium(),
            bookingController.getSelectedScreening().getSeatAssignment()
        );

        bookingController.onSeatsSelected(seats);

        assertEquals(seats, bookingController.getSeats());
        assertEquals("CustomerInputView", bookingController.getCurrentView());
    }

    @Test
    public void reset() {
        BookingController bookingController = createBookingController();

        Screening screening = bookingController.getScreenings().getScreenings().get(0);

        bookingController.onScreeningSelected(screening);

        ArrayList<Seat> seats = generateSeats(
                bookingController.getSelectedScreening().getAuditorium(),
                bookingController.getSelectedScreening().getSeatAssignment()
        );

        bookingController.onSeatsSelected(seats);

        bookingController.reset();

        assertNull(bookingController.getSelectedScreening());
        assertEquals(0, bookingController.getSeats().size());
        assertEquals("ScreeningSelectionView", bookingController.getCurrentView());
    }

    @Test
    public void onCustomerSubmit() {
        BookingController bookingController = createBookingController();

        Screening screening = bookingController.getScreenings().getScreenings().get(0);

        bookingController.onScreeningSelected(screening);

        ArrayList<Seat> seats = generateSeats(
                bookingController.getSelectedScreening().getAuditorium(),
                bookingController.getSelectedScreening().getSeatAssignment()
        );

        bookingController.onSeatsSelected(seats);

        Customer customer = new Customer("uniq_name", "12355618", "that_email_seems_quite_unique@defnotgmail.com");

        bookingController.onCustomerSubmit(customer);

        // Determine if in DB
        Booking booking = BookingsDataHandler.getInstance().getBookingsByCustomer(customer).get(0);

        assertNotNull(booking);

        assertTrue(seatsEquivalent(seats, booking.getReservedSeats()));
        assertTrue(screeningEquivalent(screening, booking.getScreening()));

        assertNull(bookingController.getSelectedScreening());
        assertEquals(0, bookingController.getSeats().size());
        assertEquals("ScreeningSelectionView", bookingController.getCurrentView());
    }
}
