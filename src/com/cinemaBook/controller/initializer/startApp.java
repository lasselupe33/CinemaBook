package com.cinemaBook.controller.initializer;

public class startApp {
    public static void main(String[] args) {
        // Create database mock up
        MockDatabase mockup = new MockDatabase();
        mockup.createDatabaseMockup();
    }
}
