package com.trace.jachuiplan.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 페이징 처리를 위한 메서드
    Page<Board> findByType(char type, Pageable pageable);
}
