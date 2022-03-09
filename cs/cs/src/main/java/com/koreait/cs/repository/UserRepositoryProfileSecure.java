package com.koreait.cs.repository;

import com.koreait.cs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositoryProfileSecure extends JpaRepository<User, String> {
    User findByEmail(String email);
}
