package com.cinemaBook.model;

public class Film {
    private String name;
    private String description;
    private int rating;
    private int minAge;

    public Film(String name, String description, int rating, int minAge) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.minAge = minAge;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public int getMinAge() {
        return minAge;
    }
}
