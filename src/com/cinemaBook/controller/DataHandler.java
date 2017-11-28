package com.cinemaBook.controller;

import java.sql.*;

public class DataHandler {
    // Database credentials
    static final String db = "sql11207684";
    static final String user = "sql11207684";
    static final String password = "tDT6BhIv9L";

    // Database access url
    static final String db_url = "jdbc:mysql://sql11.freemysqlhosting.net/" + db;
    private Connection connection;
    private Statement statement;

    /**
     * The constructor sets up the connection with the database.
     */
    public DataHandler() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            this.connection = DriverManager.getConnection(db_url, user, password);
            this.statement = this.connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Statement getStatement() {
        return statement;
    }
}
