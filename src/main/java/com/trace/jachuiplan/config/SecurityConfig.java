package com.trace.jachuiplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/board/like"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/users/myPage/verify-password"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/users/change-nickname"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/users/change-password"))
                )
                .securityMatcher("/users/**", "/board/**", "/reply/**", "/admin/**", "/api/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/signup", "/users/check-username", "/users/check-nickname", "/users/login",  // 로그인 및 회원가입 페이지는 누구나 접근 가능
                                "/board/infolist", "/board/generallist", "/board/qnalist", "/board/menu", "/map/**", "/api/**") // 게시판 목록 누구나 접근 가능
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .successHandler((request, response, authentication) -> {
                            var savedRequest = (SavedRequest) request.getSession()
                                    .getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                            if (savedRequest != null) {
                                response.sendRedirect(savedRequest.getRedirectUrl());
                            } else {
                                response.sendRedirect("/map");
                            }
                        })
                        .failureUrl("/users/login?error=true") // 실패 시 로그인 페이지로 리다이렉트
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                        .logoutSuccessUrl("/map")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                (request, response, authException) -> response.sendRedirect("/users/login")
                        )
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