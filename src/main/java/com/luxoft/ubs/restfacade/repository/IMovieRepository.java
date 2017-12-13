package com.luxoft.ubs.restfacade.repository;

import com.luxoft.ubs.restfacade.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IMovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findById(Long id);
}