package com.koreait.cs.service;

import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import com.koreait.cs.repository.TweetRepository;
import com.koreait.cs.repository.TweetRepositorySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class TweetSearchService {

    @Autowired
    private TweetRepositorySearch tweetRepositorySearch;

    @Autowired
    private TweetRepository tweetRepository;



    public List<TweetBoard> findUserTask(User user){
        return tweetRepositorySearch.findByUser(user);
    }

    public void addTweet(TweetBoard tweetBoard, User user, MultipartFile multipartFile){

        String principalEmail = user.getEmail();
        String originalName = StringUtils.cleanPath(multipartFile.getOriginalFilename().trim());

        System.out.println(" -----------------------------------------" + originalName + "---------------------------------- original name -");

        if (!originalName.equals("")){
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
            String dateStr = dateFormat.format(calendar.getTime());
            String fileName = dateStr+originalName;
            tweetBoard.setBoardImage(fileName);
        }

//        String fileName = dateStr+originalName;
//        tweetBoard.setBoardImage(fileName);
        tweetBoard.setUser(user);
        tweetRepositorySearch.save(tweetBoard);
    }

    @Transactional
    public TweetBoard viewComments(Long id){
        return tweetRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다");
                });
    }

}
