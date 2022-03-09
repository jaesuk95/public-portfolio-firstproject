package com.koreait.cs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// https://stackoverflow.com/questions/61710593/cannot-login-with-spring-boot-security
// https://stackoverflow.com/questions/14268451/spring-security-userdetailsservice-implementation-login-fails


//public class User implements UserDetails {

// LOMBOK 없이 사용해보기

@Entity
public class User{

    @Id
    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(min = 4)
    private String password;

    private String nationality;
    //    @Column(length = 1000)
    @Lob
    private String bio;

    private String instagram;
    private String twitter;
    private String telegram;
    private String ig_tag;
    private String tw_tag;
    private String tg_tag;

    @Column(nullable = true, length = 64)   // column can store file name containing up to 64 characters
    private String profilePicture;

    @Generated(GenerationTime.INSERT)
    @Column(name = "id", columnDefinition = "serial", updatable = false)
    private Long user_id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<TweetBoard> tweetBoards;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_EMAIL", referencedColumnName = "email")}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_NAME", referencedColumnName = "name")})
    private List<Role> roles;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private boolean enabled;

//    for api purposes
    public User(String email, String name, String password, String bio, String instagram, String twitter, String nationality, String ig_tag, String tw_tag, String tg_tag, String profilePicture, boolean enabled) {
    }


    public User(String email, String name, String password, String bio, String nationality, boolean enabled, String verificationCode) {
    }


    public User(String email, String password, String name, String nationality, String bio, String verificationCode, String role, boolean enabled) {
    }

    public User(User newUser) {
    }


    public void saveUser(User user) {
        user.setEnabled(true);
    }


    public User(String email, String name, String password, String bio, String instagram, String twitter, String nationality,
                String telegram, String ig_tag, String tw_tag, String tg_tag, Long user_id, String profilePicture, boolean enabled,
                String verificationCode) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.bio = bio;
        this.instagram = instagram;
        this.twitter = twitter;
        this.nationality = nationality;
        this.telegram = telegram;
        this.ig_tag = ig_tag;
        this.tw_tag = tw_tag;
        this.tg_tag = tg_tag;
        this.user_id = user_id;
        this.profilePicture = profilePicture;
        this.enabled = enabled;
        this.verificationCode = verificationCode;
    }

    public User() {
    }

    @Transient
    public String getProfilePicturePath() {
        if (profilePicture == null || email == null) return null;
        return "/profile-image/" + email + "/" + profilePicture;
    }


    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getNationality() {
        return nationality;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
    public String getPassword() {
        return password;
    }

//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public List<TweetBoard> getTweetBoards() {
        return tweetBoards;
    }

    public void setTweetBoards(List<TweetBoard> tweetBoards) {
        this.tweetBoards = tweetBoards;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getIg_tag() {
        return ig_tag;
    }

    public void setIg_tag(String ig_tag) {
        this.ig_tag = ig_tag;
    }

    public String getTw_tag() {
        return tw_tag;
    }

    public void setTw_tag(String tw_tag) {
        this.tw_tag = tw_tag;
    }


    public String getTg_tag() {
        return tg_tag;
    }

    public void setTg_tag(String tg_tag) {
        this.tg_tag = tg_tag;
    }

}
