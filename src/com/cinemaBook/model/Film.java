package com.cinemaBook.model;

public class Film {
    private String name;
    private double rating;
    private int minAge;

    public Film(String name, double rating, int minAge) {
        this.name = name;
        this.rating = rating;
        this.minAge = minAge;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public int getMinAge() {
        return minAge;
    }
}
