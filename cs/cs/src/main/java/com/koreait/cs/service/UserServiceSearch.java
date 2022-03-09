package com.koreait.cs.service;

import com.koreait.cs.entities.Role;
import com.koreait.cs.entities.User;
import com.koreait.cs.repository.UserRepository;
import com.koreait.cs.repository.UserRepositorySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceSearch{


    @Autowired
    private UserRepositorySearch userRepositorySearch;

    @Autowired
    private UserRepository userRepository;


    public List<User> findAllUsers(String keyword){
//        if (keyword != null){                                   // 만일 keyword 가 null 이 아니라면,
        if (!keyword.equals("")){                                   // 만일 keyword 가 null 이 아니라면,
            return userRepositorySearch.search(keyword);        // userRepositorySearch 에 가서 검색한다
        }
        return userRepositorySearch.searchAll();
    }
}





















