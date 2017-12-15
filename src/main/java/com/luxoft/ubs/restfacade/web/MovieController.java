package com.luxoft.ubs.restfacade.web;

import com.luxoft.ubs.restfacade.entity.Comment;
import com.luxoft.ubs.restfacade.entity.Movie;
import com.luxoft.ubs.restfacade.service.CommentService;
import com.luxoft.ubs.restfacade.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@EnableWebMvc
public class MovieController {

    private MovieService movieService;
    private CommentService commentService;

    public MovieController(MovieService movieService, CommentService commentService) {
        Assert.notNull(movieService, "movieService is required");
        Assert.notNull(commentService, "commentService is required");

        this.movieService = movieService;
        this.commentService = commentService;
    }

    @GetMapping(path = "/{id:\\d+}")
    public ResponseEntity<MovieAndComments> getMovieAndCommentsByMovieId(@PathVariable Long id) throws ExecutionException, InterruptedException {
        CompletableFuture<Movie> completableFuture = movieService.getMovieById(id);
        CompletableFuture<Collection<Comment>> collectionCompletableFuture = commentService.getCommentsByMovieId(id);

        return ok(new MovieAndComments(completableFuture.get(), collectionCompletableFuture.get()));
    }

    class MovieAndComments {
        private Movie movie;
        private Collection<Comment> commentCollection;

        MovieAndComments(Movie movie, Collection<Comment> commentCollection) {
            this.movie = movie;
            this.commentCollection = commentCollection;
        }

        public Movie getMovie() {
            return movie;
        }

        public Collection<Comment> getCommentCollection() {
            return commentCollection;
        }

        boolean addComments(Collection<Comment> commentCollection) {
            return this.commentCollection.addAll(commentCollection);
        }
    }
}
