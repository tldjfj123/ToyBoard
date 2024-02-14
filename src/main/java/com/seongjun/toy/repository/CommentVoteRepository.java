package com.seongjun.toy.repository;

import com.seongjun.toy.domain.CommentVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
}
