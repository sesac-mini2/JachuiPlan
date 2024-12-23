package com.trace.jachuiplan.reply;

import com.trace.jachuiplan.DataNotFoundException;
import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.board.BoardRepository;
import com.trace.jachuiplan.user.Users;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    private final int pageSize = 10;

    // 게시글 댓글 목록
    public List<Reply> getList(Long bno) {
        // Board Entity 가져오기
        Optional<Board> board = boardRepository.findById(bno);
        if(board.isEmpty()) {
            throw new DataNotFoundException("게시글이 존재하지 않습니다.");
        }

        return this.replyRepository.findByBoardBno(bno);
    }

    // 게시글 댓글 목록 페이징
    @Transactional
    public Page<Reply> getList(Long bno, int page) {
        // 마지막 페이지를 초과한 요청을 했을 때
        Long totalCount = replyRepository.countByBoardBno(bno);
        int lastPage = (int) ((totalCount - 1) / pageSize);

        int currentPage = (page > lastPage) ? lastPage : page;

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("replydate")); // replydate로 오름차순
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by(sorts)); // 페이징과 정렬 설정
        return this.replyRepository.findByBoardBno(bno, pageable); // JPA를 통해 페이징된 결과 반환
    }

    // 특정 댓글 페이지 번호
    @Transactional
    public int getReplyPage(Long bno, Long rno){
        Optional<Reply> reply = replyRepository.findById(rno);
        if(reply.isEmpty()) {
            throw new DataNotFoundException("댓글이 존재하지 않습니다.");
        }
        Long position = replyRepository.countByReplydateBeforeAndBoardBno(reply.get().getReplydate(), bno);

        return (int) (position / pageSize);
    }

    // 댓글 가져오기
    public Reply getReply(Long rno) {
        Optional<Reply> reply= replyRepository.findById(rno);
        if(reply.isEmpty()){
            throw new DataNotFoundException("댓글이 존재하지 않습니다.");
        }
        return reply.get();
    }

    // 댓글 등록
    public Reply create(ReplyRequest request, Long bno, Users users){
        // Board Entity 가져오기
        Optional<Board> board = boardRepository.findById(bno);
        if(board.isEmpty()) {
            throw new DataNotFoundException("게시글이 존재하지 않습니다.");
        }

        // Reply Entity 생성
        Reply reply = Reply.builder()
                .reply(request.getReply())
                .board(board.get())
                .users(users)
                .build();
        return replyRepository.save(reply);
    }

    // 댓글 수정
    public Reply modify(Reply reply, ReplyRequest replyRequest, Users users){
        reply.setReply(replyRequest.getReply());

        return replyRepository.save(reply);
    }

    // 댓글 삭제
    @Transactional
    public void delete(Reply reply) {
        // 삭제
        replyRepository.delete(reply);

        // 삭제 후 확인
        if (replyRepository.existsById(reply.getRno())) {
            throw new EntityExistsException("해당 댓글이 삭제 되지않았습니다.");
        }
    }
}
