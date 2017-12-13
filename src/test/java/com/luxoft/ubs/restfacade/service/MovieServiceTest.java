package com.luxoft.ubs.restfacade.service;

import com.luxoft.ubs.restfacade.entity.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        this.movie = new Movie("New Movie", "Description of New Movie");
    }

    @Test
    @Rollback
    public void addsMovieAndRetrievesToCompare() throws Exception {
        CompletableFuture<Movie> completableFuture = movieService.addMovie(movie);
        Movie movie1 = completableFuture.get();

        Assert.assertEquals(movie, movie1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addsNullAsMovieAndFails() throws Exception {
        movieService.addMovie(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void getsMovieByNegativeMovieIdAndFails() throws Exception {
        movieService.getMovieById(-1);
    }

    @Test
    @Rollback
    public void addsMovieAndGetsSameMovieByMovieIdAndCompares() throws Exception {
        CompletableFuture<Movie> completableFuture = movieService.addMovie(movie);
        CompletableFuture<Movie> completableFuture1 = movieService.getMovieById(movie.getId());

        Assert.assertEquals(completableFuture.get(), completableFuture1.get());
    }
}