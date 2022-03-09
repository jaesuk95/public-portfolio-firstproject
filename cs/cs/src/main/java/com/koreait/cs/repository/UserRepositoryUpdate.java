package com.koreait.cs.repository;

import com.koreait.cs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryUpdate extends JpaRepository<User, String> {
    User findByEmail(String email);
}


