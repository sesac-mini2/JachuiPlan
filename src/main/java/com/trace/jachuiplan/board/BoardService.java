package com.trace.jachuiplan.board;

import com.trace.jachuiplan.DataNotFoundException;
import com.trace.jachuiplan.likes.LikesRepository;
import com.trace.jachuiplan.reply.Reply;
import com.trace.jachuiplan.reply.ReplyRepository;
import com.trace.jachuiplan.user.Users;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private LikesRepository likesRepository;

    public Page<Board> getInfoBoards(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findByType('0', pageable);
        // 각 게시글에 댓글 수와 좋아요 수를 추가
        boardPage.forEach(board -> {
            board.setReplyCount(replyRepository.countByBoard(board)); // 댓글 수 계산
            board.setLikeCount(likesRepository.countByBoard(board));   // 좋아요 수 계산
        });
        return boardPage;
    }

    public Page<Board> getGeneralBoards(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findByType('1', pageable);
        // 각 게시글에 댓글 수와 좋아요 수를 추가
        boardPage.forEach(board -> {
            board.setReplyCount(replyRepository.countByBoard(board)); // 댓글 수 계산
            board.setLikeCount(likesRepository.countByBoard(board));   // 좋아요 수 계산
        });

        return boardPage;
    }

    public Page<Board> getQnABoards(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findByType('2', pageable);
        // 각 게시글에 댓글 수와 좋아요 수를 추가
        boardPage.forEach(board -> {
            board.setReplyCount(replyRepository.countByBoard(board)); // 댓글 수 계산
            board.setLikeCount(likesRepository.countByBoard(board));   // 좋아요 수 계산
        });

        return boardPage;
    }

    public void saveBoard(Board board) {
        boardRepository.save(board);  // Board 엔티티를 DB에 저장
    }

    // 게시글 ID로 게시글을 조회하는 메소드
    public Board getBoardById(Long id) {
        // ID로 게시글을 찾음
        return boardRepository.findById(id)
                .orElse(null); // 게시글이 없으면 null 반환
    }
    // 조회수 증가
    @Transactional
    public void addViewCount(Board board) {
        board.setViews(board.getViews() + 1);
    }

    // 게시글 삭제
    public Board deleteBoard(Long bno, UserDetails userDetails) throws Exception {
        // 게시글이 있는지 확인
        Optional<Board> board = boardRepository.findById(bno);
        if(board.isEmpty()){
            throw new DataNotFoundException("해당 게시글이 없습니다.");
        }

        // 삭제 권한 확인
        if (!board.get().getUsers().getUsername().equals(userDetails.getUsername())) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }

        boardRepository.delete(board.get());

        return board.get();
    }

    // 게시글 수정
    public void modifyBoard(Board board) {
        Board existingBoard = boardRepository.findById(board.getBno())
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + board.getBno()));

        existingBoard.setTitle(board.getTitle());
        existingBoard.setContent(board.getContent());
        existingBoard.setUpdatedate(java.time.LocalDateTime.now());

        boardRepository.save(existingBoard);
    }

}
