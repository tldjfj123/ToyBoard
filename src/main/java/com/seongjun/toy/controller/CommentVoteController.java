package com.seongjun.toy.controller;

import com.seongjun.toy.domain.CommentVote;
import com.seongjun.toy.dto.request.CommentVoteRequest;
import com.seongjun.toy.service.CommentVoteService;
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
@RequestMapping("api/comment-votes")
@RequiredArgsConstructor
public class CommentVoteController {
    private final CommentVoteService commentVoteService;

    @PostMapping
    public ResponseEntity<CommentVote> registerCommentVote(@RequestBody CommentVoteRequest request) {
        CommentVote registeredCommentVote = commentVoteService.registerCommentVote(request);

        return new ResponseEntity<>(registeredCommentVote, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCommentVote(@RequestBody CommentVoteRequest request) {
        boolean deleted = commentVoteService.deleteCommentVote(request);

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{commentId}/good")
    public ResponseEntity<Integer> getGoodCommentVoteCount(@PathVariable Long commentId) {
        int responses = commentVoteService.getGoodCommentVoteCount(commentId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{commentId}/bad")
    public ResponseEntity<Integer> getBadCommentVoteCount(@PathVariable Long commentId) {
        int responses = commentVoteService.getBadCommentVoteCount(commentId);

        return ResponseEntity.ok(responses);
    }
}
