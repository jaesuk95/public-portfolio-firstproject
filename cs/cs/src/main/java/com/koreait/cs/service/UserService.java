package com.koreait.cs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.koreait.cs.entities.Role;
import com.koreait.cs.entities.User;
import com.koreait.cs.repository.UserRepository;
import com.koreait.cs.repository.UserRepositoryProfile;
import com.koreait.cs.repository.UserRepositorySearch;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepositorySearch userRepositorySearch;

    @Autowired
    private UserRepositoryProfile userRepositoryProfile;


    public void createUserApi(User user) throws IOException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        Role userRole = new Role("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);

        String newEmail = user.getEmail();
        Path targetDir = Paths.get("./profile-image/" + newEmail);
        System.out.println(" ----------------------------------- " + targetDir + " ------------------------- target Dir -------------");
        Path finalPath = Files.createDirectories(targetDir);            // create a new directory
        System.out.println(" ----------------------------------- " + finalPath + " ------------------------- target Dir -------------");

        File src = new File("C:\\IntelliJ\\cs\\cs\\src\\main\\resources\\static\\img\\mainprofilepicture.jpg");
        File cpy = new File(String.valueOf(finalPath), src.getName());

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(src));
            bos = new BufferedOutputStream(new FileOutputStream(cpy));

            byte[] b = new byte[2097152];   // 2mb (limit size)
            int readByte = 0;

            while ((readByte = bis.read(b)) != -1) {
                bos.write(b, 0, readByte);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (bos != null) bos.close();
                if (bis != null) bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        String randomCode = RandomString.make(64);
//        user.setVerificationCode(randomCode);
//        user.setEnabled(false);
//        user.setBio("");
        user.setProfilePicture("mainprofilepicture.jpg");
        userRepository.save(user);
    }

//    public void createUser(User user, String siteURL) throws IOException {
    public void createUser(User user) throws IOException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        Role userRole = new Role("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);

//        File defaultImg = new File("/img/mainprofile.jpg"); // C:\IntelliJ\cs\profile-image\c@gmail.com\img\mainprofile.jpg // 파일만 만든다

        String newEmail = user.getEmail();
        Path targetDir = Paths.get("./profile-image/" + newEmail);
        System.out.println(" ----------------------------------- " + targetDir + " ------------------------- target Dir -------------");
        Path finalPath = Files.createDirectories(targetDir);            // create a new directory
        System.out.println(" ----------------------------------- " + finalPath + " ------------------------- target Dir -------------");

        File src = new File("C:\\IntelliJ\\cs\\cs\\src\\main\\resources\\static\\img\\mainprofilepicture.jpg");
        File cpy = new File(String.valueOf(finalPath), src.getName());

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(src));
            bos = new BufferedOutputStream(new FileOutputStream(cpy));

            byte[] b = new byte[2097152];   // 2mb (limit size)
            int readByte = 0;

            while ((readByte = bis.read(b)) != -1) {
                bos.write(b, 0, readByte);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (bos != null) bos.close();
                if (bis != null) bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        user.setProfilePicture("mainprofilepicture.jpg");
        user.setBio("");
        user.setInstagram("https://www.instagram.com/");
        user.setTwitter("https://twitter.com/");
        userRepository.save(user);
    }


    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration";
        String senderName = "Central Station";
        String mailContent = "<p>Dear " + user.getName() + ",</p>";
        mailContent += "<p>Please click the link below to verify to your registration: </p>";
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
//        http://localhost:8080/verify?code=0Ewm144kDGr5ZqpRduohCf5D9uJx1egXbVDmIpcmHxpHWUAP6vZaN1JFXCj8m2dW
        mailContent += "<a href=\"" + verifyURL + "\"> VERIFY</a>";
        mailContent += "<p>Thank you <br> the Central Station Team</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("team@central.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        javaMailSender.send(message);
    }

    public boolean verify(String verificationCode) {
        User user = userRepositorySearch.findByVerificationCode(verificationCode);
        System.out.println(" --------------------------------- " + user + " -------------------------- findByVerificationCode ---------");

        if (user == null || user.isEnabled()) {      // if user does not exist nor already enabled in the past
            System.out.println(" --------------------------------- " + " i Am False " + " -----------------------------------");
            return false;                           // return false
        } else {
            user.setVerificationCode(null);         // delete the previous verification code
            user.setEnabled(true);                  // set the enabled mode to true
            userRepository.save(user);              // then save
            return true;
        }
    }


    public boolean isUserPresent(String email) {
        User user = userRepository.findById(email).orElse(null);
        if (user != null)
            return true;        // if user does not exist = true
        return false;           // if user does exist = false
    }

    public User findOneByEmail(String email) {
        return userRepository.findById(email).orElse(null); // findOne doesn't work therefore, findByID(email) has been used instead
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }


}





















