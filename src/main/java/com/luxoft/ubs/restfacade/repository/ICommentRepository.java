package com.luxoft.ubs.restfacade.repository;

import com.luxoft.ubs.restfacade.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ICommentRepository extends JpaRepository<Comment, Long> {

    Collection<Comment> findCommentByMovieId(Long aLong);
}