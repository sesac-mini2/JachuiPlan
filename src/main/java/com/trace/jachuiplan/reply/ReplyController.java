package com.trace.jachuiplan.reply;

import com.trace.jachuiplan.DataNotFoundException;
import com.trace.jachuiplan.user.UserService;
import com.trace.jachuiplan.user.Users;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final ReplyService replyService;
    private final UserService userService;

    // 게시글 댓글 목록
    @GetMapping("/list/{bno}")
    public String list(Model model, @PathVariable("bno") Long bno, @RequestParam(value="page", defaultValue="0") int page){

        Page<ReplyResponse> replyList = this.replyService.getList(bno, page)
                .map(ReplyResponse::new);
        model.addAttribute("paging", replyList);
        return "reply/reply_list :: replyFragment";
    }

    // 댓글 등록
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{bno}")
    @ResponseBody
    public ResponseEntity<?> create(@PathVariable("bno") Long bno, @Valid @RequestBody ReplyRequest replyRequest, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> {
                errorMessages.add(error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Users users = this.userService.findByUsername(principal.getName()).get();

        // 댓글 등록 결과
        ReplyResponse replyResponse = new ReplyResponse(replyService.create(replyRequest, bno, users));

        // 페이지 번호 가져오기
        replyResponse.setPage(replyService.getReplyPage(bno, replyResponse.getRno()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(replyResponse);
    }

    // 댓글 수정
    @PutMapping("/{rno}")
    public ResponseEntity<?> modify(@PathVariable("rno") Long rno, @Valid @RequestBody ReplyRequest replyRequest, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> {
                errorMessages.add(error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errorMessages);
        }
        // Reply Entity 가져오기
        Reply reply = replyService.getReply(rno);
        if (!reply.getUsers().getUsername().equals(principal.getName())) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("수정권한이 없습니다.");
            return ResponseEntity.badRequest().body(errorMessages);
        }

        Users users = this.userService.findByUsername(principal.getName()).get();

        ReplyResponse replyResponse = new ReplyResponse(replyService.modify(reply, replyRequest, users));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(replyResponse);
    }

    // 댓글 삭제
    @DeleteMapping("/{rno}")
    public ResponseEntity<?> delete(@PathVariable("rno") Long rno, Principal principal){
        // Reply Entity 가져오기
        Reply reply = replyService.getReply(rno);
        if (!reply.getUsers().getUsername().equals(principal.getName())) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("삭제권한이 없습니다.");
            return ResponseEntity.badRequest().body(errorMessages);
        }

        replyService.delete(reply);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
