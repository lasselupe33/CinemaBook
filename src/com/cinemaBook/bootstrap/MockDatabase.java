package com.cinemaBook.bootstrap;

import com.cinemaBook.data.BookingsDataHandler;
import com.cinemaBook.data.DataHandler;
import com.cinemaBook.data.ScreeningsDataHandler;
import com.cinemaBook.model.Booking;
import com.cinemaBook.model.Customer;
import com.cinemaBook.model.Screening;
import com.cinemaBook.model.Seat;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The sole purpose of this class is to mock the database with dummy data if this haven't been done yet.
 */
public class MockDatabase extends DataHandler {
    /**
     * Initialize the main data handler
     */
    public MockDatabase() {
        super();
    }

    /**
     * When this method is called, it'll go ahead and insert mockup data into the database.
     *
     * NB: This function will drop tables, use only when necessary!
     */
    public void createDatabaseMockUp() {
        this.clearData();
        createAuditoriumInfo();
        createFilmInfo();
        createScreeningInfo();
        createCustomersTable();
        createSeatAssignmentTable();
        createBookingTable();
    }

    /**
     * Create mock data for the auditoriums
     */
    private void createAuditoriumInfo() {
        // Create the auditorium table.
        String query = "CREATE TABLE Auditoriums (" +
            "auditorium_id INT PRIMARY KEY AUTO_INCREMENT," +
            "auditorium_name VARCHAR(100) NOT NULL," +
            "auditorium_rows INT NOT NULL," +
            "auditorium_columns INT NOT NULL" +
        ")";
        this.createTable(query);

        // Create the first auditorium
        HashMap<String, String> auditorium1 = new HashMap<>();
        auditorium1.put("auditorium_name", "Sal 1");
        auditorium1.put("auditorium_rows", "10");
        auditorium1.put("auditorium_columns", "20");
        this.insertData("Auditoriums", auditorium1);

        // Create the second auditorium
        HashMap<String, String> auditorium2 = new HashMap<>();
        auditorium2.put("auditorium_name", "Sal 2");
        auditorium2.put("auditorium_rows", "23");
        auditorium2.put("auditorium_columns", "10");
        this.insertData("Auditoriums", auditorium2);

        // Create the third auditorium
        HashMap<String, String> auditorium3 = new HashMap<>();
        auditorium3.put("auditorium_name", "Sal 3");
        auditorium3.put("auditorium_rows", "15");
        auditorium3.put("auditorium_columns", "32");
        this.insertData("Auditoriums", auditorium3);
    }

    /**
     * Create mock data for films
     */
    private void createFilmInfo() {
        // Create the film table.
        String query = "CREATE TABLE Films (" +
            "film_id INT PRIMARY KEY AUTO_INCREMENT," +
            "film_name VARCHAR(100) NOT NULL," +
            "film_description VARCHAR(255) NOT NULL, " +
            "film_rating DOUBLE NOT NULL," +
            "film_minAge INT NOT NULL" +
        ")";
        this.createTable(query);

        // Create the first film
        HashMap<String, String> film1 = new HashMap<>();
        film1.put("film_name", "STAR WARS: THE LAST JEDI");
        film1.put("film_description", "Star Wars er en science fiction-saga, som foregår i \"en fjern, fjern galakse for meget længe siden\".");
        film1.put("film_rating", "9.5");
        film1.put("film_minAge", "12");
        this.insertData("Films", film1);

        // Create the second film
        HashMap<String, String> film2 = new HashMap<>();
        film2.put("film_name", "SPOOR");
        film2.put("film_description", "Janina Duszejko, an elderly woman, lives alone in the Klodzko Valley where a series of mysterious crimes are committed. Duszejko is convinced that she knows who or what is the murderer, but nobody believes her.");
        film2.put("film_rating", "6.5");
        film2.put("film_minAge", "18");
        this.insertData("Films", film2);

        // Create the third film
        HashMap<String, String> film3 = new HashMap<>();
        film3.put("film_name", "KEDI");
        film3.put("film_description", "Dokumentaren Kedi skildrer den tyrkiske by, Istanbul, gennem en raekke gadekattes oejne.");
        film3.put("film_rating", "7.4");
        film3.put("film_minAge", "7");
        this.insertData("Films", film3);
    }

