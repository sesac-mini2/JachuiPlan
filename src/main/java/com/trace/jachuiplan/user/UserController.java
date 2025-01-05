package com.trace.jachuiplan.user;

import com.trace.jachuiplan.CustomAnnotation.ExactSize;
import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.scrap.ScrapedListDTO;
import com.trace.jachuiplan.building.BuildingService;
import com.trace.jachuiplan.likes.LikesService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LikesService likesService;
    private final UserDetailsService userDetailsService;
    private final BuildingService buildingService;

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

    // 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserDetails userDetails,
                                           HttpSession session){
        String username = userDetails.getUsername();
        userService.deleteUser(username);
        SecurityContextHolder.clearContext();
        session.invalidate();

        return ResponseEntity.ok().build();
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

    @PutMapping("/change-nickname")
    public ResponseEntity<String> changeNickname(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("nickname") String nickname) {

        // DB에 닉네임 변경
        userService.changeNickname(userDetails.getUsername(), nickname);

        // 변경된 정보로 UserDetails 다시 불러오기
        UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(userDetails.getUsername());

        // 새로운 Authentication 토큰 생성
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                updatedUserDetails.getPassword(),
                updatedUserDetails.getAuthorities()
        );

        // 현재 SecurityContext에 새로운 Authentication 교체
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return ResponseEntity.ok("닉네임이 변경되었습니다.");
    }

    // 마이페이지
    @GetMapping("/mypage")
    public String myPage(Model model,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        model.addAttribute("user", userDetails);
        model.addAttribute("type", MypageTab.INFO.getType());
        model.addAttribute("currentUri", "");

        return "users/myPage_form";
    }

    // 작성한 글
    @GetMapping("/mypage/myposts")
    public String myPosts(Model model,
                            @AuthenticationPrincipal UserDetails userDetails){
        List<Board> postBoards = userService.getPosts(userDetails.getUsername());

        model.addAttribute("postBoards", postBoards);
        model.addAttribute("currentUri", "");
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
        model.addAttribute("currentUri", "");
        model.addAttribute("type", MypageTab.LIKES.getType());

        return "users/mylikes_form";
    }

    // 스크랩한 지역
    @GetMapping("/mypage/region")
    public String myRegion(@RequestParam(name = "startYearMonth", required = false) @ExactSize(6) String startYearMonth,
                           @RequestParam(name = "endYearMonth", required = false) @ExactSize(6) String endYearMonth,
                           @RequestParam(name = "rentType", required = false) String rentType,
                           @RequestParam(name = "minArea", required = false) Double minArea,
                           @RequestParam(name = "maxArea", required = false) Double maxArea,
                           @RequestParam(name = "minBuildYear", required = false) Integer minBuildYear,
                           @RequestParam(name = "maxBuildYear", required = false) Integer maxBuildYear,
                           @RequestParam(name = "minFloor", required = false) Integer minFloor,
                           @RequestParam(name = "maxFloor", required = false) Integer maxFloor,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model){
        Users users = userService.findByUsername(userDetails.getUsername()).get();
        model.addAttribute("type", MypageTab.REGION.getType());
        model.addAttribute("currentUri", "");

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

    // 소셜 로그인 회원 정보 재설정
    @GetMapping("/social-signup")
    public String socialSignup(@AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model){

        if(userDetails == null || userDetails.getPassword() != null && !"SOCIAL_LOGIN".equals(userDetails.getPassword())){
            return "redirect:/map";
        }

        model.addAttribute("user", userDetails);
        return "users/socialSignup_form";
    }

    @PostMapping("/social-signup")
    @ResponseBody
    public ResponseEntity<String> socialSignupProcess(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("nickname") String nickname,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("소셜 로그인 유저 정보가 없습니다.");
        }

        // 비밀번호, 비밀번호 확인 일치 확인
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        // 회원 정보 변경(비밀번호, 닉네임)
        try {
            userService.changePassword(userDetails.getUsername(), newPassword);
            userService.changeNickname(userDetails.getUsername(), nickname);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        UserDetails updatedUser = userDetailsService.loadUserByUsername(userDetails.getUsername());
        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(
                        updatedUser,
                        updatedUser.getPassword(),
                        updatedUser.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return ResponseEntity.ok("추가 정보가 설정되었습니다.");
    }
}


