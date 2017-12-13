package com.cinemaBook.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FilmTest {
    Film testFilm;

    @Before
    public void setUp() throws Exception {
        testFilm = new Film(1, "Film", "Hejsa", 5.7, 12);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(testFilm.getName(), "Film");
    }

    @Test
    public void getRating() throws Exception {
        assertEquals(testFilm.getRating(), 5, 7);
    }

    @Test
    public void getMinAge() throws Exception {
        assertEquals(testFilm.getMinAge(), 12);
    }
}