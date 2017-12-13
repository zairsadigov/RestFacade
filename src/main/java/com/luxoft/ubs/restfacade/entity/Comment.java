package com.luxoft.ubs.restfacade.entity;

import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return getId() == comment.getId() &&
                getMovieId() == comment.getMovieId() &&
                Objects.equals(getUsername(), comment.getUsername()) &&
                Objects.equals(getMessage(), comment.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMovieId());
    }

    public long getId() {
        return id;
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
