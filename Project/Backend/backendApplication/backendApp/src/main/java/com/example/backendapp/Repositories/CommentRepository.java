package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Collection<Comment> getCommentsByTrackId(Long id);
}

