package com.koreait.cs.service;

import com.koreait.cs.entities.Role;
import com.koreait.cs.entities.User;
import com.koreait.cs.entities.userdetails.CustomUserDetails;
import com.koreait.cs.repository.UserRepositoryProfileSecure;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserServiceImp implements UserDetailsService {

    @Autowired
    UserRepositoryProfileSecure userRepositoryProfileSecure;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepositoryProfileSecure.findByEmail(email);

        if (user == null){
            throw new UsernameNotFoundException("Not Found account");
        }

//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        Set<Role> roles = (Set<Role>) user.getRoles();
//        for (Role role : roles) {
//            System.out.println(role.getName());
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//
//        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);

        return new CustomUserDetails(user);
    }
}
