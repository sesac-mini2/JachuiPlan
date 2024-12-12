package com.trace.jachuiplan.question;

import com.trace.jachuiplan.answer.Answer;
import com.trace.jachuiplan.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="QNO")
    private Long qno; // QNO (질문 번호)

    @Column(length = 225, name="Q_TITLE", nullable = false)
    private String qTitle; // Q_TITLE (질문 제목)

    @Lob
    @Column(name="Q_CONTENT", nullable = false)
    private String qContent; // Q_CONTENT (질문 내용)

    @Column(name="Q_REGDATE", nullable = false)
    private LocalDateTime qRegDate; // Q_REGDATE (등록일)

    @Column(name="Q_UPDATEDATE")
    private LocalDateTime qUpdateDate; // Q_UPDATEDATE (수정일)

    @Column(name="STATUS", nullable = false)
    private char status; // STATUS (답변 상태)

    @ManyToOne
    private Users users; // Q_WRITER (작성자 번호)

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerList;

}

