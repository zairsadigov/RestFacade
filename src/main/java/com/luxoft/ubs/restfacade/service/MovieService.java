package com.luxoft.ubs.restfacade.service;

import com.luxoft.ubs.restfacade.repository.IMovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MovieService {

    private final IMovieRepository movieRepository;

    public MovieService(IMovieRepository movieRepository) {
        Assert.notNull(movieRepository, "movieRepository is required");

        this.movieRepository = movieRepository;
    }
}
