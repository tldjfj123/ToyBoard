package com.seongjun.toy.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private String userId;
    private Long postId;
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;

}
