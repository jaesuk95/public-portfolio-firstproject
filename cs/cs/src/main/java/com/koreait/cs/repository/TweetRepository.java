package com.koreait.cs.repository;

import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<TweetBoard, Long> {

    // 조회 기능
}
