package com.trace.jachuiplan.scrap;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.likes.LikesId;
import com.trace.jachuiplan.regioncd.Regioncd;
import com.trace.jachuiplan.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Scraps")
@IdClass(ScrapsId.class)
public class Scrap {
    @Id
    @ManyToOne
    @JoinColumn(name="S_RENO")
    private Regioncd regioncd;

    @Id
    @ManyToOne
    @JoinColumn(name="S_UNO")
    private Users users;
}
