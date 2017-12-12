package com.cinemaBook.data;

import com.cinemaBook.model.Booking;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class BookingsDataHandlerTest {
    @Before
    public void setUp() {
    }

    /**
     * Should return an instance of the BookingsDataHandler
     */
    @Test
    public void getInstance() throws Exception {
        BookingsDataHandler instance1 = BookingsDataHandler.getInstance();

        // Check that the instance is of the proper class
        assertThat(instance1, instanceOf(BookingsDataHandler.class));

        // Get another instance
        BookingsDataHandler instance2 = BookingsDataHandler.getInstance();

        // Ensure that instances will always be the same
        assertEquals(instance1, instance2);
    }

    @Test
    public void getBookings() throws Exception {

    }

    @Test
    public void getBookingsByCustomer() throws Exception {
    }

    @Test
    public void submitBooking() throws Exception {
    }

    @Test
    public void updateBooking() throws Exception {
    }

    @Test
    public void deleteBooking() throws Exception {
    }
}