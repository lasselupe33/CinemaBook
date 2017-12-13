package com.cinemaBook.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeatAssignmentTest {
    int rows = 5;
    int columns = 10;
    Seat[][] testSeats;
    SeatAssignment testAssignemnt;

    /**
     * Set up a SeatAssignemnt with two seats being reserved.. These seats are at row 4 col 4,
     */
    @Before
    public void setUp() {
        testSeats = new Seat[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                testSeats[i][j] = new Seat(i, j, false);
            }
        }

        // Set two seats to be reserved
        testSeats[4][4].setReserved(true);

        testAssignemnt = new SeatAssignment(testSeats);
    }

    @Test
    public void testGetAmountOfAvailableSeats() throws Exception {
        // Since we have 5 rows of 10 columns, we expect that this returns 5 * 10 - 1 = 49 (minus 1 since 1 seat is reserved)
        assertEquals(testAssignemnt.getAmountOfAvailableSeats(), 49);
    }

    @Test
    public void testIsSeatReserved() throws Exception {
        // Only test seat at row 4 col 4 should be reserved
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == 4 && j == 4) {
                    assertEquals(testAssignemnt.isSeatReserved(i, j), true);
                } else {
                    assertEquals(testAssignemnt.isSeatReserved(i, j), false);
                }
            }
        }
    }

    @Test
    public void testGetSeats() throws Exception {
        // Should return the seats originally created
        assertEquals(testSeats, testAssignemnt.getSeats());
    }

}