package com.cinemaBook.model;

public class Auditorium {
    private String name;
    private int[][] seats;

    public Auditorium(String name, int rows, int seatPerRow) {
        this.name = name;
        this.seats = new int[rows][seatPerRow];
    }

    public int getAmountOfSeats() {
        return this.seats.length * this.seats[0].length;
    }

    public String getName() {
        return name;
    }
}
