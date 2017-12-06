package com.cinemaBook.controller;

/**
 * This abstract is used to extend view specific controllers with base functionality
 */
public abstract class Controller {
    /**
     * The initialize method will be called automatically by the router once the selected controller gets activated
     */
    public void initialize() {
        throw new Error("Please implement custom initialize method!");
    }
}