    /**
     * Create mock data for screenings
     */
    private void createScreeningInfo() {
        // Create the film table.
        String query = "CREATE TABLE Screenings (" +
            "screening_id INT PRIMARY KEY AUTO_INCREMENT," +
            "film_id INT NOT NULL," +
            "auditorium_id INT NOT NULL," +
            "screening_startTime DATETIME NOT NULL," +
            "FOREIGN KEY (film_id) REFERENCES Films(film_id)," +
            "FOREIGN KEY (auditorium_id) REFERENCES Auditoriums(auditorium_id)" +
        ")";
        this.createTable(query);

        // Create the first screening
        HashMap<String, String> screening1 = new HashMap<>();
        screening1.put("film_id", "1");
        screening1.put("auditorium_id", "1");
        screening1.put("screening_startTime", "2017-12-12 19:30:00");
        this.insertData("Screenings", screening1);

        // Create a second screening
        HashMap<String, String> screening2 = new HashMap<>();
        screening2.put("film_id", "2");
        screening2.put("auditorium_id", "3");
        screening2.put("screening_startTime", "2017-12-30 21:00:00");
        this.insertData("Screenings", screening2);

        // Create a third screening
        HashMap<String, String> screening3 = new HashMap<>();
        screening3.put("film_id", "3");
        screening3.put("auditorium_id", "2");
        screening3.put("screening_startTime", "2017-12-20 12:45:00");
        this.insertData("Screenings", screening3);
    }

    /**
     * Create table for customers
     */
    private void createCustomersTable() {
        // Create the customer table
        String query = "CREATE TABLE Customers (" +
            "customer_id INT PRIMARY KEY AUTO_INCREMENT," +
            "customer_name VARCHAR(100) NOT NULL," +
            "customer_phone VARCHAR(100) NOT NULL," +
            "customer_email VARCHAR(100) NOT NULL" +
        ")";
        this.createTable(query);
    }

    /**
     * Create table for seatAssignment
     */
    private void createSeatAssignmentTable() {
        // Create the table
        String query = "CREATE TABLE SeatAssignment (" +
            "screening_id INT NOT NULL," +
            "row INT NOT NULL," +
            "col INT NOT NULL," +
            "isReserved BOOLEAN," +
            "CONSTRAINT UC_Person UNIQUE (screening_id, row, col)" +
        ")";
        this.createTable(query);
    }

    /**
     * Create table for bookings
     */
    private void createBookingTable() {
        // Create the table
        String query = "CREATE TABLE Bookings (" +
            "booking_id INT PRIMARY KEY AUTO_INCREMENT," +
            "customer_id INT NOT NULL," +
            "screening_id INT NOT NULL," +
            "reserved_seats VARCHAR(20384) NOT NULL," +
            "FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)," +
            "FOREIGN KEY (screening_id) REFERENCES Screenings(screening_id)" +
        ")";
        this.createTable(query);

        // CREATE TESTBOOKING1
        ArrayList<Seat> reservedSeats = new ArrayList<>();

        // Create seat reservations
        reservedSeats.add(new Seat(2, 3, true));
        reservedSeats.add(new Seat(4, 5, true));

        // Create booking model
        Customer customer = new Customer("Lasse Agersten", "123", "hej@gmail.com");
        Screening screening = ScreeningsDataHandler.getInstance().getScreenings(-1).get(0);
        Booking booking = new Booking(customer, screening, reservedSeats);

        // Submit booking model
        BookingsDataHandler.getInstance().submitBooking(booking);

        // CREATE TESTBOOKING2
        ArrayList<Seat> reservedSeats2 = new ArrayList<>();

        // Create seat reservations
        reservedSeats2.add(new Seat(1, 5, true));
        reservedSeats2.add(new Seat(1, 4, true));
        reservedSeats2.add(new Seat(1, 3, true));

        // Create booking model
        customer = new Customer("Lasse Agersten", "123", "hej@gmail.com");
        screening = ScreeningsDataHandler.getInstance().getScreenings(-1).get(0);
        booking = new Booking(customer, screening, reservedSeats2);

        // Submit booking model
        BookingsDataHandler.getInstance().submitBooking(booking);

        // CREATE TESTBOOKING3
        ArrayList<Seat> reservedSeats3 = new ArrayList<>();

        // Create seat reservations
        reservedSeats3.add(new Seat(3, 5, true));

        // Create booking model
        customer = new Customer("Mads Rued", "1233446", "hej@gmail.com");
        screening = ScreeningsDataHandler.getInstance().getScreenings(-1).get(0);
        booking = new Booking(customer, screening, reservedSeats3);

        // Submit booking model
        BookingsDataHandler.getInstance().submitBooking(booking);
    }
}
