package com.cinemaBook.model;

import com.cinemaBook.model.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    private Customer customer;

    @Before
    public void setupCustomer() {
        this.customer = new Customer("Lasse Agersten", "+4512345678", "someemail@gmail.com");
    }

    @Test
    public void testGetName() {
        assertEquals("Lasse Agersten", customer.getName());
    }

    @Test
    public void testGetPhone() {
        assertEquals("+4512345678", customer.getPhone());
    }

    @Test
    public void testGetEmail() {
        assertEquals("someemail@gmail.com", customer.getEmail());
    }
}