/// 김성현, 이화경, 김정은
package com.trace.jachuiplan.board;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.board.BoardService;
import com.trace.jachuiplan.board.BoardType;
import com.trace.jachuiplan.likes.LikesId;
import com.trace.jachuiplan.likes.LikesService;
import com.trace.jachuiplan.user.UserService;
import com.trace.jachuiplan.user.Users;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.nio.channels.AcceptPendingException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/board")
public class BoardController {

    /// 김성현
    @Autowired
    private final BoardService boardService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private UserService userService;

    @ModelAttribute("menu")
    public String menu(){
        return "community";
    }

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/infolist")
    public String getInfoBoards(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "10") int size,
                                @AuthenticationPrincipal UserDetails userDetails) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.getInfoBoards(Pageable.ofSize(size).withPage(page));

        // 오늘 날짜를 체크하여 "X시간 전" 또는 "X분 전" 형식으로 변환
        boardPage.getContent().forEach(board -> {
            LocalDateTime regdate = board.getRegdate();
            if (regdate.toLocalDate().isEqual(LocalDate.now())) {
                // 오늘인 경우 "X시간 전" 또는 "X분 전"으로 변환
                Duration duration = Duration.between(regdate, LocalDateTime.now());
                long minutes = duration.toMinutes();
                if (minutes < 60) {
                    // 1시간 이하일 경우 분 단위
                    board.setFormattedRegdate("약 " + minutes + "분 전");
                } else {
                    // 1시간 이상일 경우 시간 단위
                    long hours = duration.toHours();
                    board.setFormattedRegdate("약 " + hours + "시간 전");
                }
            } else {
                // 오늘이 아닌 경우 기존 형식으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                board.setFormattedRegdate(regdate.format(formatter));
            }
        });

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("type", BoardType.INFO.getType());
        return "board/info_list";
    }

    @GetMapping("/generallist")
    public String getGeneralBoards(Model model,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.getGeneralBoards(Pageable.ofSize(size).withPage(page));

        // 오늘 날짜를 체크하여 "X시간 전" 또는 "X분 전" 형식으로 변환
        boardPage.getContent().forEach(board -> {
            LocalDateTime regdate = board.getRegdate();
            if (regdate.toLocalDate().isEqual(LocalDate.now())) {
                // 오늘인 경우 "X시간 전" 또는 "X분 전"으로 변환
                Duration duration = Duration.between(regdate, LocalDateTime.now());
                long minutes = duration.toMinutes();
                if (minutes < 60) {
                    // 1시간 이하일 경우 분 단위
                    board.setFormattedRegdate("약 " + minutes + "분 전");
                } else {
                    // 1시간 이상일 경우 시간 단위
                    long hours = duration.toHours();
                    board.setFormattedRegdate("약 " + hours + "시간 전");
                }
            } else {
                // 오늘이 아닌 경우 기존 형식으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                board.setFormattedRegdate(regdate.format(formatter));
            }
        });

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("type", BoardType.GENERAL.getType());
        return "board/general_list";
    }

    @GetMapping("/qnalist")
    public String getQnABoards(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size,
                               @AuthenticationPrincipal UserDetails userDetails) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.getQnABoards(Pageable.ofSize(size).withPage(page));

        // 오늘 날짜를 체크하여 "X시간 전" 또는 "X분 전" 형식으로 변환
        boardPage.getContent().forEach(board -> {
            LocalDateTime regdate = board.getRegdate();
            if (regdate.toLocalDate().isEqual(LocalDate.now())) {
                // 오늘인 경우 "X시간 전" 또는 "X분 전"으로 변환
                Duration duration = Duration.between(regdate, LocalDateTime.now());
                long minutes = duration.toMinutes();
                if (minutes < 60) {
                    // 1시간 이하일 경우 분 단위
                    board.setFormattedRegdate("약 " + minutes + "분 전");
                } else {
                    // 1시간 이상일 경우 시간 단위
                    long hours = duration.toHours();
                    board.setFormattedRegdate("약 " + hours + "시간 전");
                }
            } else {
                // 오늘이 아닌 경우 기존 형식으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                board.setFormattedRegdate(regdate.format(formatter));
            }
        });

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("type", BoardType.QNA.getType());
        return "board/qna_list";
    }

    @GetMapping("/add_board")
    public String showAddBoardPage(@RequestParam(name = "type", required = false) char type,
                                   @RequestParam(name = "page") int page,
                                   @RequestParam(name = "size") int size,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   Model model) {
        model.addAttribute("user", userDetails);
        model.addAttribute("type", type);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "board/add_board";
    }

    @PostMapping("/add_board")
    public String addBoard(Board board,
                           @RequestParam("type") char type,
                           @RequestParam(name = "page") int page,
                           @RequestParam(name = "size") int size,
                           @AuthenticationPrincipal UserDetails userDetails) {
        // 로그인된 사용자 정보 설정
        if (userDetails != null) {
            String username = userDetails.getUsername();// 사용자 ID 가져오기
            Users currentUser = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));  // Optional에서 값 꺼내기
            board.setUsers(currentUser);  // 게시판에 작성자 설정
        }

        // 김정은
        if(type == '0' && !userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))){
            throw new AccessDeniedException("관리자만 글을 등록할 수 있습니다.");
        }

        /// 김성현
        board.setType(type);
        board.setRegdate(LocalDateTime.now());
        board.setViews(0);

        boardService.saveBoard(board);

        if(type == '0'){
            return "redirect:/board/infolist?page=" + page + "&size=" + size;
        } else if(type == '1'){
            return "redirect:/board/generallist?page=" + page + "&size=" + size;
        } else if(type == '2'){
            return "redirect:/board/qnalist?page=" + page + "&size=" + size;
        } else {
            return "redirect:/board/menu";
        }
    }

    @GetMapping("/detail/{id}")
    public String getBoardDetail(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {
        Board board = boardService.getBoardById(id);

        // 로그인된 사용자 정보 설정
        Users currentUser = null;
        if (userDetails != null) {
            String username = userDetails.getUsername(); // 사용자 ID 가져오기
            currentUser = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found")); // Optional에서 값 꺼내기
        }

        boolean isLiked = likesService.isLiked(board, currentUser);

        boardService.addViewCount(board);

        model.addAttribute("isLiked", isLiked);
        model.addAttribute("board", board);
        model.addAttribute("type", board.getType());
        return "board/board_detail";
    }

    @PostMapping("/like")
    @ResponseBody
    public String toggleLike(@RequestParam("boardId") Long boardId,
                             @AuthenticationPrincipal UserDetails userDetails) {
        // 로그인된 사용자 정보 설정
        Users currentUser = null;
        if (userDetails != null) {
            String username = userDetails.getUsername(); // 사용자 ID 가져오기
            currentUser = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found")); // Optional에서 값 꺼내기
        }

        // 게시물 정보 가져오기
        Board board = boardService.getBoardById(boardId);

        // 좋아요 상태 확인 및 토글
        return likesService.toggleLike(board, currentUser);
    }

    /// 이화경
    // 게시글 삭제
    @DeleteMapping("/{bno}")
    public String getBoardDelete(@PathVariable("bno") Long bno,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {
        try {
            Board board = boardService.deleteBoard(bno, userDetails);
            if (board.getType() == BoardType.INFO.getType()) {
                return "redirect:/board/infolist";
            } else if (board.getType() == BoardType.GENERAL.getType()) {
                return "redirect:/board/generallist";
            } else if (board.getType() == BoardType.QNA.getType()) {
                return "redirect:/board/qnalist";
            } else {
                return "redirect:/board/menu";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:" + request.getHeader("referer"); // 실패 시 현재 페이지로 리다이렉트
        }
    }

    /// 김정은
    // 게시물 수정
    @GetMapping("/modify/{id}")
    public String boardModify(@PathVariable("id") Long id,
                              @AuthenticationPrincipal UserDetails userDetails,
                              HttpServletRequest request,
                              Model model) {
        Board board = boardService.getBoardById(id);

        model.addAttribute("board", board);
        model.addAttribute("user", userDetails);
        model.addAttribute("currentUri", request.getRequestURI()); // sidebar 설정
        return "board/modify_board";
    }

    @PutMapping("/modify_board/{bno}")
    public String modifyBoard(
            @PathVariable("bno") Long bno,
            @ModelAttribute Board board, // 수정된 게시글 데이터
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        try{
            boardService.modifyBoard(bno, board, userDetails);
            return "redirect:/board/detail/" + bno;
        } catch (IllegalStateException e) {
            // 권한이 없는 경우
            redirectAttributes.addFlashAttribute("error", "수정 권한이 없습니다.");
        } catch (RuntimeException e) {
            // 기타 에러 처리
            redirectAttributes.addFlashAttribute("error", "수정 중 문제가 발생했습니다: " + e.getMessage());
        }

        return "redirect:/board/detail/" + bno;
    }
}