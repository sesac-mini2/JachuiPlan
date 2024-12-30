package com.trace.jachuiplan.board;

import com.trace.jachuiplan.likes.LikesRepository;
import com.trace.jachuiplan.reply.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
