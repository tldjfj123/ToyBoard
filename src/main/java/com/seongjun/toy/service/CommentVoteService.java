package com.seongjun.toy.service;

import com.seongjun.toy.domain.Comment;
import com.seongjun.toy.domain.CommentVote;
import com.seongjun.toy.domain.Member;
import com.seongjun.toy.domain.Post;
import com.seongjun.toy.dto.request.CommentVoteRequest;
import com.seongjun.toy.repository.CommentRepository;
import com.seongjun.toy.repository.CommentVoteRepository;
import com.seongjun.toy.repository.MemberRepository;
import com.seongjun.toy.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentVoteService {
    private final CommentVoteRepository commentVoteRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentVote registerCommentVote(CommentVoteRequest request) {
        if (commentVoteRepository.existsByMemberIdAndPostIdAndCommentIdAndVoteType(request.getMemberId(), request.getPostId(), request.getCommentId(), request.isVoteType())) {
            if (request.isVoteType()) {
                throw new IllegalArgumentException("이미 추천한 댓글입니다!");
            } else {
                throw new IllegalArgumentException("이미 비추천한 댓글입니다!");
            }
        }

        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new NotFoundException("해당하는 멤버가 없어욧!!"));
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new NotFoundException("해당하는 게시글이 없어욧!!"));
        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow(() -> new NotFoundException("해당하는 댓글이 없어욧!!"));

        CommentVote commentVote = new CommentVote();

        commentVote.setVoteType(request.isVoteType());
        commentVote.setComment(comment);
        commentVote.setPost(post);
        commentVote.setMember(member);

        return commentVoteRepository.save(commentVote);
    }

    public boolean deleteCommentVote(CommentVoteRequest request) {
        Optional<CommentVote> existingVote =  commentVoteRepository.findByMemberIdAndPostIdAndCommentIdAndVoteType(request.getMemberId(), request.getPostId(), request.getCommentId(), request.isVoteType());
            if (existingVote.isPresent()) {
                commentVoteRepository.delete(existingVote.get());
                return true;
            } else {
                throw new NotFoundException("해당하는 추천이 없어욧!!");
            }
    }

    public int getGoodCommentVoteCount(Long commentId) {
        List<CommentVote> votes = commentVoteRepository.findCommentVotesByCommentId(commentId);
        int goodResponses = 0;
        for (CommentVote vote : votes) {
            if (vote.isVoteType()) {
                goodResponses++;
            }
        }

        return goodResponses;
    }

    public int getBadCommentVoteCount(Long commentId) {
        List<CommentVote> votes = commentVoteRepository.findCommentVotesByCommentId(commentId);
        int badResponses = 0;
        for (CommentVote vote : votes) {
            if (!vote.isVoteType()) {
                badResponses++;
            }
        }

        return badResponses;
    }

}
