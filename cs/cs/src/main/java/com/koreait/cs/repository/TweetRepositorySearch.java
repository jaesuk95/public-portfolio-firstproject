package com.koreait.cs.repository;

import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepositorySearch extends JpaRepository<TweetBoard, Long> {
    List<TweetBoard> findByUser(User user);
//    List<TweetBoard> deleteByUserEmail(String email);
}
