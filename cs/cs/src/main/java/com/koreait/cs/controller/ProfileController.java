package com.koreait.cs.controller;

import com.koreait.cs.entities.Reply;
import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import com.koreait.cs.repository.*;
import com.koreait.cs.service.TweetSearchService;
import com.koreait.cs.service.UserService;
import com.koreait.cs.service.UserServiceSearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepositorySearch tweetRepositorySearch;
    @Autowired
    private TweetSearchService tweetSearchService;
    @Autowired
    private UserRepositoryProfile userRepositoryProfile;
    @Autowired
    private UserRepositoryUpdate userRepositoryUpdate;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private UserServiceSearch userServiceSearch;


    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal, Authentication authentication) {
        String email = principal.getName();
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userService.findOneByEmail(email);
        List<TweetBoard> boards = tweetRepositorySearch.findByUser(user);
        Collections.reverse(boards);
        String diffuse = user.getEmail();
        model.addAttribute("profile", userRepositoryProfile.findByEmail(diffuse));
        model.addAttribute("tweets", boards);

        List<User> userSearch = userRepository.findAll();
        model.addAttribute("userSearch", new User());
        return "views/profile/profile";
    }

//    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String listOfUsers(@RequestParam("keyword") String keyword, Model model){
        System.out.println(" -------------------------------------- " + keyword + " ----------------------------------------- ");

        List<User> users = userServiceSearch.findAllUsers(keyword);
        model.addAttribute("users", users);
        return "views/list";
    }

    @GetMapping("/profile/{email}")
    public String showUserProfile(@PathVariable("email") String email, Model model, Principal principal) {
        System.out.println("888888888888888888888888888 " + email + " YOU'RE CURRENTLY VIEWING SOMEONE ELSE'S PROFILE PAGE ");
        model.addAttribute("otherProfile", userRepositoryProfile.findByEmail(email));

        User user = userService.findOneByEmail(email);          // 입력 받은 이메일

        String principalDetail = principal.getName();                       // get principal's detail (current user detail)
        User principalUser = userService.findOneByEmail(principalDetail);   //  get principal's detail (current user detail)

        if (principalUser == user) {     // 만약, 이메일 주소가 같이 않다면,  jaesuk@gmail.com, martin@gmail.com 그러면 else 로 전달
            List<TweetBoard> userAuthority = tweetRepositorySearch.findByUser(principalUser);
            Collections.reverse(userAuthority);
            model.addAttribute("userAuthority", userAuthority);         // 입력 받은 이메일과, 나의 주소가 동일 하다면
        } else {
            List<TweetBoard> boards = tweetRepositorySearch.findByUser(user);
            Collections.reverse(boards);
            model.addAttribute("otherTweets", boards);                  // 동일 하지 않는다면
        }

        List<User> userSearch = userRepository.findAll();
        model.addAttribute("userSearch", new User());
        return "views/profile/otherProfile";
    }

    // 회원정보 수정
    @RequestMapping("/profile")
    public String editProfile(@RequestParam(value = "image", required = false)MultipartFile multipartFile,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "bio", required = false) String bio,
                              @RequestParam(value = "twitter", required = false) String twitter,
                              @RequestParam(value = "twitterTag", required = false) String tw_tag,
                              @RequestParam(value = "instagram", required = false) String instagram,
                              @RequestParam(value = "instagramTag", required = false) String ig_tag,
                              @RequestParam(value = "telegramTag", required = false) String tg_tag,
                              User user, Principal principal) throws IOException {

        String principalDetail = principal.getName();
        User principalUser = userService.findOneByEmail(principalDetail);
        String principalEmail = principalUser.getEmail();
        String principalProfileImg = principalUser.getProfilePicture();

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        System.out.println(" -------------------------------------------" + fileName + " ----------------------------- file name ----------");

//        String uploadDirectory = "./cs/profile-image/" + principalEmail;     // image save under relative directory
        String uploadDirectory = "./profile-image/" + principalEmail;     // image save under relative directory
        System.out.println(" -------------------------------------------" + uploadDirectory + " ----------------------------- uploadDirectory ----------");

        Path uploadPath = Paths.get(uploadDirectory);
        System.out.println(" -------------------------------------------" + uploadPath + " ----------------------------- uploadPath ----------");
        if (!Files.exists(uploadPath)){                 // if directory does not exist
            Files.createDirectories(uploadPath);        // create a new directory, if only does not exist
        } try (InputStream inputStream = multipartFile.getInputStream()){       //
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(" -------------------------------------------" + filePath + " ----------------------------- filePath ----------");
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
//            throw new IOException("could not save uploaded file: " + fileName);
        }

        User updateUser = userRepositoryUpdate.findByEmail(email);                // find the user's email
        if (fileName == null || fileName.isEmpty()){
            updateUser.setProfilePicture(principalProfileImg);
        } else {
            updateUser.setProfilePicture(fileName);
        }

        if (email.equals(principalEmail)){
            updateUser.setName(name);
            updateUser.setBio(bio);
            updateUser.setTwitter(twitter);
            updateUser.setTw_tag(tw_tag);
            updateUser.setInstagram(instagram);
            updateUser.setIg_tag(ig_tag);
            updateUser.setTg_tag(tg_tag);
            userRepositoryUpdate.save(updateUser);
        }
        return "redirect:/profile";
    }

    // main content from comment
    @RequestMapping("/profile/{email}/delete/{id}")
    public String userUpdateProfile(@PathVariable("email") String email,
                                    @PathVariable("id") Long id, Principal principal) {

        String userEmail = principal.getName();                                                 // to prevent hackers
        User user = userService.findOneByEmail(userEmail);
        String principalEmail = user.getEmail();

        TweetBoard boardNumberIdentity = tweetRepository.findById(id).orElse(null);        // you have to put orElse input in case you get lost
        User boardUserDetail = boardNumberIdentity.getUser();
        String boardUserEmail = boardUserDetail.getEmail();

        if (principalEmail == boardUserEmail) {                                                 // checking for hackers with user authority(Spring security provided)
            tweetRepository.deleteById(id);
        } else {
            return "views/forHackers";
        }
        return "redirect:/profile/{email}";
    }


    // reply section from comment
    @RequestMapping("/profile/{email}/delete/reply/{id}")
    public String deleteReplySection(@PathVariable("email") String email,                   // email = tweetBoard author
                                    @PathVariable("id") Long id, Principal principal) {     // id = reply id

        String userEmail = principal.getName();                                 // for hackers
        User user = userService.findOneByEmail(userEmail);
        String principalEmail = user.getEmail();

        Reply replyNumberIdentity = replyRepository.findById(id).orElse(null);
        TweetBoard tweetBoardDetail = replyNumberIdentity.getTweetBoards();
        User userDetail = tweetBoardDetail.getUser();
        String userEmail2 = userDetail.getEmail();

        System.out.println(" --------------------------- " + userEmail2 + " --------------------------------- reply -> tweetBoard -> User(email) ");    // tweetBoard author
        System.out.println(" --------------------------- " + principalEmail + " --------------------------------- ");

        User userDetails = replyNumberIdentity.getUser();
        String replyAuthor = userDetails.getEmail();

        System.out.println(" --------------------------- " + replyAuthor + " --------------------------------- reply -> User(email) "); // reply board author

//                다른 페이지에서 내 댓글을 삭제 하고 싶을때
//        http://localhost:8080/profile/jaesuk95@outlook.com/delete/reply/30
//        http://localhost:8080/profile/kenneth@gmail.com/delete/reply/21

        if (userEmail2 == principalEmail){      // tests if the user is the TweetBoard author
            replyRepository.deleteById(id);
        } else if (replyAuthor == principalEmail){  // tests if the reply message belongs to the right author, hence have permission to delete
            replyRepository.deleteById(id);
        } else {
        return "views/forHackers"; }

        Long boardId = tweetBoardDetail.getId();
        return "redirect:/profile/" + email + "/comment/" + boardId;
    }


    @RequestMapping("/profile/delete/{id}")
    public String userUpdateProfileAtProfile(@PathVariable("id") Long id, Principal principal) {     // this 'id' received from page is the board id
        String userEmail = principal.getName();                                 // for hackers
        User user = userService.findOneByEmail(userEmail);
        String principalEmail = user.getEmail();

        TweetBoard boardNumberIdentity = tweetRepository.findById(id).orElse(null);         // you have to put orElse input in case you get lost
        Long boardNumberId = boardNumberIdentity.getId();    // board id created by @pathVariable
        User boardUserDetail = boardNumberIdentity.getUser();
        String boardUserEmail = boardUserDetail.getEmail();

//        Reply response = replyRepositoryBoardID.findByTweetBoards(boardNumberIdentity);
//        Long responseId = response.getId();

        if (principalEmail == boardUserEmail) {
//            replyRepositoryBoardID.deleteByTweetBoards(boardNumberIdentity);
            tweetRepository.deleteById(id);
        } else {return "views/forHackers";}
        return "redirect:/profile";
    }



    // 옛날 방식

