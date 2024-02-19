package com.seongjun.toy.controller;

import com.seongjun.toy.domain.Post;
import com.seongjun.toy.dto.request.PostRequest;
import com.seongjun.toy.dto.response.PostResponse;
import com.seongjun.toy.service.PostService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> registerPost(@RequestBody PostRequest request) {
        PostResponse registeredPost = postService.registerPost(request);
        return new ResponseEntity<>(registeredPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        Optional<Post> post = postService.getPost(postId);

        if (post.isPresent()) {
            PostResponse response = new PostResponse();

            response.setUserId(post.get().getMember().getUserId());
            response.setTitle(post.get().getTitle());
            response.setPostId(postId);
            response.setContent(post.get().getContent());
            response.setCreatedAt(post.get().getCreatedAt());
            response.setUpdatedAt(post.get().getUpdatedAt());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostRequest request) {
        Post updatedPost = postService.updatePost(postId, request);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        boolean deleted = postService.deletePost(postId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{memberId}/posts")
    public ResponseEntity<List<PostResponse>> getAllPostsByMemberId(@PathVariable Long memberId) {
        List<PostResponse> posts = postService.getPostsByMemberId(memberId);

        return ResponseEntity.ok(posts);

    }




}
