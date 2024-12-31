package com.trace.jachuiplan.user;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.board.BoardService;
import com.trace.jachuiplan.likes.LikesId;
import com.trace.jachuiplan.likes.LikesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LikesService likesService;

    @ModelAttribute("menu")
    public String menu(){
        return "mypage";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signupPage() {
        return "users/signup_form";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserSignupDTO userSignupDTO, Model model) {
        try {
            userService.registerUser(userSignupDTO);
            return "redirect:/users/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users/signup_form";
        }
    }

    // 아이디 중복 확인
    @GetMapping("/check-username")
    @ResponseBody
    public ResponseEntity<String> checkUsername(@RequestParam("username") String username) {
        if (userService.isUsernameAvailable(username)) {
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        } else {
            return ResponseEntity.badRequest().body("이미 있는 아이디입니다.");
        }
    }

    // 닉네임 중복 확인
    @GetMapping("/check-nickname")
    @ResponseBody
    public ResponseEntity<String> checkNickname(@RequestParam("nickname") String nickname) {
        if (userService.isNicknameAvailable(nickname)) {
            return ResponseEntity.ok("사용 가능한 닉네임입니다.");
        } else {
            return ResponseEntity.badRequest().body("이미 있는 닉네임입니다.");
        }
    }

    // 로그인
    @GetMapping("/login")
    public String login() {return "users/login_form";}

    // myPage user 검증
    @PostMapping("/myPage/verify-password")
    public ResponseEntity<String> verifyPassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("password") String password) {

        boolean isPasswordValid = userService.verifyPassword(userDetails.getUsername(), password);

        if (isPasswordValid) {
            return ResponseEntity.ok("비밀번호가 일치합니다.");
        } else {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }
    }

    // 임시 myPage
    @GetMapping("/mypage")
    public String myPage(Model model,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        model.addAttribute("user", userDetails);
        model.addAttribute("type", MypageTab.INFO.getType());
        return "users/myPage_form";
    }

    // 비밀번호 변경
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword){

        if(!newPassword.equals(confirmPassword)){
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        userService.changePassword(userDetails.getUsername(), newPassword);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    // 닉네임 변경
    @PutMapping("/change-nickname")
    public ResponseEntity<String> changeNickname(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("nickname") String nickname){

        userService.changeNickname(userDetails.getUsername(), nickname);
        return ResponseEntity.ok("닉네임이 변경되었습니다.");
    }


    @GetMapping("/mypage/myposts")
    public String myPosts(Model model,
                            @AuthenticationPrincipal UserDetails userDetails){
        List<Board> postBoards = userService.getPosts(userDetails.getUsername());
        List<Board> likedBoards = userService.likedBoards(userDetails.getUsername());

        // 좋아요 수 계산
        Map<Long, Long> likesCountMap = new HashMap<>();
        for (Board board : likedBoards) {
            long likesCount = likesService.getLikesCount(board);
            likesCountMap.put(board.getBno(), likesCount);
        }

        model.addAttribute("postBoards", postBoards);
        model.addAttribute("likesCountMap", likesCountMap);
        model.addAttribute("type", MypageTab.POSTS.getType());

        return "users/myposts_form";
    }

    // 좋아요한 글
    @GetMapping("/mypage/mylikes")
    public String myLikes(Model model,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(value="page", defaultValue="0") int page) {
        List<Board> likedBoards = userService.likedBoards(userDetails.getUsername());

        // 좋아요 수 계산
        Map<Long, Long> likesCountMap = new HashMap<>();
        for (Board board : likedBoards) {
            long likesCount = likesService.getLikesCount(board);
            likesCountMap.put(board.getBno(), likesCount);
        }

        model.addAttribute("likedBoards", likedBoards);
        model.addAttribute("likesCountMap", likesCountMap);
        model.addAttribute("type", MypageTab.LIKES.getType());
        return "users/mylikes_form";
    }

    // 스크랩한 지역
    @GetMapping("/mypage/region")
    public String myRegion(Model model){
        model.addAttribute("type", MypageTab.REGION.getType());
        return "users/myRegion_form";
    }

    // 권한 확인
    @ResponseBody
    @GetMapping("/check-auth")
    public ResponseEntity<Map<String, Object>> checkAuthentication(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();
        boolean authenticated = userDetails != null && !userDetails.getAuthorities().isEmpty();
        response.put("authenticated", authenticated);
        response.put("nickname", authenticated ? userDetails.getNickname() : "");
        return ResponseEntity.ok(response);
    }
}


