package com.trace.jachuiplan.likes;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Likes")
@IdClass(LikesId.class)
public class Likes {
    @Id
    @ManyToOne
    @JoinColumn(name="L_BNO")
    private Board board;

    @Id
    @ManyToOne
    @JoinColumn(name="L_UNO")
    private Users users;
}