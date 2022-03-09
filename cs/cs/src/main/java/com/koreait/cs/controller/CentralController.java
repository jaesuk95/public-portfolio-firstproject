package com.koreait.cs.controller;

import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import com.koreait.cs.repository.TweetRepository;
import com.koreait.cs.repository.UserRepository;
import com.koreait.cs.repository.UserRepositoryProfile;
import com.koreait.cs.service.TweetSearchService;
import com.koreait.cs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Controller
public class CentralController {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepositoryProfile userRepositoryProfile;

    @Autowired
    private TweetSearchService tweetSearchService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/central")
    public String showIndexPage(Model model, Principal principal) {
        String email = principal.getName();                         // added late
        User user = userService.findOneByEmail(email);              // added late
        String diffuse = user.getEmail();                           // added late
        model.addAttribute("profile", userRepositoryProfile.findByEmail(diffuse));                  // user profile data
        model.addAttribute("newtweet", new TweetBoard());// board data

        List<TweetBoard> boards = tweetRepository.findAll();
        Collections.reverse(boards);
        model.addAttribute("tweets", boards);
        return "views/central/central";
    }

    @PostMapping("/central")                       // receives 'form' from tweetForm
    public String submittedForm(@Valid TweetBoard tweetBoard, BindingResult bindingResult, Principal principal,
                                @RequestParam(value = "image", required = false)MultipartFile multipartFile) throws IOException {
        if (bindingResult.hasErrors()){
            return "views/taskForm";
        }
        String user = principal.getName();      // gets the name of the user
        User principalUser = userService.findOneByEmail(user);      // gets the email by specifying the current user name
        String principalEmail = principalUser.getEmail();
        Long principalId = principalUser.getUser_id();

        Long boardId = tweetBoard.getId();

        String originalName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
        String dateStr = dateFormat.format(calendar.getTime());
        if (originalName == ""){

        } else {
            String fileName = dateStr+originalName;
//            String uploadDirectory = "./cs/board-image/" + principalEmail + "/";
            String uploadDirectory = "./board-image/" + principalEmail + "/";
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);                                    // 경로 생성
            } try (InputStream inputStream = multipartFile.getInputStream()){
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e){
//            throw new IOException("could not save uploaded file: " + fileName);
            }
        }
        tweetSearchService.addTweet(tweetBoard, userService.findOneByEmail(principalEmail), multipartFile);
        return "redirect:/central";
    }

//        Pattern	Example
//        dd-MM-yy	31-01-12
//        dd-MM-yyyy	31-01-2012
//        MM-dd-yyyy	01-31-2012
//        yyyy-MM-dd	2012-01-31
//        yyyy-MM-dd HH:mm:ss	2012-01-31 23:59:59
//        yyyy-MM-dd HH:mm:ss.SSS	2012-01-31 23:59:59.999
//        yyyy-MM-dd HH:mm:ss.SSSZ	2012-01-31 23:59:59.999+0100
//        EEEEE MMMMM yyyy HH:mm:ss.SSSZ	Saturday November 2012 10:45:42.720+0100
}