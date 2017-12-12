package com.cinemaBook.data;

import com.cinemaBook.model.Auditorium;
import com.cinemaBook.model.Film;
import com.cinemaBook.model.Screening;
import com.cinemaBook.model.SeatAssignment;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * This singleton data handler has to manage all communication to the database in regards to screenings
 */
public class ScreeningsDataHandler extends DataHandler {
    // Store a reference to the dataHandler
    private static final ScreeningsDataHandler instance = new ScreeningsDataHandler();

    /**
     * Create a connection via the database
     */
    public ScreeningsDataHandler() {
        super();
    }

    // Returns a reference to the screeningsDataHandler
    public static final ScreeningsDataHandler getInstance() { return instance; }

    /**
     * Method that returns the entire list of screenings found in the cinema.
     *
     * @param screening_id The id of the desired screening. Set to -1 to fetch all screenings.
     */
    public ArrayList<Screening> getScreenings(int screening_id) {
        try {
            ArrayList<Screening> screenings = new ArrayList<>();

            // Get all selected screenings and their associated fields
            String query = "SELECT " +
                    "s.screening_id," +
                    "s.screening_startTime," +
                    "a.auditorium_id," +
                    "a.auditorium_name," +
                    "a.auditorium_rows," +
                    "a.auditorium_columns," +
                    "f.film_id," +
                    "f.film_name," +
                    "f.film_description," +
                    "f.film_rating," +
                    "f.film_minAge " +
                    "FROM " +
                    "Screenings s," +
                    "Auditoriums a," +
                    "Films f " +
                    "WHERE " +
                    (screening_id != -1 ? "s.screening_id = '" + screening_id + "' AND " : "") +
                    "s.auditorium_id = a.auditorium_id AND " +
                    "s.film_id = f.film_id";

            // Execute the query
            ResultSet rs = this.createStatement().executeQuery(query);

            // Loop over all active screenings
            while (rs.next()) {
                // Get related filmModel
                Film film = createFilmModel(rs);

                // Get related auditorium
                Auditorium auditorium = createAuditoriumModel(rs);

                // Get screening specific information
                Date startTime = new Date(rs.getTimestamp("screening_startTime").getTime());
                int screeningId = rs.getInt("screening_id");

                // Get seat assignment for the given screening
                SeatAssignment seatAssignment = new SeatAssignment(SeatDataHandler.getInstance().getSeatAssignment(screeningId, auditorium.getRows(), auditorium.getColumns()));

                // Create screeningModel and add it to the array of all screenings
                screenings.add(new Screening(screeningId, startTime, film, auditorium, seatAssignment));
            }

            // Return the list of screenings
            return screenings;
        } catch (Exception e) {
            throw new Error("Failed to retrieve screenings... " + e.getMessage());
        }
    }

    /**
     * Internal helper that creates an auditorium model, based on a ResultSet already fetched
     *
     * @return Returns an auditoriumModel
     */
    private Auditorium createAuditoriumModel(ResultSet rs) {
        try {
            // Get information related to the auditorium model
            int auditorium_id = rs.getInt("auditorium_id");
            String auditorium_name = rs.getString("auditorium_name");
            int auditorium_rows = rs.getInt("auditorium_rows");
            int auditorium_columns = rs.getInt("auditorium_columns");

            // Create and return the auditorium model
            return new Auditorium(auditorium_id, auditorium_name, auditorium_rows, auditorium_columns);
        } catch (Exception e) {
            throw new Error("Error occurred while creating an auditoriumModel... " + e.getMessage());
        }
    }

    /**
     * Internal helper that creates a film model, based on a ResultSet already fetched
     *
     * @return Returns a filmModel
     */
    private Film createFilmModel(ResultSet rs) {
        try {
            // Get information related to the auditorium model
            int film_id = rs.getInt("film_id");
            String film_name = rs.getString("film_name");
            String film_description = rs.getString("film_description");
            double film_rating = rs.getDouble("film_rating");
            int film_minAge = rs.getInt("film_minAge");

            // Create and return the auditorium model
            return new Film(film_id, film_name, film_description, film_rating, film_minAge);
        } catch (Exception e) {
            throw new Error("Error occurred while creating a filmModel... " + e.getMessage());
        }
    }
}
