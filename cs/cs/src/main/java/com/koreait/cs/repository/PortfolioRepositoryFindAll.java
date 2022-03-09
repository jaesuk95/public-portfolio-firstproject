package com.koreait.cs.repository;

import com.koreait.cs.entities.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepositoryFindAll extends JpaRepository<UserPortfolio, Long> {
}
