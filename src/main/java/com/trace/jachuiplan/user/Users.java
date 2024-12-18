package com.trace.jachuiplan.user;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.likes.Likes;
import com.trace.jachuiplan.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Users")
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USERS")
    @SequenceGenerator(
            name = "SEQ_USERS", // JPA 내부에서 사용할 생성기 이름
            sequenceName = "SEQ_USERS", // 데이터베이스에 실제 존재하는 시퀀스 이름
            allocationSize = 1  // 시퀀스 증가 크기
    )
    @Column(name = "UNO")
    private Long uno; // 사용자 번호 (PK)

    @Column(name = "USERNAME", nullable = false, length = 50, unique = true)
    private String username; // 사용자 이름

    @Column(name = "PASSWORD", nullable = false, length = 225)
    private String password; // 사용자 비밀번호

    @Column(name = "NICKNAME", nullable = false, length = 225, unique = true)
    private String nickname; // 사용자 닉네임

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Board> dboardList;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Question> questionList;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Likes> likesList;

    // **새로운 생성자 추가**
    public Users(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
     // 성현수정
    public void setUno(long uno) {
        this.uno = uno;
    }
}

