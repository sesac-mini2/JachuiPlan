package com.trace.jachuiplan.reply;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.user.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponse {

    private Long rno;
    private String reply;
    private LocalDateTime replydate;
    private LocalDateTime replyUpdatedate;
    private String nickname;
    private String username;

    @Setter
    private int page;

    public ReplyResponse(Reply reply) {
        this.rno = reply.getRno();
        this.reply = reply.getReply();
        this.replydate = reply.getReplydate();
        this.replyUpdatedate = reply.getReplyUpdatedate();
        this.nickname = reply.getUsers().getNickname();
        this.username = reply.getUsers().getUsername();
    }

}
