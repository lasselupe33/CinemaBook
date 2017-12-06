package com.cinemaBook.model;

public class Seat {
    private int row;
    private int column;
    private boolean reserved;

    public Seat(int row, int column, boolean isReserved) {
        this.row = row;
        this.column = column;
        this.reserved = isReserved;
    }

    public String getSeatId() {
        return row + " - " + column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isReserved() { return reserved; }

    public void setReserved(boolean reservedState) {
        this.reserved = reservedState;
    }
}
