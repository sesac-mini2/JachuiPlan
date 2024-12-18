package com.trace.jachuiplan.board;

import com.trace.jachuiplan.likes.LikesId;
import com.trace.jachuiplan.likes.LikesService;
import com.trace.jachuiplan.user.Users;
import com.trace.jachuiplan.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private UsersService usersService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/infolist")
    public String getInfoBoards(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.getInfoBoards(pageRequest);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "board/info_list";
    }

    @GetMapping("/generallist")
    public String getGeneralBoards(Model model,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.getGeneralBoards(pageRequest);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "board/general_list";
    }

    @GetMapping("/qnalist")
    public String getQnABoards(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.getQnABoards(pageRequest);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "board/qna_list";
    }

    @GetMapping("/menu")
    public String menu() {
        return "board/menu";
    }

    @GetMapping("/add_board")
    public String showAddBoardPage(@RequestParam(name = "type", required = false) char type,
                                   @RequestParam(name = "page") int page,
                                   @RequestParam(name = "size") int size,
                                   Model model) {
        model.addAttribute("type", type);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "board/add_board";
    }

    @PostMapping("/add_board")
    public String addBoard(Board board,
                           @RequestParam("type") char type,
                           @RequestParam(name = "page") int page,
                           @RequestParam(name = "size") int size) {
        Users currentUser = new Users();
        currentUser.setUno(1L);

        board.setType(type);
        board.setUsers(currentUser);
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
    public String getBoardDetail(@PathVariable("id") Long id, Model model) {
        Board board = boardService.getBoardById(id);

        Users currentUser = new Users();
        currentUser.setUno(1L);

        boolean isLiked = likesService.isLiked(board, currentUser);

        model.addAttribute("isLiked", isLiked);
        model.addAttribute("board", board);
        return "board/board_detail";
    }

    @PostMapping("/like")
    @ResponseBody
    public String toggleLike(@RequestParam("boardId") Long boardId) {
        Board board = boardService.getBoardById(boardId);

        Users user = usersService.findByUno(1L).orElseThrow(() -> new RuntimeException("User not found"));

        LikesId likesId = new LikesId(board, user);

        if (likesService.isLiked(board, user)) {
            likesService.removeLike(likesId);
            return "unliked";
        } else {
            likesService.addLike(likesId);
            return "liked";
        }
    }
}
