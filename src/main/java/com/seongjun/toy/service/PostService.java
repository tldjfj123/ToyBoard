package com.seongjun.toy.service;

import com.seongjun.toy.domain.Member;
import com.seongjun.toy.domain.Post;
import com.seongjun.toy.dto.request.PostRequest;
import com.seongjun.toy.dto.response.PostResponse;
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
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    public PostResponse registerPost(PostRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new NotFoundException("해당하는 멤버가 없어욧!"));
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setMember(member);

        Post savedPost = postRepository.save(post);

        PostResponse response = new PostResponse();
        response.setPostId(savedPost.getId());
        response.setUserId(member.getUserId());
        response.setTitle(savedPost.getTitle());
        response.setContent(savedPost.getContent());
        response.setCreatedAt(savedPost.getCreatedAt());
        response.setUpdatedAt(savedPost.getUpdatedAt());

        return response;
    }

    public Optional<Post> getPost(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Long postId, PostRequest request) {
        Optional<Post> foundPost = postRepository.findById(postId);
        if (request.getMemberId() != foundPost.get().getMember().getId()) {
            throw new IllegalArgumentException("너는 권한이 없어욧!");
        }
        if (foundPost.isPresent()) {
            Post post = foundPost.get();
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());

            return postRepository.save(post);
        }

        throw new IllegalArgumentException("업데이트할 게시글을 찾을 수 없어욧!");

    }

    public boolean deletePost(Long postId) {
        Optional<Post> foundPost = postRepository.findById(postId);

        if (foundPost.isPresent()) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }

    public List<PostResponse> getPostsByMemberId(Long memberId) {
        List<Post> foundPosts = postRepository.findPostsByMemberId(memberId);
        List<PostResponse> posts = new ArrayList<>();

        for (Post foundPost : foundPosts) {
            PostResponse post = new PostResponse();

            post.setUserId(memberRepository.findById(memberId).get().getUserId());
            post.setPostId(foundPost.getId());
            post.setTitle(foundPost.getTitle());
            post.setContent(foundPost.getContent());
            post.setCreatedAt(foundPost.getCreatedAt());
            post.setUpdatedAt(foundPost.getUpdatedAt());

            posts.add(post);
        }

        return posts;
    }
}
