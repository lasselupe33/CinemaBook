package com.cinemaBook.globals;

import com.cinemaBook.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.sql.*;

import static java.util.stream.Collectors.joining;

/**
 * This singleton has the responsibility for handling all communication between the database and the main application.
 */
public class DataHandler {
    // Database credentials
    private static final String db = "cinema_book";
    private static final String user = "welcometo";
    private static final String password = "thecinema";

    // Database access url
    private static final String db_url = "jdbc:mysql://mydb.itu.dk/" + db;

    // Connection handling
    private Connection connection;
    private long connectionCreationTime = 0;
    private long timeOut = 60000;

    // Store a reference to the dataHandler
    private static final DataHandler instance = new DataHandler();

    /**
     * The constructor sets up the connection with the database and creates the default statement.
     */
    private DataHandler() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (Exception e) {
            throw new Error("An error occurred when attempting to register the driver. Error: " + e.getMessage());
        }
    }

    /**
     * Returns a reference to the DataHandler
     */
    public static DataHandler getInstance() { return instance; }

    /**
     * @return returns a new connection to be used
     */
    private Connection createConnection() {
        try {
            // If more than a timeout amount of time has passed, create a new connection
            if (System.currentTimeMillis() > this.connectionCreationTime + this.timeOut) {
                this.connectionCreationTime = System.currentTimeMillis();
                this.connection = DriverManager.getConnection(db_url, user, password);
            }

            // Return the connection
            return this.connection;
        } catch (Exception e) {
            throw new Error("Failed to get database connection! " + e.getMessage());
        }
    }

    /**
     * @return returns a new statement to be used
     */
    private Statement createStatement() {
        try {
            return this.createConnection().createStatement();
        } catch (Exception e) {
            throw new Error("Failed to get database connection! " + e.getMessage());
        }
    }

    /**
     * This function is used to clear all tables within the database.
     *
     * NB: This method clears all tables in the database. It should be used with caution!
     */
    public void clearData() {
        try {
            // Delete all data unconditionally
            this.createStatement().executeQuery("SET FOREIGN_KEY_CHECKS = 0");

            // Execute query to get all tables in database
            String query = "SHOW TABLES";
            ResultSet rs = this.createStatement().executeQuery(query);

            // Create a secondary statement to ensure resultSet won't be overwritten
            Statement clearStatement = this.createStatement();

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
                this.createStatement().executeQuery("SET FOREIGN_KEY_CHECKS = 1");
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
            this.createStatement().executeUpdate(query);
        } catch (Exception e) {
            throw new Error("Failed to create table.. Error: " + e.getMessage());
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
            this.createStatement().executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
            System.out.println("Failed to insert data..");
            System.out.println(table);
            System.out.println(columns);
            System.out.println(e.getMessage());
        }
    }

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
                SeatAssignment seatAssignment = new SeatAssignment(getSeatAssignment(screeningId, auditorium.getRows(), auditorium.getColumns()));

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
     * This method returns all bookings that have been made
     */
    public ArrayList<Booking> getBookings() {
        try {
            String query = "SELECT * FROM Bookings";
            ResultSet rs = this.createStatement().executeQuery(query);

            ArrayList<Booking> bookings = new ArrayList<>();

            while (rs.next()) {
                // Get associated screening
                Screening screening = this.getScreenings(rs.getInt("screening_id")).get(0);

                // Get the reserved seats in proper format
                ArrayList<Seat> reservedSeats = this.convertReservedSeatsStringToArray(rs.getString("reserved_seats"));

                // Get the associated customer
                Customer customer = this.getCustomer(rs.getInt("customer_id"));

                // Create a booking model with the fetched information
                Booking booking = new Booking(customer, screening, reservedSeats);

                // Store its id as well
                booking.setId(rs.getInt("booking_id"));

                // Add the booking to the arraylist
                bookings.add(booking);
            }

            // Return the bookings
            return bookings;
        } catch (Exception e) {
            throw new Error("Error while getting bookings. Error: " + e.getMessage());
        }
    }

    /**
     * This method is made in order to get a list of bookings made by a customer
     * @param customer A customer model
     * @return an arrayList of bookings made by a customer - Will be null if no customer exist
     */
    public ArrayList<Booking> getBookingsByCustomer(Customer customer) {
        int customer_id = this.getCustomerId(customer);

        // If no customer id exist, then there will be no associated bookings..
        if (customer_id == -1) {
            return null;
        } else {
            try {
                // Execute query to get all bookings for the customer
                String query = "SELECT * FROM Bookings WHERE customer_id = '" + customer_id + "';";
                ResultSet rs = this.createStatement().executeQuery(query);

                ArrayList<Booking> bookings = new ArrayList<>();

                // Loop over bookings and add them to the array
                while (rs.next()) {
                    // Get the associated screening
                    Screening screening = this.getScreenings(rs.getInt("screening_id")).get(0);

                    // Get the reserved seats and format them into an array usable in the code.
                    ArrayList<Seat> reservedSeats = this.convertReservedSeatsStringToArray(rs.getString("reserved_seats"));

                    // Create a booking model with the fetched information
                    Booking booking = new Booking(customer, screening, reservedSeats);

                    // Store its id as well
                    booking.setId(rs.getInt("booking_id"));

                    // Add the booking to the list of user bookings
                    bookings.add(booking);
                }

                return bookings;
            } catch (Exception e) {
                throw new Error("Couldn't fetch bookings for customer with id=" + customer_id + ". " + e.getMessage());
            }
        }
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

        // Get the screening ID
        int screening_id = booking.getScreening().getId();

        // Update the reserved seats in the database
        insertSeatReservation(screening_id, booking.getReservedSeats());

        // Store the booking
        insertBooking(customer_id, screening_id, booking);
    }

    /**
     * When updating a booking, the old seats will be cleared from the seatAssignment and afterwards the new will be
     * added.
     *
     * Furthermore the booking entry will be updated as well.
     *
     * @param booking_id
     * @param newReservedSeats
     */
    public void updateBooking(int booking_id, ArrayList<Seat> newReservedSeats) {
        try {
            // First, get the list of old seats
            ResultSet rs = this.createStatement().executeQuery("SELECT screening_id, reserved_seats FROM Bookings WHERE booking_id = '" + booking_id + "';");
            rs.next();
            ArrayList<Seat> oldReservedSeats = this.convertReservedSeatsStringToArray(rs.getString("reserved_seats"));

            // Delete old seats
            int screening_id = rs.getInt("screening_id");
            this.deleteSeatReservation(screening_id, oldReservedSeats);

            // Now add the list of new seats
            this.insertSeatReservation(screening_id, newReservedSeats);

            // Finally update the booking with the new seats
            String reservedSeatsString = this.convertReservedSeatsToString(newReservedSeats);
            this.createStatement().executeUpdate("UPDATE Bookings SET reserved_seats = '" + reservedSeatsString + "' WHERE booking_id = '" + booking_id + "';");
        } catch (Exception e) {
            throw new Error("Failed to update booking: " + e.getMessage());
        }
    }

    /**
     * When called this deletes a booking along with its reserved seats.
     */
    public void deleteBooking(int booking_id) {
        try {
            // First, get the list of seats to delete.
            ResultSet rs = this.createStatement().executeQuery("SELECT screening_id, reserved_seats FROM Bookings WHERE booking_id = '" + booking_id + "';");
            rs.next();
            ArrayList<Seat> seatsToBeDeleted = this.convertReservedSeatsStringToArray(rs.getString("reserved_seats"));

            // Delete the old seats
            int screening_id = rs.getInt("screening_id");
            this.deleteSeatReservation(screening_id, seatsToBeDeleted);

            // Now delete the booking!
            this.createStatement().executeUpdate("DELETE FROM Bookings WHERE booking_id = '" + booking_id + "';");
        } catch (Exception e) {
            throw new Error("Failed to delete booking! " + e.getMessage());
        }
    }

    /**
     * This function inserts a booking into the booking table
     * @param customer_id The id of the customer
     * @param screening_id The id of the screening
     * @param booking The bookingModel
     */
    private void insertBooking(int customer_id, int screening_id, Booking booking) {
        HashMap<String, String> bookingMap = new HashMap<>();
        bookingMap.put("customer_id", "" + customer_id);
        bookingMap.put("screening_id", "" + screening_id);
        bookingMap.put("reserved_seats", convertReservedSeatsToString(booking.getReservedSeats()));
        this.insertData("Bookings", bookingMap);

        try {
            // Create a new statement to get the id of the newly inserted booking
            ResultSet idResultSet = this.createStatement().executeQuery("SELECT LAST_INSERT_ID() id");
            idResultSet.next();

            // Update the booking model with the newly created id
            booking.setId(idResultSet.getInt("id"));
        } catch (Exception e) {
            throw new Error("Failed to set the id of the booking.." + e.getMessage());
        }
    }

    /**
     * This function generates the seatAssignment for a given screening.
     *
     * @param screeningId The id of the screening.
     * @param auditoriumRows The amount of rows in the auditorium where the film is being played.
     * @param auditoriumColumns The amount of columns in the auditorium.
     * @return The seatAssignments in a two dimensional array
     */
    private Seat[][] getSeatAssignment(int screeningId, int auditoriumRows, int auditoriumColumns) {
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
     * Internal helper that returns the id of a customer
     * @param customer
     * @return The id of the customer. Will return "-1" if the customer doesn't exist in the database
     */
    private int getCustomerId(Customer customer) {
        try {
            String query = "SELECT customer_id FROM Customers WHERE " +
                    "customer_name = '" + customer.getName() + "' AND " +
                    "customer_phone = '" + customer.getPhone() + "' AND " +
                    "customer_email = '" + customer.getEmail() + "';";

            ResultSet rs = this.createStatement().executeQuery(query);

            int customer_id;

            // If a user haven't been found, then set the customer id to "-1", i.e. not existing
            if (!rs.next()) {
                customer_id = -1;
            } else {
                // Else, retrieve the customerID
                customer_id = rs.getInt("customer_id");
            }

            return customer_id;
        } catch (Exception e) {
            throw new Error("Failed to retrieve customer id! " + e.getMessage());
        }
    }

    /**
     * This method returns a customer model
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    private Customer getCustomer(int customerId) {
        try {
            String query = "SELECT * FROM Customers";
            ResultSet rs = this.createStatement().executeQuery(query);
            rs.next();

            // Create and return the customer
            String name = rs.getString("customer_name");
            String phone = rs.getString("customer_phone");
            String email = rs.getString("customer_email");

            return new Customer(name, phone, email);
        } catch (Exception e) {
            throw new Error("Error while attempting to fetch customer. Error: " + e.getMessage());
        }
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
            // Get customer id
            int customer_id = getCustomerId(customer);

            // Only insert customer if he/she doens't exist already in the database
            if (customer_id == -1) {
                HashMap<String, String> customerMap = new HashMap<>();
                customerMap.put("customer_name", customer.getName());
                customerMap.put("customer_phone", customer.getPhone());
                customerMap.put("customer_email", customer.getEmail());
                this.insertData("Customers", customerMap);

                // Create a new statement to get the id of the newly inserted customer
                ResultSet idResultSet = this.createStatement().executeQuery("SELECT LAST_INSERT_ID() id");
                idResultSet.next();

                // Return the id of the newly inserted customer
                return idResultSet.getInt("id");
            } else {
                // Else just return the customer id
                return customer_id;
            }

        } catch (Exception e) {
            throw new Error("Error while inserting user to database... " + e.getMessage());
        }
    }

    /**
     * Internal helper that updates the reservation of seats for a given screening
     */
    private void insertSeatReservation(int screeningId, ArrayList<Seat> seats) {
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
    private void deleteSeatReservation(int screeningId, ArrayList<Seat> seats) {
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

    /**
     * Internal helper that formats the reserved seats of a booking to a string
     * @param reservedSeats the ArrayList of reserved seats of the booking
     * @return A string in the form of "row1,col1:row2,col2:row3:col3....
     */
    private String convertReservedSeatsToString(ArrayList<Seat> reservedSeats) {
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
    private ArrayList<Seat> convertReservedSeatsStringToArray(String reservedSeatsString) {
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
