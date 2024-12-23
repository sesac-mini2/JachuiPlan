package com.trace.jachuiplan.reply;

import com.trace.jachuiplan.user.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequest {
    @NotBlank(message="댓글을 입력해주세요.")
    private String reply;
}