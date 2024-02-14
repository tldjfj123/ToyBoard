package com.seongjun.toy.repository;

import com.seongjun.toy.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByMemberId(Long memberId);
    List<Comment> findCommentsByPostId(Long postId);
}
