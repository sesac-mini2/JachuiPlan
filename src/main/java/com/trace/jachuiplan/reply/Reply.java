package com.trace.jachuiplan.reply;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RNO")
    private Integer rno;

    @Lob
    @Column(name = "REPLY", nullable = false)
    private String reply;

    @Column(name = "REPLYDATE", nullable = false)
    private LocalDateTime replydate;

    @Column(name = "REPLY_UPDATEDATE", nullable = true)
    private LocalDateTime replyUpdatedate;

    @ManyToOne
    private Board board;

    @ManyToOne
    private Users users;
}