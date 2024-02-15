package com.seongjun.toy.repository;

import com.seongjun.toy.domain.PostVote;
import com.seongjun.toy.dto.request.PostVoteRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
    boolean deleteByPostId(Long postId);
    Optional<PostVote> findPostVoteByPostId(Long postId);

    List<PostVote> findPostVotesByPostId(Long postId);

    boolean existsByMemberIdAndPostIdAndVoteType(Long memberId, Long postId, boolean voteType);

    Optional<PostVote> findByMemberIdAndPostIdAndVoteType(Long memberId, Long postId, boolean voteType);
}
