package com.trace.jachuiplan.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 추가

    // 회원가입 로직
    public void registerUser(UserSignupDTO dto) {
        // 비밀번호 확인
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 아이디 중복 확인
        if (!isUsernameAvailable(dto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 닉네임 중복 확인
        if (!isNicknameAvailable(dto.getNickname())) {
            throw new IllegalArgumentException("이미 있는 닉네임입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // Users 객체 생성
        Users user = new Users(
                dto.getUsername(),
                encodedPassword, // 암호화된 비밀번호 저장
                dto.getNickname()
        );

        // 사용자 저장
        userRepository.save(user);
    }

    // 아이디 중복 확인
    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    // 닉네임 중복 확인
    public boolean isNicknameAvailable(String nickname) {
        return userRepository.findByNickname(nickname).isEmpty();
    }

    /*
    // 비밀번호 변경
    public void changePassword(Long userId, String newPassword) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.changePassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // 닉네임 변경
    public void changeNickname(Long userId, String newNickname) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.changeNickname(newNickname);
        userRepository.save(user);
    }
     */
}
