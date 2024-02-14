package com.seongjun.toy.repository;

import com.seongjun.toy.domain.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
}
