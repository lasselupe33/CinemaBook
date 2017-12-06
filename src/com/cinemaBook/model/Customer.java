package com.cinemaBook.model;

import java.util.ArrayList;

/**
 * This model contains information about a given customer.
 */
public class Customer {
    private String name;
    private String phone;
    private String email;

    public Customer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * @return Returns the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * @return Returns the phone number of the customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return Returns the email of the customer
     */
    public String getEmail() {
        return email;
    }
}
