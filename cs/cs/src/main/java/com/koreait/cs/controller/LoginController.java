package com.koreait.cs.controller;

import com.koreait.cs.entities.User;
import com.koreait.cs.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("user", new User());
        User user = new User();
        return "views/login";
    }

//    utility
    private String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                               HttpServletRequest request) throws IOException, UnsupportedEncodingException, MessagingException {

        if (bindingResult.hasErrors()){
//            errors.rejectValue("failedMessage", "The account already EXISTS");
            return "views/login";
        }
        if (userService.isUserPresent(user.getEmail())){                // if user does not exist = true
//            model.addAttribute("exist", true);
            model.addAttribute("failedMessage", "The account already EXISTS");
            return "views/login";
        }
        userService.createUser(user);

        String siteURL = getSiteURL(request);
        userService.sendVerificationEmail(user, siteURL);
        redirectAttributes.addFlashAttribute("message",
                "Your account has been successfully created!");
        return "views/login/register/success";
    }


    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model model){

        if (userService.verify(code)){
            return "views/login/register/verify_success";
        } else {
            return "views/login/register/verify_fail";
        }
    }

}

