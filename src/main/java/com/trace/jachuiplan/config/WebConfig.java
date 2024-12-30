package com.trace.jachuiplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // 모든 API 엔드포인트에 대해 CORS 설정
                        .allowedOrigins("http://localhost:3000") // 허용할 출처
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메소드
                        .allowedHeaders("*") // 허용할 헤더
                        .allowCredentials(true); // 자격 증명 허용
            }
        };
    }
}