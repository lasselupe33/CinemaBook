package com.cinemaBook.model;

public class SeatAssignment {
    private Seat[][] seatAssignment;

    public SeatAssignment(Seat[][] seatAssignment) {
        this.seatAssignment = seatAssignment;
    }

    public int getAmountOfAvailableSeats() {
        int availableSeats = 0;

        for (int i = 0; i < seatAssignment.length; i++) {
            for (int j = 0; j < seatAssignment[0].length; j++) {
                if (!seatAssignment[i][j].isReserved()) {
                    availableSeats++;
                }
            }
        }

        return availableSeats;
    }

    public boolean isSeatReserved(int row, int column) {
        return seatAssignment[row][column].isReserved();
    }

    public Seat[][] getSeats() {
        return seatAssignment;
    }

    public void setSeat(int row, int col, boolean reserved) {
        seatAssignment[row][col] = new Seat(row, col, reserved);
    }
}
