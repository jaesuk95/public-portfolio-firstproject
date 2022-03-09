package com.koreait.cs.mapper;

import com.koreait.cs.entities.Role;
import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserProfileMapper {
    @Select("select * from user where email=#{email}")
    User getUserProfile(@Param("email") String email);

    @Select("select * from user")
    List<User> getUserInfo();

    @Select("select role_name from user_roles where user_email=#{email}")
    String getUserRole(@Param("email") String email);

    @Select("select * from user_roles")
//    ArrayList<String> getEveryUserRole();
    List<Map<String, String>> getEveryUserRole();



    @Delete("delete from user_roles where user_email=#{email}")
    void deleteUserFromRole(@Param("email") String email);

    @Delete("delete from user_portfolio where user_email=#{email}")
    void deleteUserFromPortfolio(@Param("email") String email);

//           DELETE FROM user WHERE email="4@outlook.com";
    @Delete("delete from reply where user_email=#{email}")
    void deleteUserFromReply(@Param("email") String email);

    @Delete("delete from tweet_board where user_email=#{email}")
    void deleteTweetBoard(@Param("email") String email);

    @Delete("delete from user where email=#{email}")
    void deleteUser(@Param("email") String email);

    //    UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
    //    DELETE FROM user_roles WHERE user_email="4@outlook.com";
}

