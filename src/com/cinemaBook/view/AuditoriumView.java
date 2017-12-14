package com.cinemaBook.view;

import com.cinemaBook.model.Auditorium;
import com.cinemaBook.model.Seat;
import com.cinemaBook.model.SeatAssignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/**
 * This class is used to render the auditorium view. Used when the user tries to select the desired seats.
 */
public class AuditoriumView extends JComponent {
    private Auditorium auditorium;
    private SeatAssignment seatAssignment;
    private ArrayList<Seat> pendingSeats;

    public AuditoriumView(Function<Seat, Void> onAdd, Function<Integer, Void> onRemove) {
        super();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            int seatWidth = getWidth() / auditorium.getColumns();
            int seatHeight = (getHeight() - 10) / auditorium.getRows();
            int seatSize = (seatWidth > seatHeight ? seatHeight : seatWidth) - 2;
            int marginWidth = getWidth() - ((seatSize+2) * auditorium.getColumns());
            int marginHeight = getHeight() - ((seatSize+2) * auditorium.getRows());

            int col = (e.getX() - marginWidth/2) / (seatSize + 2);
            int row = (e.getY() - marginHeight/3) / (seatSize + 2);

            if (row > auditorium.getRows() || col > auditorium.getColumns() || seatAssignment.isSeatReserved(row, col)) {
                return;
            }

            for (int i = 0; i < pendingSeats.size(); i++) {
                if (pendingSeats.get(i).getColumn() == col && pendingSeats.get(i).getRow() == row) {
                    System.out.println(i);
                    onRemove.apply(i);
                    return;
                }
            }

            onAdd.apply(new Seat(row, col, true));
            }
        });
    }

    public void display(Auditorium auditorium, SeatAssignment seatAssignment, ArrayList<Seat> pendingSeats) {
        this.auditorium = auditorium;
        this.seatAssignment = seatAssignment;
        this.pendingSeats = pendingSeats;

        repaint();
    }

    public void paint(Graphics g) {
        int seatWidth = getWidth() / auditorium.getColumns();
        int seatHeight = (getHeight() - 10) / auditorium.getRows();
        int seatSize = (seatWidth > seatHeight ? seatHeight : seatWidth) - 2;

        int marginWidth = getWidth() - ((seatSize+2) * auditorium.getColumns());
        int marginHeight = getHeight() - ((seatSize+2) * auditorium.getRows());

        Arrays.stream(seatAssignment.getSeats()).forEach(row -> Arrays.stream(row).forEach(seat -> {
            if (seat.isReserved()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
            }

            g.fillRect(marginWidth/2 + seat.getColumn() * (seatSize + 2), marginHeight/3 + seat.getRow() * (seatSize + 2), seatSize, seatSize);
        }));

        pendingSeats.forEach(seat -> {
            g.setColor(Color.BLUE);
            g.fillRect(marginWidth/2 + seat.getColumn() * (seatSize + 2), marginHeight/3 + seat.getRow() * (seatSize + 2), seatSize, seatSize);
        });

        g.setColor(Color.GRAY);
        g.fillRect(marginWidth / 2, getHeight() - 10, (seatSize+2)*auditorium.getColumns() - 2, 5);
    }
}
