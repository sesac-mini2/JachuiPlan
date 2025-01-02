package com.trace.jachuiplan.board;

import com.trace.jachuiplan.likes.Likes;
import com.trace.jachuiplan.reply.Reply;
import com.trace.jachuiplan.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD")
    @SequenceGenerator(
            name = "SEQ_BOARD", // JPA 내부에서 사용할 생성기 이름
            sequenceName = "SEQ_BOARD", // 데이터베이스에 실제 존재하는 시퀀스 이름
            allocationSize = 1  // 시퀀스 증가 크기
    )
    @Column(name = "BNO")
    private Long bno;

    @Column(name = "TITLE", length = 225, nullable = false)
    private String title;

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "REGDATE", nullable = false)
    @ColumnDefault("SYSDATE")
    private LocalDateTime regdate;

    @Column(name = "UPDATEDATE", nullable = true)
    @ColumnDefault("SYSDATE")
    private LocalDateTime updatedate;

    @Column(name = "VIEWS", nullable = false, columnDefinition = "NUMBER DEFAULT 0")
    private Integer views;

    @Column(name = "TYPE", nullable = false)
    private Character type;

    @ManyToOne
    @JoinColumn(name="WRITER", nullable = false)
    private Users users;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Reply> replyList;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Likes> likesList;

    @Transient
    private Long replyCount; // 댓글 수 필드 추가

    @Transient
    private Long likeCount; // 좋아요 수 필드 추가

    @Transient
    private String formattedRegdate; // 포맷된 등록 날짜
}
