package com.koreait.cs.config;


import com.koreait.cs.component.AuthFailureHandler;
import com.koreait.cs.service.UserService;
import com.koreait.cs.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 특정 페이제에 특정 권한이 있는 유저만 접근을 허용할 경우 권한 및
// 인증을 미리 체크하겠다는 설정을 활성화한다.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

//    @Autowired
//    private DataSource dataSource;

    private final AuthFailureHandler authFailureHandler;
    private final UserServiceImp userServiceImp;

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    DataSource dataSource;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  // with AuthenticationManagerBuilder you can configure JDBC authentication
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select email as principal, password as credentials, true from user where email=?") // spring security requires that if this variable is true the user can be authenticated
//                .authoritiesByUsernameQuery("select user_email as principal, role_name as role from user_roles where user_email=?")
//                .passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");
//        // create the queries that returns the user's credentials as well as the rules from the database
//        // start by creating the query that returns the users credentials
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userServiceImp).passwordEncoder(passwordEncoder());
//    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImp).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // basic 으로 한 이유는 Insomnia, Postman 에서 api 확인 할 수 있는 방법이다
        http.csrf().disable().httpBasic();
        http
                .authorizeRequests()
                .antMatchers("/register", "/", "/market", "/login", "/testing", "/verify**",
                        "/css/**",
                        "/js/**",
                        "/img/**")
                    .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/loginaction")
                // 해당 URL 로 요청이 오면 스프링 security 가 가로채서 로그인 처리를 한다 -> loadUserByName
                .failureHandler(authFailureHandler)
                    .defaultSuccessUrl("/profile")
                    .permitAll()
                .and().
                logout().
                logoutSuccessUrl("/login");
    }
}