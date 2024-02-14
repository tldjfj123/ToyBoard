package com.seongjun.toy.dto.request;

import lombok.Getter;

@Getter
public class PostVoteRequest {
    private Long memberId;
    private Long postId;
    private boolean voteType;
}
