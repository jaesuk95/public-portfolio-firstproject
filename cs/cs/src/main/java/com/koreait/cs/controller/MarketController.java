package com.koreait.cs.controller;

import com.koreait.cs.entities.User;
import com.koreait.cs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MarketController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/market")
    public String showMarket(Model model) {
        List<User> userSearch = userRepository.findAll();
        model.addAttribute("userSearch", new User());
        return "views/stockmarket/market";
    }




    @GetMapping("/market/dowj")
    public String showMarketDowj() {
        return "views/stockmarket/submarket/dow30";
    }

    @GetMapping("/market/spy")
    public String showMarketSPY() {
        return "views/stockmarket/submarket/spy";
    }

    @GetMapping("/market/nasdaq")
    public String showMarketNasdaq() {
        return "views/stockmarket/submarket/nasdaq";
    }

    @GetMapping("/market/btcusd")
    public String showMarketBitcoinUSD() {
        return "views/stockmarket/submarket/bitcoin";
    }

    @GetMapping("/market/ethusd")
    public String showMarketEthereumUSD() {
        return "views/stockmarket/submarket/ethereum";
    }

    @GetMapping("/market/usdkrw")
    public String showMarketUSDKRW() {
        return "views/stockmarket/submarket/usd-krw";
    }
}
