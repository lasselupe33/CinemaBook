package com.cinemaBook.model;

public class Auditorium {
    private int id;
    private String name;
    private int rows;
    private int columns;

    public Auditorium(int id, String name, int rows, int columns) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public int getAmountOfSeats() {
        return this.rows * this.columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public String getName() {
        return name;
    }
}
