package com.seongjun.toy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostVoteRequest {
    private Long memberId;
    private Long postId;
    private boolean voteType;
}
