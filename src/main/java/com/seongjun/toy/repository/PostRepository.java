package com.seongjun.toy.repository;

import com.seongjun.toy.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByMemberId(Long memberId);
}
