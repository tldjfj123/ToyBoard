package com.seongjun.toy.service;

import com.seongjun.toy.domain.Member;
import com.seongjun.toy.domain.Post;
import com.seongjun.toy.domain.PostVote;
import com.seongjun.toy.dto.request.PostVoteRequest;
import com.seongjun.toy.repository.MemberRepository;
import com.seongjun.toy.repository.PostRepository;
import com.seongjun.toy.repository.PostVoteRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostVoteService {
    private final PostVoteRepository postVoteRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public PostVote registerPostVote(PostVoteRequest request) {
        if (postVoteRepository.existsByMemberIdAndPostIdAndVoteType(request.getMemberId(), request.getPostId(),
                request.isVoteType())) {
            if (request.isVoteType()) {
                throw new IllegalArgumentException("이미 추천한 게시글입니다!");
            } else {
                throw new IllegalArgumentException("이미 비추천한 게시글입니다!");
            }
        }

        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new NotFoundException("해당하는 멤버가 없어욧!!"));
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new NotFoundException("해당하는 게시글이 없어욧!!"));
        PostVote postVote = new PostVote();

        postVote.setVoteType(request.isVoteType());
        postVote.setMember(member);
        postVote.setPost(post);

        return postVoteRepository.save(postVote);
    }

    public boolean deletePostVote(PostVoteRequest request) {
        Optional<PostVote> existingVote = postVoteRepository.findByMemberIdAndPostIdAndVoteType(request.getMemberId(), request.getPostId(), request.isVoteType());

        if (existingVote.isPresent()) {
            postVoteRepository.delete(existingVote.get());
            return true;
        } else {
            throw new NotFoundException("해당하는 추천이 없습니다.");
        }
    }


    public int getGoodPostVoteCount(Long postId) {
        List<PostVote> votes = postVoteRepository.findPostVotesByPostId(postId);
        int goodResponse = 0;
        for (PostVote vote : votes) {
            if (vote.isVoteType()) {
                goodResponse++;
            }
        }

        return goodResponse;
    }

    public int getBadPostVoteCount(Long postId) {
        List<PostVote> votes = postVoteRepository.findPostVotesByPostId(postId);
        int badResponse = 0;

        for (PostVote vote : votes) {
            if (!vote.isVoteType()) {
                badResponse++;
            }
        }

        return badResponse;
    }
}
