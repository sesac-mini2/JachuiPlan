package com.trace.jachuiplan.board;

import com.trace.jachuiplan.likes.Likes;
import com.trace.jachuiplan.reply.Reply;
import com.trace.jachuiplan.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BNO")
    private Integer bno;

    @Column(name = "TITLE", length = 225, nullable = false)
    private String title;

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "REGDATE", nullable = false)
    private LocalDateTime regdate;

    @Column(name = "UPDATEDATE", nullable = true)
    private LocalDateTime updatedate;

    @Column(name = "VIEWS", nullable = false, columnDefinition = "NUMBER DEFAULT 0")
    private Integer views;

    @Column(name = "TYPE", nullable = false)
    private Character type;

    @ManyToOne
    private Users users;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Reply> replyList;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Likes> likesList;
}
