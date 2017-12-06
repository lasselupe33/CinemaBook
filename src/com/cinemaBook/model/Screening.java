package com.cinemaBook.model;

import java.util.Date;

/**
 * This model contains information about a single screening (i.e. a movie being displayed in a given auditorium at a
 * given time)
 */
public class Screening {
    private int id;
    private Date startTime;
    private Film filmPlaying;
    private Auditorium auditorium;
    private SeatAssignment seatAssignment;

    public Screening(int screeningId, Date startTime, Film filmPlaying, Auditorium auditorium, SeatAssignment seatAssignment) {
        this.id = screeningId;
        this.startTime = startTime;
        this.filmPlaying = filmPlaying;
        this.auditorium = auditorium;
        this.seatAssignment = seatAssignment;
    }

    public int getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Film getFilm() {
        return filmPlaying;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public SeatAssignment getSeatAssignment() { return seatAssignment; }
}
