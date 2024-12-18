package com.trace.jachuiplan.reply;

import com.trace.jachuiplan.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByBoardBno(Long bno);
    Page<Reply> findByBoardBno(Long bno, Pageable pageable);
}
