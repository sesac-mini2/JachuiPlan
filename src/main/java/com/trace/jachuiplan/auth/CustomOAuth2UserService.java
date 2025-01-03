package com.trace.jachuiplan.auth;

import com.trace.jachuiplan.user.CustomUserDetails;
import com.trace.jachuiplan.user.UserRepository;
import com.trace.jachuiplan.user.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public CustomOAuth2UserService(UserRepository userRepository, HttpSession httpSession){
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 소셜 로그인 제공자 정보
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = getOAuth2Response(provider, oAuth2User.getAttributes());

        // DB에 사용자 저장 또는 업데이트
        Users user = userRepository.findByProviderAndProviderId(
                oAuth2Response.getProvider(),
                oAuth2Response.getProviderId()
                ).orElseGet(() -> userRepository.save(
                        new Users(
                                oAuth2Response.getEmail().split("@")[0], // username은 이메일 앞부분
                                "Guest_" + oAuth2Response.getProviderId().substring(0, 5), // 임시 닉네임
                                oAuth2Response.getEmail(),
                                oAuth2Response.getProvider(),
                                oAuth2Response.getProviderId()
                        )
                ));

        CustomUserDetails customUserDetails = new CustomUserDetails(
                user.getUsername(),
                user.getPassword() != null ? user.getPassword() : "SOCIAL_LOGIN",
                user.getNickname(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes()
        );

        return customUserDetails;
    }

    // 제공자별 OAuth2Response 생성
    private OAuth2Response getOAuth2Response(String provider, Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return new GoogleResponse(attributes);

            default:
                throw new IllegalArgumentException("Unknown provider: " + provider);
        }
    }
}
