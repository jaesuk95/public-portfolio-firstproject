package com.koreait.cs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TweetBoard {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date date = new Date();

    @NotEmpty
    @Lob
    private String tweet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="USER_EMAIL", referencedColumnName = "email")
    private User user;

    @OneToMany(mappedBy = "tweetBoards", fetch = FetchType.EAGER, orphanRemoval = true) // mappedBy 연관관계의 주인이 아니다(난 FK 가 아니에요) DB 에 컬럼 만들지 말아라
    @OrderBy("id desc")
    private List<Reply> replies;

    @Column(nullable = true, length = 64)   // column can store file name containing up to 64 characters
    private String boardImage;


    public TweetBoard(Long id, Date date, String tweet, User user, String boardImage) {
        this.id = id;
        this.date = date;
        this.tweet = tweet;
        this.user = user;
        this.boardImage = boardImage;
    }

    public TweetBoard(Date date, String tweet, User user, String boardImage) {
        this.date = date;
        this.tweet = tweet;
        this.user = user;
        this.boardImage = boardImage;
    }

    public TweetBoard(TweetBoard tweetBoard) {
    }

    @Transient
    public String getBoardImagePath(){
        if (boardImage == null || user.getEmail() == null) return null;
        return "/board-image/" + user.getEmail() + "/" + boardImage;
    }
}





















