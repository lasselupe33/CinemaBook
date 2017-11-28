package com.cinemaBook.model;

public class Screening {
    private int startTime;
    private Film filmPlaying;
    private Auditorium auditorium;

    public Screening(int startTime, Film filmPlaying, Auditorium auditorium) {
        this.startTime = startTime;
        this.filmPlaying = filmPlaying;
        this.auditorium = auditorium;
    }

    public int getStartTime() {
        return startTime;
    }

    public Film getFilmPlaying() {
        return filmPlaying;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }
}
