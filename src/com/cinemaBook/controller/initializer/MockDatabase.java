package com.cinemaBook.controller.initializer;

import com.cinemaBook.controller.DataHandler;
import java.sql.*;
import java.util.Date;

/**
 * The sole purpose of this class is to mock the database with dummy data if this haven't been done yet.
 */
public class MockDatabase {
    private DataHandler dataHandler;
    private Statement statement;

    /**
     * Get the database connection from the dataHandler
     */
    public MockDatabase() {
        this.dataHandler = new DataHandler();
        this.statement = dataHandler.getStatement();
    }

    /**
     * When this method is called, it'll go ahead and insert mockup data into the database.
     *
     * NB: This function will drop tables, use only when necessary!
     */
    public void createDatabaseMockup() {
        clearTables();
        insertAuditoriumInfo();
        insertFilmInfo();
        insertScreeningInfo();
    }

    /**
     * Function to clear previously set tables
     */
    private void clearTables() {
        try {
            // Drop auditoriums
            String query = "DROP TABLE Auditoriums";
            statement.executeUpdate(query);

            // Drop films
            query = "DROP TABLE Films";
            statement.executeUpdate(query);

            // Drop Screenings
            query = "DROP TABLE Screenings";
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Couldn't delete tables, they probably don't exist yet. Continuing");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert mock data into the auditorium table
     */
    private void insertAuditoriumInfo() {
        try {
            // Create the auditorium table.
            String query = "CREATE TABLE Auditoriums (" +
                "aID INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100) NOT NULL," +
                "rows INT NOT NULL," +
                "seatsPerRow INT NOT NULL" +
            ")";
            statement.executeUpdate(query);

            // Insert mock data
            query = "INSERT INTO Auditoriums (name, rows, seatsPerRow) VALUES ('Sal 1', 10, 20);";
            statement.executeUpdate(query);

            query = "INSERT INTO Auditoriums (name, rows, seatsPerRow) VALUES ('Sal 2', 23, 10)";
            statement.executeUpdate(query);

            query = "INSERT INTO Auditoriums (name, rows, seatsPerRow) VALUES ('Sal 2', 13, 30)";
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error during insertion of auditorium info");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert mock data into the film table
     */
    private void insertFilmInfo() {
        try {
            // Create the film table.
            String query = "CREATE TABLE Films (" +
                "fID INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100) NOT NULL," +
                "rating DOUBLE NOT NULL," +
                "minAGE INT NOT NULL" +
            ")";
            statement.executeUpdate(query);

            // Insert mock data
            query = "INSERT INTO Films (name, rating, minage) VALUES ('STAR WARS: THE LAST JEDI', 9.5, 12);";
            statement.executeUpdate(query);

            query = "INSERT INTO Films (name, rating, minage) VALUES ('SPOOR', 6.5, 18)";
            statement.executeUpdate(query);

            query = "INSERT INTO Films (name, rating, minage) VALUES ('KEDI', 7.4, 7)";
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error during insertion of film info");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert mock data into the screenings table
     */
    private void insertScreeningInfo() {
        try {
            // Create the film table.
            String query = "CREATE TABLE Screenings (" +
                "sID INT PRIMARY KEY AUTO_INCREMENT," +
                "filmID INT NOT NULL," +
                "auditoriumID DOUBLE NOT NULL," +
                "startTime LONG NOT NULL" +
            ")";
            statement.executeUpdate(query);

            // Insert mock data
            Date date = new Date(1512426600000L);
            query = "INSERT INTO Screenings (filmID, auditoriumID, startTime) VALUES (0, 0, " + date.getTime() + ");";
            statement.executeUpdate(query);

            date = new Date(1512728100000L);
            query = "INSERT INTO Screenings (filmID, auditoriumID, startTime) VALUES (1, 2, " + date.getTime() + ");";
            statement.executeUpdate(query);

            date = new Date(1514149200000L);
            query = "INSERT INTO Screenings (filmID, auditoriumID, startTime) VALUES (2, 1, " + date.getTime() + ");";
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error during insertion of screening info");
            System.out.println(e.getMessage());
        }
    }
}
