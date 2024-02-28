package com.seongjun.toy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoRequest {
    private Long postId;
    private String originalPhotoName;
    private String savedPhotoName;
    private String savedPath;
}
