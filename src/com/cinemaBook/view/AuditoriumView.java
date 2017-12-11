package com.cinemaBook.view;

import com.cinemaBook.controller.AuditoriumController;
import com.cinemaBook.globals.State;
import com.cinemaBook.model.Auditorium;
import com.cinemaBook.model.SeatAssignment;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * This class is used to render the auditorium view. Used when the user tries to select the desired seats.
 */
public class AuditoriumView extends JComponent {
    private Auditorium auditorium;
    private SeatAssignment seatAssignment;

    public AuditoriumView() {
        super();
    }

    public void display(Auditorium auditorium, SeatAssignment seatAssignment) {
        this.auditorium = auditorium;
        this.seatAssignment = seatAssignment;

        repaint();
    }

    public void paint(Graphics g) {
        Arrays.stream(seatAssignment.getSeats()).forEach(row -> Arrays.stream(row).forEach(seat -> {
            if (seat.isReserved()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
            }
            g.fillRect(seat.getColumn() * 10,seat.getRow() * 10,5,5);
        }));
    }
}
