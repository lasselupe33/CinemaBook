package com.cinemaBook.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeatTest {
    private Seat testSeat;

    @Before
    public void setupTestSeat() {
        this.testSeat = new Seat(2, 3, true);
    }

    @Test
    public void testGetRow() throws Exception {
        assertEquals(this.testSeat.getRow(), 2);
    }

    @Test
    public void testGetColumn() throws Exception {
        assertEquals(this.testSeat.getColumn(), 3);
    }

    @Test
    public void testIsReserved() throws Exception {
        assertEquals(this.testSeat.isReserved(), true);
    }

    @Test
    public void testSetReserved() throws Exception {
        this.testSeat.setReserved(false);

        assertEquals(this.testSeat.isReserved(), false);

        this.testSeat.setReserved(true);

        assertEquals(this.testSeat.isReserved(), true);
    }
}