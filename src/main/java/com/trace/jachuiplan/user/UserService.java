package com.trace.jachuiplan.user;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.likes.Likes;
import com.trace.jachuiplan.likes.LikesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 추가
    private final LikesRepository likesRepository;

    // 사용자 이름으로 사용자 찾기
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);  // Repository의 findByUsername 메서드 호출
    }

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

    // myPage 사용자 비밀번호 검증
    public boolean verifyPassword(String username, String inputPassword){
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return passwordEncoder.matches(inputPassword, user.getPassword());
    }

    // 비밀번호 변경
    public void changePassword(String username, String newPassword) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setPassword(passwordEncoder.encode(newPassword)); // 새 비밀번호 암호화 후 저장
        userRepository.save(user);
    }

    // 닉네임 변경
    public void changeNickname(String username, String nickname) {
        // 닉네임 중복 확인
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("사용 중인 닉네임입니다.");
        }
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setNickname(nickname); // 새 닉네임 설정
        userRepository.save(user);
    }

    // 작성한 글
    public List<Board> getPosts(String username){
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Collections.sort(user.getDboardList(), Comparator.comparing(Board::getRegdate).reversed());
        return user.getDboardList();
    }

    // 좋아요한 글
    @Transactional
    public List<Board> likedBoards(String username){
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Likes> likesList = likesRepository.findByUsers(user); // Likes 데이터 가져오기
        List<Board> likedBoards = new ArrayList<>();

        for (Likes like : likesList) {
            likedBoards.add(like.getBoard());
        }

        // 최신 날짜순 정렬
        Collections.sort(likedBoards, Comparator.comparing(Board::getRegdate).reversed());

        return likedBoards;
    }
}
