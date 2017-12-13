package com.cinemaBook.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuditoriumTest {
    Auditorium testAuditorium;

    @Before
    public void setUp() throws Exception {
        testAuditorium = new Auditorium(1, "Sal 1", 5, 10);
    }

    @Test
    public void getRows() throws Exception {
        assertEquals(testAuditorium.getRows(), 5);
    }

    @Test
    public void getColumns() throws Exception {
        assertEquals(testAuditorium.getColumns(), 10);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(testAuditorium.getName(), "Sal 1");
    }

}