package com.cinemaBook.model;

import com.cinemaBook.data.BookingsDataHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class BookingTest {
    Screening testScreening;
    Film testFilm;
    Auditorium testAuditorium;
    Date startTime;
    int rows = 5;
    int columns = 10;
    Seat[][] testSeats;
    SeatAssignment testAssignemnt;
    Booking testBooking;
    Customer testCustomer;
    ArrayList<Seat> testReservedSeats = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        // SeatAssignment setup
        testSeats = new Seat[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                testSeats[i][j] = new Seat(i, j, false);
            }
        }

        // Reserved seats
        testReservedSeats.add(new Seat(3, 4, true));
        testReservedSeats.add(new Seat(5, 7, true));

        testAssignemnt = new SeatAssignment(testSeats);
        startTime = new Date(12000);
        testFilm = new Film(1, "Film", "Hejsa", 5.7, 12);
        testAuditorium = new Auditorium(1, "Sal 1", 5, 10);
        testScreening = new Screening(1, startTime, testFilm, testAuditorium, testAssignemnt);
        testCustomer = new Customer("Test Customer", "+4512345678", "someemail@gmail.com");

        testBooking = new Booking(testCustomer, testScreening, testReservedSeats);
    }

    @Test
    public void setId() throws Exception {
        testBooking.setId(5);
        assertEquals(testBooking.getId(), 5);

        testBooking.setId(9000);
        assertEquals(testBooking.getId(), 9000);
    }

    @Test
    public void getCustomer() throws Exception {
        assertEquals(testBooking.getCustomer(), testCustomer);
    }

    @Test
    public void getScreening() throws Exception {
        assertEquals(testBooking.getScreening(), testScreening);
    }

    @Test
    public void getReservedSeats() throws Exception {
        assertEquals(testBooking.getReservedSeats(), testReservedSeats);
    }

    @Test
    public void testDBCommunication() throws Exception {
        // Test Add to db
        testBooking.addToDB();
        ArrayList<Booking> bookingsInDB = BookingsDataHandler.getInstance().getBookings();
        boolean matched = false;

        for (Booking booking : bookingsInDB) {
            if (booking.getCustomer().getName().equals("Test Customer")) {
                matched = true;
            }
        }

        assertEquals(matched, true);

        // Test delete from db
        for (Booking booking : bookingsInDB) {
            if (booking.getCustomer().getName().equals("Test Customer")) {
                booking.delete();
            }
        }

        // Fetch bookings again to check that it is deleted
        bookingsInDB = BookingsDataHandler.getInstance().getBookings();
        matched = false;

        for (Booking booking : bookingsInDB) {
            if (booking.getCustomer().getName().equals("Test Customer")) {
                matched = true;
            }
        }

        assertEquals(matched, false);
    }

}