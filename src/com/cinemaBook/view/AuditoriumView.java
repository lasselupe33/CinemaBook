package com.cinemaBook.view;

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
        int seatWidth = getWidth() / auditorium.getColumns();
        int seatHeight = getHeight() / auditorium.getRows();
        int seatSize = (seatWidth > seatHeight ? seatHeight : seatWidth) - 2;

        int marginWidth = getWidth() - ((seatSize+2) * auditorium.getColumns());
        int marginHeight = getHeight() - ((seatSize+2) * auditorium.getRows());
        System.out.println(marginWidth);
        System.out.println(marginHeight);

        Arrays.stream(seatAssignment.getSeats()).forEach(row -> Arrays.stream(row).forEach(seat -> {
            if (seat.isReserved()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
            }

            g.fillRect(marginWidth/2 + seat.getColumn() * (seatSize + 2), marginHeight/2 + seat.getRow() * (seatSize + 2), seatSize, seatSize);
        }));
    }
}
