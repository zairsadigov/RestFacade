package com.luxoft.ubs.restfacade.service;

import com.luxoft.ubs.restfacade.repository.ICommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CommentService {

    private final ICommentRepository commentRepository;

    public CommentService(ICommentRepository commentRepository) {
        Assert.notNull(commentRepository, "commentRepository is required");

        this.commentRepository = commentRepository;
    }
}
