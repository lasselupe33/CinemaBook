package com.cinemaBook.model;

public class Film {
    private int id;
    private String name;
    private String description;
    private double rating;
    private int minAge;

    public Film(int id, String name, String description, double rating, int minAge) {
        this.id = id;
        this.name = name;
        this.description = description;
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
