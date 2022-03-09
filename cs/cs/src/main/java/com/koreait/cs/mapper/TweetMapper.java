package com.koreait.cs.mapper;

import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TweetMapper {

    @Select("select tweet from tweet_board")
    List<TweetBoard> getTweetInfo();

}
