package com.trace.jachuiplan.reply;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.user.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPLY")
    @SequenceGenerator(
            name = "SEQ_REPLY", // JPA 내부에서 사용할 생성기 이름
            sequenceName = "SEQ_REPLY", // 데이터베이스에 실제 존재하는 시퀀스 이름
            allocationSize = 1  // 시퀀스 증가 크기
    )
    @Column(name = "RNO")
    private Long rno;

    @Lob
    @Column(name = "REPLY", nullable = false, length = 300)
    private String reply;

    @Column(name = "REPLYDATE", nullable = false)
    @CreatedDate
    private LocalDateTime replydate;

    @Column(name = "REPLY_UPDATEDATE", nullable = true)
    @CreatedDate
    @LastModifiedDate
    private LocalDateTime replyUpdatedate;

    @ManyToOne
    @JoinColumn(name="R_BNO", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name="R_UNO", nullable = false)
    private Users users;
}