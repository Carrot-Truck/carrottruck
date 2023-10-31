package com.boyworld.carrot.domain.review.repository;

import com.boyworld.carrot.domain.review.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
