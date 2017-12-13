package com.cinemaBook.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import static java.util.stream.Collectors.joining;

/**
 * This class contains the core functionality to connect to the database and will be extends by subHandlers
 */
public abstract class DataHandler {
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

    /**
     * The constructor sets up the connection with the database and creates the default statement.
     */
    public DataHandler() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (Exception e) {
            throw new Error("An error occurred when attempting to register the driver. Error: " + e.getMessage());
        }
    }

    /**
     * @return returns a new connection to be used
     */
    protected Connection createConnection() {
        try {
            // If more than a timeout amount of time has passed, create a new connection
            if (System.currentTimeMillis() > this.connectionCreationTime + this.timeOut) {
                // Close the old connection if one exists
                if (this.connectionCreationTime != 0) {
                    this.connection.close();
                }

                // Create the new connection
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
    protected Statement createStatement() {
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
    protected void clearData() {
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
    protected void createTable(String query) {
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
    protected void insertData(String table, HashMap<String, String> columns) {
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
}
