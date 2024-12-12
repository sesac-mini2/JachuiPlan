package com.trace.jachuiplan.user;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.likes.Likes;
import com.trace.jachuiplan.question.Question;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 사용자 번호 자동 증가
    @Column(name = "UNO")
    private Integer uno; // 사용자 번호 (PK)

    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username; // 사용자 이름

    @Column(name = "PASSWORD", nullable = false, length = 225)
    private String password; // 사용자 비밀번호

    @Column(name = "NICKNAME", nullable = false, length = 225)
    private String nickname; // 사용자 닉네임


    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Board> boardList;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Question> questionList;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Likes> likesList;
}

