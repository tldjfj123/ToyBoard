package com.seongjun.toy.repository;

import com.seongjun.toy.domain.CommentVote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    Optional<CommentVote> findByMemberIdAndPostIdAndCommentIdAndVoteType(Long memberId, Long postId, Long commentId,
                                                                         boolean voteType);

    boolean existsByMemberIdAndPostIdAndCommentIdAndVoteType(Long memberId, Long postId, Long commentId,
                                                             boolean voteType);

    List<CommentVote> findCommentVotesByCommentId(Long commentId);
}
