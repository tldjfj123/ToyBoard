package com.seongjun.toy.dto.request;

import lombok.Getter;

@Getter
public class CommentVoteRequest {
    private Long memberId;
    private Long postId;
    private Long commentId;
    private boolean voteType;
}
