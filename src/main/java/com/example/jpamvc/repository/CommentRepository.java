package com.example.jpamvc.repository;

import com.example.jpamvc.domain.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findById(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    public Long delete(Long commentId) {
        Comment comment = findById(commentId);
        em.remove(comment);
        return commentId;
    }
}
