package com.cinemaBook.globals;

import com.cinemaBook.model.Seat;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class convertReservedSeatsTest {
    @Test
    public void testConvertToString() {
        // Create test array
        ArrayList<Seat> seats = new ArrayList<>();

        // Populate with test seats
        seats.add(new Seat(3, 4, true));
        seats.add(new Seat(5, 7, true));

        // Generate test string
        String convertedString = ConvertReservedSeats.convertReservedSeatsToString(seats);

        assertEquals("3,4:5,7", convertedString);
    }

    @Test
    public void testConvertToArray() {
        // Create test string
        String testString = "7,4:5,2";

        // Create test array
        ArrayList<Seat> testArray = ConvertReservedSeats.convertReservedSeatsStringToArray(testString);

        // First seat should be of row 7 and column 4, while the second seat should be of row 5 and column 2
        Assert.assertEquals(testArray.get(0).getRow(), 7);
        Assert.assertEquals(testArray.get(0).getColumn(), 4);
        Assert.assertEquals(testArray.get(1).getRow(), 5);
        Assert.assertEquals(testArray.get(1).getColumn(), 2);
    }
}