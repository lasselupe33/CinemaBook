package com.cinemaBook.model;

import com.cinemaBook.model.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    /**
     * This test is made to ensure that upon creating a customer, that the methods that returns the fields, actually
     * do that.
     */
    @Test
    public void testCustomer() {
        Customer testCustomer = new Customer("Lasse Agersten", "+4512345678", "someemail@gmail.com");

        assertEquals("Lasse Agersten", testCustomer.getName());
        assertEquals("+4512345678", testCustomer.getNumber());
        assertEquals("someemail@gmail.com", testCustomer.getEmail());
    }
}