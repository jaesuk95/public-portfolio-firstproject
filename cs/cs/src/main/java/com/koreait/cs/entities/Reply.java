package com.koreait.cs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 db 의 넘버링 전략을 따라간다
    private Long id; // 시퀀스, auto-increment

//    (nullable = false, length = 200)
    @NotEmpty
    @Lob
    private String content;

//    @OneToOne   // one to one 일 경우, 하나의 글에는, 하나의 답변밖에 못한다
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tweet_board_id")
    private TweetBoard tweetBoards;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="USER_EMAIL", referencedColumnName = "email")
//    private User user;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createDate = new Date();

    @Column(nullable = true, length = 64)   // column can store file name containing up to 64 characters
    private String replyImage;

    @Transient
    public String getResponseImagePath(){
        if (replyImage == null || user.getEmail() == null) return null;
        return "/response-image/" + user.getEmail() + "/" + replyImage;
    }
}
