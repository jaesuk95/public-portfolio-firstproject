package com.koreait.cs.service;

import com.koreait.cs.entities.Reply;
import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import com.koreait.cs.repository.ReplyRepository;
import com.koreait.cs.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class ReplyService {

//    @Autowired
//    private TweetRepositoryComment tweetRepositoryComment;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Transactional
    public void addResponse(User user, Long id, Reply requestReply, MultipartFile multipartFile){
        TweetBoard tweetBoard = tweetRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("failed to write a comment : Could not find the 'id' of the parent comment");
        });

        String originalName = StringUtils.cleanPath(multipartFile.getOriginalFilename().trim());

//        reply.setReplyImage(fileName);

        if (!originalName.equals("")){
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
            String dateStr = dateFormat.format(calendar.getTime());
            String fileName = dateStr+originalName;
            requestReply.setReplyImage(fileName);
        }

        requestReply.setUser(user);
        requestReply.setTweetBoards(tweetBoard);

        replyRepository.save(requestReply);
    }



}
