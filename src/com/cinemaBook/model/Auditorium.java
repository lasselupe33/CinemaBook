package com.cinemaBook.model;

public class Auditorium {
    private int id;
    private String name;
    private int[][] seats;

    public Auditorium(int id, String name, int rows, int columns) {
        this.id = id;
        this.name = name;
        this.seats = new int[rows][columns];
    }

    public int getAmountOfSeats() {
        return this.seats.length * this.seats[0].length;
    }
}
