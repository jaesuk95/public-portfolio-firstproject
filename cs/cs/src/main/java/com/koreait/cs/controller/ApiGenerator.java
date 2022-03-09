package com.koreait.cs.controller;

import com.koreait.cs.entities.Role;
import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import com.koreait.cs.entities.UserPortfolio;
import com.koreait.cs.mapper.PortfolioMapper;
import com.koreait.cs.mapper.TweetMapper;
import com.koreait.cs.mapper.UserProfileMapper;
import com.koreait.cs.repository.*;
import com.koreait.cs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

// @Controller annotation will return information on view information on html
// however this is not the case for restful api case, in this case you just return an XML content
// or JSON data, you don't need to worry about to views, it just responds your data

/*
    사람들 중에 하나의 개인 정보를 출력할 수 있게 해주는 것이 REST API 작업니다
    예를 들어 localhost:8080/users/userDetail
 */

@RequestMapping("/api")
@RestController
public class ApiGenerator {

    private UserProfileMapper mapper;
    private TweetMapper tweetMapper;
    private Map<String, User> userMap;
    private PortfolioMapper portfolioMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepositoryUpdate userRepositoryUpdate;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private PortfolioRepositoryFindAll portfolioRepositoryFindAll;
    @Autowired
    private UserRepositoryProfileSecure repositoryProfileSecure;
    @Autowired
    private TweetRepositorySearch tweetRepositorySearch;

    public ApiGenerator(UserProfileMapper mapper, TweetMapper tweetMapper, PortfolioMapper portfolioMapper) {
        this.mapper = mapper;
        this.tweetMapper = tweetMapper;
        this.portfolioMapper = portfolioMapper;
    }

    @GetMapping(value = "/{email}")
    public User getUserProfile(@PathVariable("email") String email){
        return mapper.getUserProfile(email);
    }

    // 권한 확인
    @GetMapping(value = "/role/{email}")
    public String checkRole(@PathVariable("email") String email){
        return mapper.getUserRole(email);
    }

    @GetMapping(value = "/role/all")
    public List<Map<String, String>> everyUserRole(Principal principal){
        String principalEmail = principal.getName();
        String result = mapper.getUserRole(principalEmail);
        System.out.println("------------------------------ " + result + " ---------------------------");
        if (result.equals("ADMIN")){
            return mapper.getEveryUserRole();
        } else {
            return null;
        }
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    preAuthorize 가 안되는 이유 : UserDetailService 를 이용했기 때문에 조금 복잡해 졌다. 애시당초 사용자 권한을 설정할때,
//    GrantAuthorities 로 ROLE_ADMIN, ROLE_USER 설정하면 preAuthorize 가 먹힌다. 
//    만약 userDetailService 를 이용하지 않았다면, auth.jdbcAuthentication.dataSource(dataSource)... .prefix("ROLE_") 
//    으로 해결이 가능하다
//    GET ALL USERS UNDER ADMIN ACC
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public List<User> getAllUsers(Principal principal){
        String principalEmail = principal.getName();
        String result = mapper.getUserRole(principalEmail);
        System.out.println("------------------------------ " + result + " ---------------------------");
        if (result.equals("ADMIN")){
            return mapper.getUserInfo();
        } else {
            return null;
        }
    }

    @PostMapping("/register")
    public void putUserProfile(@RequestBody User userRequest)
            throws IOException {
        userService.createUserApi(userRequest);
    }

    // 사용자 권한 설정
    @PostMapping("/authorise-admin/{email}")
    public void adminRegister(@PathVariable(value = "email") String email){
        User user = userRepositoryUpdate.findByEmail(email);
        Role userRole = new Role("ADMIN");
//        Role userRole = new Role(AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepositoryUpdate.save(user);
    }

    @PostMapping("/{email}")
    public void postUserProfile(@PathVariable("email") String email,
//                               @RequestParam(value = "email", required = false) String email,
                                @RequestParam(value = "verification", required = false) String verificationCode,
                                @RequestParam(value = "enabled", required = false) boolean enabled,
                                Principal principal){

        String principalEmail = principal.getName();
        String result = mapper.getUserRole(principalEmail);
        System.out.println("------------------------------ " + result + " ---------------------------");
        if (result.equals("ADMIN")){
            User updateUser = userRepositoryUpdate.findByEmail(email);                // find the user's email
            updateUser.setEnabled(enabled);
            updateUser.setVerificationCode(verificationCode);
            Role userRole = new Role("USER");
            List<Role> roles = new ArrayList<>();
            roles.add(userRole);
            updateUser.setRoles(roles);
            userRepositoryUpdate.save(updateUser);
            System.out.println("user's accessibility is set");
        } else {
            System.out.println("you are not authorised to permit user's accessibility");
            return;
        }
    }

    @GetMapping(path = "/central")
    public List<TweetBoard> allInfo(){
        return tweetMapper.getTweetInfo();
    }

    @PostMapping("/central")
    public void postTweet(@RequestParam(value = "boardImage", required = false) String boardImage,
                          @RequestParam(value = "tweet", required = true) String tweet,
                          Principal principal){
        String principalDetail = principal.getName();
        User principalUser = userService.findOneByEmail(principalDetail);
        Date date = new Date();
        TweetBoard tweetBoard = new TweetBoard(date, tweet, principalUser, boardImage);
        tweetRepository.save(tweetBoard);
    }
//                                                                                                             PORTFOLIO
    // admin access
    @GetMapping(path = "/admin/portfolio")
    public List<UserPortfolio> portfolioManagement(Principal principal){
        String principalEmail = principal.getName();
        String result = mapper.getUserRole(principalEmail);
        System.out.println("------------------------------ " + result + " ---------------------------");
        if (result.equals("ADMIN")){
            return portfolioMapper.getPortfolio();
        } else {
            return null;
        }
    }

//    @GetMapping(path = "/portfolio")
    @GetMapping(value = "/portfolio")
    public Collection<UserPortfolio> myPortfolio(Principal principal){
        String principalEmail = principal.getName();
        User principalUser = userService.findOneByEmail(principalEmail);
        return portfolioMapper.getUserPortfolio(principalEmail);
    }


    @PostMapping(path = "/portfolio")
    public void addPortfolioManagement(@RequestParam(value = "ave_price", required = false) Long avePrice,
                                       @RequestParam(value = "shares", required = false) Long shares,
                                       @RequestParam(value = "symbol", required = false) String symbol,
                                       Principal principal){
        String principalDetail = principal.getName();
        User principalUser = userService.findOneByEmail(principalDetail);
        UserPortfolio userPortfolio = new UserPortfolio(symbol, shares, avePrice, principalUser);
        portfolioRepository.save(userPortfolio);
    }

    // admin only
    @PostMapping(value = "/deactivate/{email}")
    public void deactivateUser(@PathVariable String email, Principal principal){
        String principalEmail = principal.getName();
        String result = mapper.getUserRole(principalEmail);
        System.out.println("--------------- " + result + " -----------------");
        if (result.equals("ADMIN")){
            System.out.println(email);
            User deactivateUser = repositoryProfileSecure.findByEmail(email);
            deactivateUser.setEnabled(false);
            repositoryProfileSecure.save(deactivateUser);
        } else {
            return;
        }
    }

    @PostMapping(value = "/delete/{email}")
    public void deleteUser(@PathVariable String email){
        System.out.println(email);
        mapper.deleteUserFromRole(email);
        mapper.deleteUserFromPortfolio(email);
        mapper.deleteUserFromReply(email);
        mapper.deleteTweetBoard(email);
        mapper.deleteUser(email);
    }

}
