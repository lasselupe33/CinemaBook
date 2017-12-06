package com.cinemaBook.globals;

import com.cinemaBook.model.*;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import java.util.Map;

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
     * Returns a reference to the DataHandler
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
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
            System.out.println("Failed to insert data..");
            System.out.println(e.getMessage());
        }
    }

    public void getScreeningSeatings() {

    }

    public void getBookings() {

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

                // Get seat assignment for the given screening
                SeatAssignment seatAssignment = new SeatAssignment(getSeatAssignment(screeningId, auditorium.getRows(), auditorium.getColumns()));

                // Create screeningModel and add it to the array of all screenings
                screenings.add(new Screening(screeningId, startTime, film, auditorium, seatAssignment));
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
            Statement seatAssignmentStatement = connection.createStatement();
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
     * This function submits a booking to the database based on the booking object passed.
     *
     * In other words, it creates a customer if he/she doesn't exist yet, while also updating the seatAssignment for the
     * given screening
     */
    public void submitBooking(Booking booking) {
        // Insert the customer into the database
        int customer_id = insertCustomer(booking.getCustomer());

        int screening_id = booking.getScreening().getId();

        // Update the reserved seats in the database
        insertSeatReservation(screening_id, customer_id, booking.getReservedSeats());
    }


    /**
     * Internal helper that inserts a customer to a database (if the customer doesn't exist yet), and then returns the
     * customer id
     *
     * @param customer The customer object
     * @return The id of the inserted customer
     */
    private int insertCustomer(Customer customer) {
        try {
            String query = "SELECT customer_id FROM Customers WHERE " +
                    "customer_name = '" + customer.getName() + "' AND " +
                    "customer_phone = '" + customer.getPhone() + "' AND " +
                    "customer_email = '" + customer.getEmail() + "';";

            ResultSet rs = statement.executeQuery(query);

            int customer_id;

            // Only create a new user if none exists with the given information
            if (!rs.next()) {
                HashMap<String, String> customerMap = new HashMap<>();
                customerMap.put("customer_name", customer.getName());
                customerMap.put("customer_phone", customer.getPhone());
                customerMap.put("customer_email", customer.getEmail());
                this.insertData("Customers", customerMap);

                // Create a new statement to get the id of the newly inserted customer
                ResultSet idResultSet = statement.executeQuery("SELECT LAST_INSERT_ID() id");
                idResultSet.next();
                customer_id = idResultSet.getInt("id");
            } else {
                customer_id = rs.getInt("customer_id");
            }

            return customer_id;
        } catch (Exception e) {
            throw new Error("Error while inserting user to database... " + e.getMessage());
        }
    }

    /**
     * Internal helper that updates the reservation of seats for a given screening
     */
    private void insertSeatReservation(int screeningId, int customer_id, ArrayList<Integer[]> seats) {
        // Loop over all the seat reservations and insert the data into the database
        for (Integer[] seat : seats) {
            HashMap<String, String> seatInformation = new HashMap<>();
            // Create info map
            seatInformation.put("screening_id", "" + screeningId);
            seatInformation.put("customer_id", "" + customer_id);
            seatInformation.put("row", "" + seat[0]);
            seatInformation.put("col", "" + seat[1]);
            seatInformation.put("isReserved", "1");

            // Insert data!
            this.insertData("SeatAssignment", seatInformation);
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
