package com.cinemaBook.model;

/**
 * This model contains information about a given customer.
 */
public class Customer {
    private String name;
    private String number;
    private String email;

    public Customer(String name, String number, String email) {
        this.name = name;
        this.number = number;
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
    public String getNumber() {
        return number;
    }

    /**
     * @return Returns the email of the customer
     */
    public String getEmail() {
        return email;
    }
}
