package com.seongjun.toy.controller;

import com.seongjun.toy.domain.PostVote;
import com.seongjun.toy.dto.request.PostVoteRequest;
import com.seongjun.toy.service.PostVoteService;
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
@RequestMapping("/api/post-votes")
@RequiredArgsConstructor
public class PostVoteController {
    private final PostVoteService postVoteService;

    @PostMapping
    public ResponseEntity<PostVote> registerPostVote(@RequestBody PostVoteRequest request) {
        PostVote registeredPostVote = postVoteService.registerPostVote(request);

        return new ResponseEntity<>(registeredPostVote, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePostVote(@RequestBody PostVoteRequest request) {
        boolean deleted = postVoteService.deletePostVote(request);

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{postId}/good")
    public ResponseEntity<Integer> getGoodPostVoteCount(@PathVariable Long postId) {
        int responses = postVoteService.getGoodPostVoteCount(postId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{postId}/bad")
    public ResponseEntity<Integer> getBadPostVoteCount(@PathVariable Long postId) {
        int responses = postVoteService.getBadPostVoteCount(postId);

        return ResponseEntity.ok(responses);
    }
}
