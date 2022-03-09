package com.koreait.cs.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class UserPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 db 의 넘버링 전략을 따라간다
    private Long id;
    private String symbol;
    private Long shares;
    private Long avePrice;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    public UserPortfolio(String symbol, Long shares, Long avePrice, User user) {
        this.symbol = symbol;
        this.shares = shares;
        this.avePrice = avePrice;
        this.user = user;
    }
}