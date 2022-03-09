package com.koreait.cs.controller;

import com.koreait.cs.entities.User;
import com.koreait.cs.entities.UserPortfolio;
import com.koreait.cs.repository.PortfolioRepository;
import com.koreait.cs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class PortfolioController {

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @GetMapping("/portfolio")
    public String portfolio(Model model, Principal principal){
        String principalDetail = principal.getName();
        User principalUser = userService.findOneByEmail(principalDetail);
//        String principalEmail = principalUser.getEmail();

        List<UserPortfolio> userPortfolio = portfolioRepository.findByUser(principalUser);
        model.addAttribute("portfolio", userPortfolio);

        return "views/stockmarket/portfolio";
    }

}
