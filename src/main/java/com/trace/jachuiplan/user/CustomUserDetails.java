package com.trace.jachuiplan.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public class CustomUserDetails implements UserDetails, OAuth2User {

    private String username;
    private String password;

    @Getter
    private String nickname;
    private Collection<? extends GrantedAuthority> authorities;

    // 소셜 로그인에서 넘어오는 attributes (구글 userInfo 등)
    private Map<String, Object> oAuth2Attributes;

    // 폼 로그인
    public CustomUserDetails(String username, String password, String nickname,
                             List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.authorities = authorities;
    }

    // 소셜 로그인
    public CustomUserDetails(String username,
                             String password,
                             String nickname,
                             Collection<? extends GrantedAuthority> authorities,
                             Map<String, Object> oAuth2Attributes) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.authorities = authorities;
        this.oAuth2Attributes = oAuth2Attributes;
    }

    // UserDetails 구현 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
    // OAuth2User 구현 메서드
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes;
    }

    @Override
    public String getName() {
        if (oAuth2Attributes != null && oAuth2Attributes.containsKey("sub")) {
            return (String) oAuth2Attributes.get("sub");
        }
        return this.username;
    }
}