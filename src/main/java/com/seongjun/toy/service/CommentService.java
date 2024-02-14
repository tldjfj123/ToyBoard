package com.seongjun.toy.service;

import com.seongjun.toy.domain.Comment;
import com.seongjun.toy.domain.Member;
import com.seongjun.toy.domain.Post;
import com.seongjun.toy.dto.request.CommentRequest;
import com.seongjun.toy.dto.response.CommentResponse;
import com.seongjun.toy.repository.CommentRepository;
import com.seongjun.toy.repository.MemberRepository;
import com.seongjun.toy.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentResponse registerComment(CommentRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new NotFoundException("해당하는 멤버가 없어욧!"));
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new NotFoundException("해당하는 게시글이 없어욧!"));

        Comment comment = new Comment();
        comment.setMember(member);
        comment.setPost(post);
        comment.setContent(request.getContent());

        Comment savedComment = commentRepository.save(comment);

        CommentResponse response = new CommentResponse();
        response.setUserId(savedComment.getContent());
        response.setCommentId(savedComment.getId());
        response.setPostId(post.getId());
        response.setUpdatedAt(savedComment.getUpdatedAt());
        response.setCreatedAt(savedComment.getCreatedAt());
        response.setContent(savedComment.getContent());

        return response;
    }

    public Optional<Comment> getComment(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public boolean deleteComment(Long commentId) {
        Optional<Comment> foundComment = commentRepository.findById(commentId);

        if (foundComment.isPresent()) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }

    public List<CommentResponse> getCommentsByMemberId(Long memberId) {
        List<Comment> foundComments = commentRepository.findCommentsByMemberId(memberId);
        List<CommentResponse> comments = new ArrayList<>();

        for (Comment foundComment : foundComments) {
            CommentResponse comment = new CommentResponse();

            comment.setUserId(memberRepository.findById(memberId).get().getUserId());
            comment.setPostId(foundComment.getPost().getId());
            comment.setCommentId(foundComment.getId());
            comment.setUpdatedAt(foundComment.getUpdatedAt());
            comment.setCreatedAt(foundComment.getCreatedAt());
            comment.setContent(foundComment.getContent());

            comments.add(comment);
        }

        return comments;
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        List<Comment> foundComments = commentRepository.findCommentsByPostId(postId);
        List<CommentResponse> comments = new ArrayList<>();

        for (Comment foundComment : foundComments) {
            CommentResponse comment = new CommentResponse();

            comment.setPostId(foundComment.getPost().getId());
            comment.setCommentId(foundComment.getId());
            comment.setUpdatedAt(foundComment.getUpdatedAt());
            comment.setCreatedAt(foundComment.getCreatedAt());
            comment.setContent(foundComment.getContent());

            comments.add(comment);
        }

        return comments;
    }

}
