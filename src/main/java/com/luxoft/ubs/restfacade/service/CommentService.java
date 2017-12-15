package com.luxoft.ubs.restfacade.service;

import com.luxoft.ubs.restfacade.entity.Comment;
import com.luxoft.ubs.restfacade.repository.ICommentRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Service
@CacheConfig(cacheNames = "comments")
public class CommentService {

    private final ICommentRepository commentRepository;

    public CommentService(ICommentRepository commentRepository) {
        Assert.notNull(commentRepository, "commentRepository is required");

        this.commentRepository = commentRepository;
    }

    @Async
    @Secured("ROLE_USER")
    public CompletableFuture<Comment> addComment(Comment comment) {
        Assert.notNull(comment, "comment is required");

        return CompletableFuture.completedFuture(saveAndCacheComment(comment));
    }

    @Secured("ROLE_USER")
    @CachePut(key = "#comment.movieId")
    public Comment saveAndCacheComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Async
    @Secured("ROLE_USER")
    public CompletableFuture<Collection<Comment>> getCommentsByMovieId(long movieId) {
        return CompletableFuture.completedFuture(getCachedComments(movieId));
    }

    @Secured("ROLE_USER")
    @Cacheable(key = "#movieId")
    public Collection<Comment> getCachedComments(long movieId) {
        Assert.isTrue(movieId > 0, "movieId has to be greater than zero. movieId = " + movieId);

        return commentRepository.findCommentByMovieId(movieId);
    }

}
