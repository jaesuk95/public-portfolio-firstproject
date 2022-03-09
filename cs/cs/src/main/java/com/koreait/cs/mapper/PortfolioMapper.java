package com.koreait.cs.mapper;

import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.UserPortfolio;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PortfolioMapper {

    @Select("select * from user_portfolio")
    List<UserPortfolio> getPortfolio();

    @Select("select * from user_portfolio where user_email=#{email}")
//    @Select("select id, avePrice, shares, symbol from user_portfolio where user_email=#{email}")
    List<UserPortfolio> getUserPortfolio(@Param("email") String email);

//    @Select("select role_name from user_roles where user_email=#{email}")
//    String getUserRole(@Param("email") String email);
//    @Param("email") String email
}
