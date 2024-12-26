package com.trace.jachuiplan.reply;

import com.trace.jachuiplan.user.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max=300, message="댓글은 최대 300자까지 입력 가능합니다.")
    private String reply;
}