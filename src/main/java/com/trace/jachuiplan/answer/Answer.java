package com.trace.jachuiplan.answer;

import com.trace.jachuiplan.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Entity
@Table(name = "Answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ANO")
    private Long ano; // ANO (답변 번호)

    @Lob
    @Column(name="A_CONTENT", nullable = false)
    private String aContent; // A_CONTENT (답변 내용)

    @ManyToOne
    private Question question; // A_QNO (질문 번호)
}

