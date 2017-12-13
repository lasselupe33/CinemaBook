package com.cinemaBook.data;

import com.cinemaBook.model.Seat;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This singleton data handler has the responsibility to manage the reservation of seats
 */
public class SeatDataHandler extends DataHandler {
    // Store a reference to the dataHandler
    private static final SeatDataHandler instance = new SeatDataHandler();

    /**
     * Call the core data handler constructor
     */
    public SeatDataHandler() {
        super();
    }

    // Returns a reference to the screeningsDataHandler
    public static final SeatDataHandler getInstance() { return instance; }

    /**
     * This function generates the seatAssignment for a given screening.
     *
     * @param screeningId The id of the screening.
     * @param auditoriumRows The amount of rows in the auditorium where the film is being played.
     * @param auditoriumColumns The amount of columns in the auditorium.
     * @return The seatAssignments in a two dimensional array
     */
    public Seat[][] getSeatAssignment(int screeningId, int auditoriumRows, int auditoriumColumns) {
        Seat[][] seats = new Seat[auditoriumRows][auditoriumColumns];

        // Loop through the seats array and populate with empty seats
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                seats[i][j] = new Seat(i, j, false);
            }
        }

        // Get seats that have already been created in the database, and set their reserved state accordingly
        try {
            // Create query to get all seatAssignments for the given screening
            Statement seatAssignmentStatement = this.createStatement();
            String query = "SELECT * FROM SeatAssignment WHERE screening_id = " + screeningId + ";";

            ResultSet rs = seatAssignmentStatement.executeQuery(query);

            // Loop over seatAssignments and update seat states accordingly
            while (rs.next()) {
                Seat seat = seats[rs.getInt("row")][rs.getInt("col")];
                if (rs.getBoolean("isReserved")) {
                    seat.setReserved(true);
                } else {
                    seat.setReserved(false);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to get reserved seats... ");
            System.out.println(e.getMessage());
        }

        return seats;
    }

    /**
     * Internal helper that updates the reservation of seats for a given screening
     */
    public void insertSeatReservation(int screeningId, ArrayList<Seat> seats) {
        // Loop over all the seat reservations and insert the data into the database
        for (Seat seat : seats) {
            HashMap<String, String> seatInformation = new HashMap<>();
            // Create info map
            seatInformation.put("screening_id", "" + screeningId);
            seatInformation.put("row", "" + seat.getRow());
            seatInformation.put("col", "" + seat.getColumn());
            seatInformation.put("isReserved", "1");

            // Insert data!
            this.insertData("SeatAssignment", seatInformation);
        }
    }

    /**
     * This method deletes seatReservations.
     *
     * @param screeningId The id of the screening in which the seats are
     * @param seats an arrayList of seats that contains an array with the row and a column value for a seat.
     */
    public void deleteSeatReservation(int screeningId, ArrayList<Seat> seats) {
        for (Seat seat : seats) {
            try {
                String query = "DELETE FROM SeatAssignment WHERE " +
                        "screening_id = '" + screeningId + "' AND " +
                        "row = '" + seat.getRow() + "' AND " +
                        "col = '" + seat.getColumn() + "'" +
                        ";";
                this.createStatement().executeUpdate(query);
            } catch (Exception e) {
                throw new Error("Failed to delete seat with row=" + seat.getRow() + " and col=" + seat.getColumn() + ". " + e.getMessage());
            }

        }
    }
}
