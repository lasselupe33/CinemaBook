package com.cinemaBook.utils;

import com.cinemaBook.model.Seat;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Helper class that can convert an ArrayList to a string to be stored in the database and vice-versa
 */
public class ConvertReservedSeats {
    /**
     * Internal helper that formats the reserved seats of a booking to a string
     * @param reservedSeats the ArrayList of reserved seats of the booking
     * @return A string in the form of "row1,col1:row2,col2:row3:col3....
     */
    public static final String convertReservedSeatsToString(ArrayList<Seat> reservedSeats) {
        // Convert arrayList of reserved seats into string of the following form "x1,y1:x2,y2:x3,y3...."
        String seatsString = "";
        for (int i = 0; i < reservedSeats.size(); i++) {
            // Add a single seat with the row first followed by a comma and then the column
            seatsString += reservedSeats.get(i).getRow() + "," + reservedSeats.get(i).getColumn();

            // If this isn't the last reserved seat add a ":" to be used as a delimiter
            if (i != reservedSeats.size() - 1) {
                seatsString += ":";
            }
        }

        return seatsString;
    }

    /**
     * Internal helper that formats a string of reserved seats of a booking back into an ArrayList<Seat>
     * that contains arrays of seats with a row and a column value.
     *
     * @param reservedSeatsString the string of reserved seats of the booking
     * @return an arrayList of reserved seats rows and columns.
     */
    public static final ArrayList<Seat> convertReservedSeatsStringToArray(String reservedSeatsString) {
        ArrayList<Seat> reservedSeats = new ArrayList<>();

        // Split the string of reserved seats into an array containing the individual seats
        String[] seats = reservedSeatsString.split(":");

        for (String seat : seats) {
            // Convert seat string to an array where the first index is the row and the second the column
            int[] seatInfo = Arrays.stream(seat.split(",")).mapToInt(Integer::parseInt).toArray();
            reservedSeats.add(new Seat(seatInfo[0], seatInfo[1], true));
        }

        return reservedSeats;
    }
}
