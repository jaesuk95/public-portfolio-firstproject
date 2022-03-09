package com.koreait.cs.repository;

import com.koreait.cs.entities.User;
import com.koreait.cs.entities.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<UserPortfolio, Long> {
    List<UserPortfolio> findByUser(User user);
}
