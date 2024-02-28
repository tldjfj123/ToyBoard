package com.seongjun.toy.repository;

import com.seongjun.toy.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
