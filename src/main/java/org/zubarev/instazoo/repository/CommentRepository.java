package org.zubarev.instazoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zubarev.instazoo.entity.Comment;
import org.zubarev.instazoo.entity.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPost(Post post);
    Comment findByIdAndUserId(Long commentId,Long userId);
}
