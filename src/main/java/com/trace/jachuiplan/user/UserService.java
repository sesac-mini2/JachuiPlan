package com.trace.jachuiplan.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    // 테스트 하드 코딩
    public Users getUser(String username) {
        Users users = new Users();
        users.setUno(1L);
        users.setUsername("user00");
        users.setNickname("user00");
        return users;
    }
}
