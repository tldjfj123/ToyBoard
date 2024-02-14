package com.seongjun.toy.controller;

import com.seongjun.toy.domain.Comment;
import com.seongjun.toy.dto.request.CommentRequest;
import com.seongjun.toy.dto.response.CommentResponse;
import com.seongjun.toy.service.CommentService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> registerComment(@RequestBody CommentRequest request) {
        CommentResponse registeredResponse = commentService.registerComment(request);
        return new ResponseEntity<>(registeredResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
        Optional<Comment> comment = commentService.getComment(commentId);

        if (comment.isPresent()) {
            CommentResponse response = new CommentResponse();

            response.setUserId(comment.get().getMember().getUserId());
            response.setPostId(comment.get().getPost().getId());
            response.setCommentId(comment.get().getId());
            response.setContent(comment.get().getContent());
            response.setCreatedAt(comment.get().getCreatedAt());
            response.setUpdatedAt(comment.get().getUpdatedAt());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        boolean deleted = commentService.deleteComment(commentId);

        if (deleted) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/member/{memberId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByMemberId(@PathVariable Long memberId) {
        List<CommentResponse> comments = commentService.getCommentsByMemberId(memberId);

        return ResponseEntity.ok(comments);
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(comments);

    }


}
