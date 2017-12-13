package com.luxoft.ubs.restfacade.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.Assert;

import javax.persistence.Entity;

@Entity
public class Comment extends AbstractPersistable<Long> {

    private String username;
    private String message;
    private long movieId;

    protected Comment() {
    }

    public Comment(String username, String message, long movieId) {
        Assert.hasText(username, "username is required");
        Assert.hasText(message, "message is required");
        Assert.isTrue(movieId > 0, "movieId has to be greater than zero. movieId = " + movieId);

        this.username = username;
        this.message = message;
        this.movieId = movieId;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public long getMovieId() {
        return movieId;
    }
}
