package com.seongjun.toy.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {
    private String userId;
    private String password;
    private String mail;
    private String address;
}
