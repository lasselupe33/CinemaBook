package com.cinemaBook.bootstrap;

import com.cinemaBook.globals.DataHandler;
import java.util.Date;
import java.util.HashMap;

/**
 * The sole purpose of this class is to mock the database with dummy data if this haven't been done yet.
 */
public class MockDatabase {
    private DataHandler dataHandler = new DataHandler();

    /**
     * When this method is called, it'll go ahead and insert mockup data into the database.
     *
     * NB: This function will drop tables, use only when necessary!
     */
    public void createDatabaseMockUp() {
        dataHandler.clearData();
        createAuditoriumInfo();
        createFilmInfo();
        createScreeningInfo();
    }

    /**
     * Create mock data for the auditoriums
     */
    private void createAuditoriumInfo() {
        // Create the auditorium table.
        String query = "CREATE TABLE Auditoriums (" +
            "aID INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(100) NOT NULL," +
            "rows INT NOT NULL," +
            "seatsPerRow INT NOT NULL" +
        ")";
        dataHandler.createTable(query);

        // Create the first auditorium
        HashMap<String, String> auditorium1 = new HashMap<>();
        auditorium1.put("name", "Sal 1");
        auditorium1.put("rows", "10");
        auditorium1.put("seatsPerRow", "20");
        dataHandler.insertData("Auditoriums", auditorium1);

        // Create the second auditorium
        HashMap<String, String> auditorium2 = new HashMap<>();
        auditorium2.put("name", "Sal 2");
        auditorium2.put("rows", "23");
        auditorium2.put("seatsPerRow", "10");
        dataHandler.insertData("Auditoriums", auditorium2);

        // Create the third auditorium
        HashMap<String, String> auditorium3 = new HashMap<>();
        auditorium3.put("name", "Sal 3");
        auditorium3.put("rows", "15");
        auditorium3.put("seatsPerRow", "32");
        dataHandler.insertData("Auditoriums", auditorium3);
    }

    /**
     * Create mock data for films
     */
    private void createFilmInfo() {
        // Create the film table.
        String query = "CREATE TABLE Films (" +
            "fID INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(100) NOT NULL," +
            "rating DOUBLE NOT NULL," +
            "minAge INT NOT NULL" +
        ")";
        dataHandler.createTable(query);

        // Create the first film
        HashMap<String, String> film1 = new HashMap<>();
        film1.put("name", "STAR WARS: THE LAST JEDI");
        film1.put("rating", "9.5");
        film1.put("minAge", "12");
        dataHandler.insertData("Films", film1);

        // Create the second film
        HashMap<String, String> film2 = new HashMap<>();
        film2.put("name", "SPOOR");
        film2.put("rating", "6.5");
        film2.put("minAge", "18");
        dataHandler.insertData("Films", film2);

        // Create the third film
        HashMap<String, String> film3 = new HashMap<>();
        film3.put("name", "KEDI");
        film3.put("rating", "7.4");
        film3.put("minAge", "7");
        dataHandler.insertData("Films", film3);
    }

    /**
     * Create mock data for screenings
     */
    private void createScreeningInfo() {
        // Create the film table.
        String query = "CREATE TABLE Screenings (" +
            "sID INT PRIMARY KEY AUTO_INCREMENT," +
            "filmID INT NOT NULL," +
            "auditoriumID DOUBLE NOT NULL," +
            "startTime LONG NOT NULL" +
        ")";
        dataHandler.createTable(query);

        // Create the first screening
        HashMap<String, String> screening1 = new HashMap<>();
        screening1.put("filmID", "1");
        screening1.put("auditoriumID", "1");
        String startTime = "" + new Date(1512426600000L).getTime();
        screening1.put("startTime", startTime);
        dataHandler.insertData("Screenings", screening1);

        // Create a second screening
        HashMap<String, String> screening2 = new HashMap<>();
        screening2.put("filmID", "2");
        screening2.put("auditoriumID", "3");
        startTime = "" + new Date(1512728100000L).getTime();
        screening2.put("startTime", startTime);
        dataHandler.insertData("Screenings", screening2);

        // Create a third screening
        HashMap<String, String> screening3 = new HashMap<>();
        screening3.put("filmID", "3");
        screening3.put("auditoriumID", "2");
        startTime = "" + new Date(1514149200000L).getTime();
        screening3.put("startTime", startTime);
        dataHandler.insertData("Screenings", screening3);
    }
}
