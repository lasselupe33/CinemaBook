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

    public void updateData() {

    }

    public void deleteData() {

    }

    /**
     * Method that returns the entire list of screenigns found in the cinema.
     */
    public ArrayList<Screening> getScreenings() {
        // If screenings have already been fetched, return the cached screenings.
        if (screeningsCache != null) {
            return screeningsCache;
        }

        try {
            ArrayList<Screening> screenings = new ArrayList<>();

            // Get all screenings
            String query = "SELECT * FROM Screenings";
            ResultSet rs = statement.executeQuery(query);

            Statement auditoriumStatement = connection.createStatement();

            // Loop over all active screenings
            while (rs.next()) {
                // Get related film
                int filmId = rs.getInt("filmID");
                Film film = getFilm(filmId);

                // Get related auditorium
                int auditoriumId = rs.getInt("auditoriumID");
                Auditorium auditorium = getAuditorium(auditoriumId);

                // Get screening specific information
                Date startTime = new Date(rs.getLong("startTime"));
                int screeningId = rs.getInt("sID");

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
     * Method that returns a filmModel, given a specific film ID.
     * Will only be called by internal DataHandler methods.
     *
     * @param fID a film ID
     * @return Returns a filmModel.
     */
    private Film getFilm(int fID) {
        try {
            // Create secondary statements to ensure other resultSets won't be overwritten
            Statement filmStatement = connection.createStatement();

            // Retrieve information about the film
            ResultSet film = filmStatement.executeQuery("SELECT * FROM Films WHERE fID = " + fID + ";");

            // Move cursor to film
            film.next();

            // Store film info
            String filmName = film.getString("name");
            Double filmRating = film.getDouble("rating");
            int filmMinAge = film.getInt("minAge");

            // Create a new model to store this data and return it
            return new Film(filmName, filmRating, filmMinAge);
        } catch (Exception e) {
            throw new Error("Failed to retrieve film... " + e.getMessage());
        }
    }

    /**
     * Method that returns an auditoriumModel, given a specific auditorium ID.
     * Will only be called by internal DataHandler methods.
     *
     * @param aID an auditorium ID
     * @return Returns an auditoriumModel.
     */
    private Auditorium getAuditorium(int aID) {
        try {
            // Create secondary statements to ensure other resultSets won't be overwritten
            Statement auditoriumStatement = connection.createStatement();

            // Retrieve information about the auditorium
            ResultSet auditorium = auditoriumStatement.executeQuery("SELECT * FROM Auditoriums WHERE aID = " + aID + ";");

            // Move cursor to film
            auditorium.next();

            // Store film info
            String auditoriumName = auditorium.getString("name");
            int auditoriumRows = auditorium.getInt("rows");
            int auditoriumSeatsPerRow = auditorium.getInt("seatsPerRow");

            // Create a new model to store this data and return it
            return new Auditorium(auditoriumName, auditoriumRows, auditoriumSeatsPerRow);
        } catch (Exception e) {
            throw new Error("Failed to retrieve auditorium... " + e.getMessage());
        }
    }
}
