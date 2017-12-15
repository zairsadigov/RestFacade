package com.luxoft.ubs.restfacade.service;

import com.luxoft.ubs.restfacade.entity.Movie;
import com.luxoft.ubs.restfacade.repository.IMovieRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
public class MovieService {

    private final IMovieRepository movieRepository;

    public MovieService(IMovieRepository movieRepository) {
        Assert.notNull(movieRepository, "movieRepository is required");

        this.movieRepository = movieRepository;
    }

    @Async
    @Secured("ROLE_ADMIN")
    public CompletableFuture<Movie> addMovie(Movie movie) {
        Assert.notNull(movie, "movie is required");

        return CompletableFuture.completedFuture(movieRepository.save(movie));
    }

    @Async
    @Secured("ROLE_ADMIN")
    public CompletableFuture<Movie> getMovieById(long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found movie with id: " + id));

        return CompletableFuture.completedFuture(movie);
    }
}
