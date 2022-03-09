package com.koreait.cs.repository;

import com.koreait.cs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepositoryProfile extends JpaRepository<User, String> {
    List<User> findByEmail(String email);
}
