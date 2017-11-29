package com.cinemaBook.controller;

/**
 * This abstract is used to extend view specific controllers with base functionality
 */
public abstract class Controller {
    public void initialize() {
        throw new Error("Please implement custom initialize method!");
    }
}
