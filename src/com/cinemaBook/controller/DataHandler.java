package com.cinemaBook.controller;

import java.util.HashMap;
import java.sql.*;
import static java.util.stream.Collectors.joining;

public class DataHandler {
    // Database credentials
    private static final String db = "sql11207684";
    private static final String user = "sql11207684";
    private static final String password = "tDT6BhIv9L";

    // Database access url
    private static final String db_url = "jdbc:mysql://sql11.freemysqlhosting.net/" + db;
    private Connection connection;
    private Statement statement;

    /**
     * The constructor sets up the connection with the database and creates the default statement.
     */
    public DataHandler() {
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
}
