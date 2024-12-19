package com.trace.jachuiplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/board/like")) 
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/users/signup", "/users/check-username", "/users/check-nickname", "/users/login",  // 로그인 및 회원가입 페이지는 누구나 접근 가능
                                "/api/regioncd/**", "/api/officeHotel/**") // API는 누구나 접근 가능
                        .permitAll()
                        //.requestMatchers("/public/**", "/user/signup", "/user/check-username", "/user/check-nickname").permitAll()
                        //.requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                        //.requestMatchers("/user/**").hasRole("USER")   // 사용자만 접근 가능
                        .requestMatchers("/board/like").authenticated() // 인증된 사용자만 접근 가능
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/users/login") // 로그인 post
                        .defaultSuccessUrl("/users/myPage") // 임의로 마이페이지 연결
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}