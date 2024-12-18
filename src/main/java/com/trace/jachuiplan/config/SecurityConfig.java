package com.trace.jachuiplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화 방식으로 수정

                // 인증 없이 모든 요청 허용
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll()  // 모든 요청에 대해 인증 없이 접근 허용
                );

        return http.build();
    }
}
