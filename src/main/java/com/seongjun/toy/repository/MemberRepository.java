package com.seongjun.toy.repository;

import com.seongjun.toy.domain.Member;
import com.seongjun.toy.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
