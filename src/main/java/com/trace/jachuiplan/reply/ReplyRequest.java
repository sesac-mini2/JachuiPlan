package com.trace.jachuiplan.reply;

import com.trace.jachuiplan.user.Users;
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
    @NotNull(message="내용은 필수항목입니다.")
    private String reply;
}