//    underneath methods are old trial versions
    @GetMapping("/profile/edit/{email}")
    public String showEdit(@PathVariable("email") String email, Model model) {
        model.addAttribute("client", userRepositoryProfile.findByEmail(email));

        List<User> userSearch = userRepository.findAll();
        model.addAttribute("userSearch", new User());
        return "views/editprofile";
    }

    @RequestMapping("/profile/edit/{email}")
    public String updateUserProfile(@RequestParam(value = "email", required = false) String email,
                                    @RequestParam(value = "bio", required = false) String bio,
                                    @RequestParam(value = "instagram", required = false) String instagram,
                                    @RequestParam(value = "instagramTag", required = false) String ig_tag,
                                    @RequestParam(value = "twitter", required = false) String twitter,
                                    @RequestParam(value = "twitterTag", required = false) String tw_tag,
                                    @RequestParam(value = "telegramTag", required = false) String tg_tag) {

        User updateUser = userRepositoryUpdate.findByEmail(email);                // find the user's email
        updateUser.setBio(bio);
        updateUser.setInstagram(instagram);
        updateUser.setIg_tag(ig_tag);
        updateUser.setTwitter(twitter);
        updateUser.setTw_tag(tw_tag);
        updateUser.setTg_tag(tg_tag);
        userRepositoryUpdate.save(updateUser);

        return "redirect:/profile";
    }

}
