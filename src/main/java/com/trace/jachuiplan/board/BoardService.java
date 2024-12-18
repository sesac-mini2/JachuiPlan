package com.trace.jachuiplan.board;

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

    public Page<Board> getInfoBoards(Pageable pageable) {
        return boardRepository.findByType('0', pageable);
    }

    public Page<Board> getGeneralBoards(Pageable pageable) {
        return boardRepository.findByType('1', pageable);
    }

    public Page<Board> getQnABoards(Pageable pageable) {
        return boardRepository.findByType('2', pageable);
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

}
