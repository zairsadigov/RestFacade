package com.luxoft.ubs.restfacade.web;

import com.luxoft.ubs.restfacade.entity.Comment;
import com.luxoft.ubs.restfacade.entity.Movie;
import com.luxoft.ubs.restfacade.service.CommentService;
import com.luxoft.ubs.restfacade.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.CompletableFuture;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MovieControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;
    @Autowired
    private CommentService commentService;

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        this.movie = new Movie("Test Movie Title", "Test movie description");
    }

    @Test
    @Rollback
    @WithMockUser(roles = {"ADMIN", "USER"})
    public void getMovieAndCommentsByMovieId() throws Exception {
        CompletableFuture<Movie> completableFuture = movieService.addMovie(movie);
        Movie retrievedMovie = completableFuture.get();

        Comment comment1 = new Comment("user1", "This is 1st scene to watch", retrievedMovie.getId());
        Comment comment2 = new Comment("user1", "This is 2nd scene to watch", retrievedMovie.getId());

        CompletableFuture<Comment> commentCompletableFuture1 = commentService.addComment(comment1);
        CompletableFuture<Comment> commentCompletableFuture2 = commentService.addComment(comment2);
        Comment retrievedComment1 = commentCompletableFuture1.get();
        Comment retrievedComment2 = commentCompletableFuture2.get();

        mockMvc.perform(get("/" + retrievedMovie.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"movie\":{\"id\":" +
                        retrievedMovie.getId() +
                        ",\"title\":\"" +
                        retrievedMovie.getTitle() +
                        "\",\"description\":\"" +
                        retrievedMovie.getDescription() +
                        "\"},\"commentCollection\":[{\"id\":" +
                        retrievedComment1.getId() +
                        ",\"username\":\"" +
                        retrievedComment1.getUsername() +
                        "\",\"message\":\"" +
                        retrievedComment1.getMessage() +
                        "\",\"movieId\":" +
                        retrievedComment1.getMovieId() +
                        "},{\"id\":" +
                        retrievedComment2.getId() +
                        ",\"username\":\"" +
                        retrievedComment2.getUsername() +
                        "\",\"message\":\"" +
                        retrievedComment2.getMessage() +
                        "\",\"movieId\":" +
                        retrievedComment2.getMovieId() +
                        "}]}"));
    }

}