package com.trace.jachuiplan.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String username;
    private String nickname;
    private String message; // 처리 결과 메시지
}
