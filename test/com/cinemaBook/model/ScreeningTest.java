package com.cinemaBook.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ScreeningTest {
    Screening testScreening;
    Film testFilm;
    Auditorium testAuditorium;
    Date startTime;
    int rows = 5;
    int columns = 10;
    Seat[][] testSeats;
    SeatAssignment testAssignemnt;

    @Before
    public void setUp() throws Exception {
        testSeats = new Seat[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                testSeats[i][j] = new Seat(i, j, false);
            }
        }

        testAssignemnt = new SeatAssignment(testSeats);
        startTime = new Date(12000);
        testFilm = new Film(1, "Film", "Hejsa", 5.7, 12);
        testAuditorium = new Auditorium(1, "Sal 1", 5, 10);
        testScreening = new Screening(1, startTime, testFilm, testAuditorium, testAssignemnt);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(testScreening.getId(), 1);
    }

    @Test
    public void getStartTime() throws Exception {
        assertEquals(testScreening.getStartTime(), startTime);
    }

    @Test
    public void getFilm() throws Exception {
        assertEquals(testScreening.getFilm(), testFilm);
    }

    @Test
    public void getAuditorium() throws Exception {
        assertEquals(testScreening.getAuditorium(), testAuditorium);
    }

    @Test
    public void getSeatAssignment() throws Exception {
        assertEquals(testScreening.getSeatAssignment(), testAssignemnt);
    }

}