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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ANSWER")
    @SequenceGenerator(
            name = "SEQ_ANSWER", // JPA 내부에서 사용할 생성기 이름
            sequenceName = "SEQ_ANSWER", // 데이터베이스에 실제 존재하는 시퀀스 이름
            allocationSize = 1  // 시퀀스 증가 크기
    )
    @Column(name="ANO")
    private Long ano; // ANO (답변 번호)

    @Lob
    @Column(name="A_CONTENT", nullable = true)
    private String aContent; // A_CONTENT (답변 내용)

    @ManyToOne
    @JoinColumn(name="A_QNO", nullable = false)
    private Question question; // A_QNO (질문 번호)
}

