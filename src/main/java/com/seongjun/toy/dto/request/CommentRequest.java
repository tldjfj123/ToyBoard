package com.seongjun.toy.dto.request;

import lombok.Getter;

@Getter
public class CommentRequest {
    private Long memberId;
    private Long postId;
    private String content;
}
