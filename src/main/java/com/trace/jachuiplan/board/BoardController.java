package com.trace.jachuiplan.board;

import com.trace.jachuiplan.likes.LikesId;
import com.trace.jachuiplan.likes.LikesService;
import com.trace.jachuiplan.user.UserService;
import com.trace.jachuiplan.user.Users;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/board")
public class BoardController {

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
}