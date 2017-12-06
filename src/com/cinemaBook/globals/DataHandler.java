package com.cinemaBook.globals;

import com.cinemaBook.model.Auditorium;
import com.cinemaBook.model.Film;
import com.cinemaBook.model.Screening;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import static java.util.stream.Collectors.joining;

/**
 * This singleton has the responsibility for handling all communication between the database and the main application.
 */
public class DataHandler {
    // Database credentials
    private static final String db = "sql11207684";
    private static final String user = "sql11207684";
    private static final String password = "tDT6BhIv9L";

    // Database access url
    private static final String db_url = "jdbc:mysql://sql11.freemysqlhosting.net/" + db;
    private Connection connection;
    private Statement statement;

    // Store a reference to the dataHandler
    private static final DataHandler instance = new DataHandler();

    // Fields used for caching
    private ArrayList<Screening> screeningsCache;

    /**
     * The constructor sets up the connection with the database and creates the default statement.
     */
    private DataHandler() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            this.connection = DriverManager.getConnection(db_url, user, password);
            this.statement = this.connection.createStatement();
        } catch (Exception e) {
            System.out.println("An error occurred during the attempt to connect to the database");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns a refernece to the DataHandler
     */
    public static DataHandler getInstance() { return instance; }

    /**
     * This function is used to clear all tables within the database.
     *
     * NB: This method clears all tables in the database. It should be used with caution!
     */
    public void clearData() {
        try {
            // Delete all data unconditionally
            statement.executeQuery("SET FOREIGN_KEY_CHECKS = 0");

            // Execute query to get all tables in database
            String query = "SHOW TABLES";
            ResultSet rs = statement.executeQuery(query);

            // Create a secondary statement to ensure resultSet won't be overwritten
            Statement clearStatement = connection.createStatement();

            // Loop through all tables and clear them
            while (rs.next()) {
                String table_name = rs.getString(1);
                clearStatement.executeUpdate("DROP TABLE " + table_name + ";");
            }
        } catch (Exception e) {
            System.out.println("Couldn't clear tables..");
            System.out.println(e.getMessage());
        } finally {
            try {
                // Enable foreign keys again
                statement.executeQuery("SET FOREIGN_KEY_CHECKS = 1");
            } catch (Exception e) {
                throw new Error("Foreign key checks couldn't be enabled again!");
            }
        }
    }

    /**
     * The following method creates a table.
     * This should only be necessary to use in the MockDatabase class during database creation.
     */
    public void createTable(String query) {
        try {
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Failed to create table..");
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is used to insert into the database.
     *
     * @param table The name of the desired table to insert data into.
     * @param columns A map of the columns, which contains a key with the name of the column and a value which is the
     *                data to be inserted into the given column.
     */
    public void insertData(String table, HashMap<String, String> columns) {
        // Loop through the different keys in the hashmap and join them with ", " to prepare them for the MySQL query
        String columnNames = columns.keySet()
                .stream() // Create stream to handle map keys
                .collect(joining(", ")); // Join columns with a comma

        // Do the same with the data values
        String data = columns.values()
                .stream() // Create stream to handle map keys
                .map(value -> "'" + value + "'") // Wrap data in ''
                .collect(joining(", ")); // Join columns with a comma

        try {
            // Create the query and run it through the database!
            String query = "INSERT INTO " + table + " (" + columnNames + ") VALUES (" + data + ")";
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Failed to insert data..");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that returns the entire list of screenings found in the cinema.
     */
    public ArrayList<Screening> getScreenings() {
        // If screenings have already been fetched, return the cached screenings.
        if (screeningsCache != null) {
            return screeningsCache;
        }

        try {
            ArrayList<Screening> screenings = new ArrayList<>();

            // Get all screenings and their associated fields
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
                "s.auditorium_id = a.auditorium_id AND " +
                "s.film_id = f.film_id";

            // Execute the query
            ResultSet rs = statement.executeQuery(query);

            // Loop over all active screenings
            while (rs.next()) {
                // Get related filmModel
                Film film = createFilmModel(rs);

                // Get related auditorium
                Auditorium auditorium = createAuditoriumModel(rs);

                // Get screening specific information
                Date startTime = rs.getDate("screening_startTime");
                int screeningId = rs.getInt("screening_id");

                // Create screeningModel and add it to the array of all screenings
                screenings.add(new Screening(screeningId, startTime, film, auditorium));
            }

            // Cache screenings for future reference
            this.screeningsCache = screenings;

            // Return the list of screenings
            return screenings;
        } catch (Exception e) {
            System.out.println("Failed to retrieve screenings...");
            System.out.println(e.getMessage());
        }

        // If we get here, then no screenings have been found. Therefore null is returned
        return null;
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
