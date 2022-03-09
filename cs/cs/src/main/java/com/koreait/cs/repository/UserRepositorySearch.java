package com.koreait.cs.repository;

import com.koreait.cs.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Mapper
public interface UserRepositorySearch extends JpaRepository<User, String> {

    @Query("FROM User where enabled = true")
    List<User> searchAll();

    @Query("FROM User where enabled = true AND name LIKE %?1%")            // User 에 대문자... 이거 땜에 시간 많이 날렸네 ㅅ...b ㅋㅋㅋ
    List<User> search(String keyword);

    @Query("FROM User where verification_code = ?1")
    public User findByVerificationCode(String code);

//    @Query("FROM User where verification_code = ?1")
//    User deleteUser (User user);
}
