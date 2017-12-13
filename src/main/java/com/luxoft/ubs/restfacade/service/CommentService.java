package com.luxoft.ubs.restfacade.service;

import com.luxoft.ubs.restfacade.entity.Comment;
import com.luxoft.ubs.restfacade.repository.ICommentRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Service
public class CommentService {

    private final ICommentRepository commentRepository;

    public CommentService(ICommentRepository commentRepository) {
        Assert.notNull(commentRepository, "commentRepository is required");

        this.commentRepository = commentRepository;
    }

    @Async
    public CompletableFuture<Comment> addComment(Comment comment) {
        Assert.notNull(comment, "comment is required");

        return CompletableFuture.completedFuture(commentRepository.save(comment));
    }

    @Async
    public CompletableFuture<Collection<Comment>> getCommentsByMovieId(long movieId) {
        Assert.isTrue(movieId > 0, "movieId has to be greater than zero. movieId = " + movieId);

        Collection<Comment> comments = commentRepository.findCommentByMovieId(movieId);
        return CompletableFuture.completedFuture(comments);
    }
}
