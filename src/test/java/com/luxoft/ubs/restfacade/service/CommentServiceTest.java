package com.luxoft.ubs.restfacade.service;

import com.luxoft.ubs.restfacade.entity.Comment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    private Comment comment;

    @Before
    public void setUp() throws Exception {
        this.comment = new Comment("user1", "here is my comment", 1);
    }

    @Test
    @Rollback
    @WithMockUser(roles = "USER")
    public void addsCommentAndRetrievesToCompare() throws Exception {
        CompletableFuture<Comment> commentCompletableFuture = commentService.addComment(comment);
        Comment comment1 = commentCompletableFuture.get();
        Assert.assertEquals(comment, comment1);
    }

    @Test(expected = IllegalArgumentException.class)
    @WithMockUser(roles = "USER")
    public void addsNullAsCommentAndFails() throws Exception {
        commentService.addComment(null);
    }


    @Test(expected = IllegalArgumentException.class)
    @WithMockUser(roles = "USER")
    public void getsCommentByNegativeMovieIdAndFails() throws Exception {
        commentService.getCommentsByMovieId(-1);
    }

    @Test
    @Rollback
    @WithMockUser(roles = "USER")
    public void getsCommentByMovieId() throws Exception {
        commentService.addComment(comment);
        CompletableFuture<Collection<Comment>> collectionCompletableFuture = commentService.getCommentsByMovieId(comment.getMovieId());
        Collection<Comment> commentCollection = collectionCompletableFuture.get();
        Assert.assertTrue(commentCollection.size() > 0);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = {"ADMIN", "FAKE"})
    public void shouldFailAuthorization() throws Exception {
        commentService.getCommentsByMovieId(1);
    }
}