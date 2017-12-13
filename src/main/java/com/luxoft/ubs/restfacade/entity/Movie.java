package com.luxoft.ubs.restfacade.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.Assert;

public class Movie extends AbstractPersistable<Long> {

    private String title;
    private String description;

    public Movie(String title, String description) {
        Assert.hasText(title, "title is required");
        Assert.hasText(description, "description is required");

        this.title = title;
        this.description = description;
    }

    protected Movie() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
