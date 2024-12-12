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
    private Board board;

    @Id
    @ManyToOne
    private Users users;
}