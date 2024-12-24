package com.trace.jachuiplan.reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByBoardBno(Long bno);
    Page<Reply> findByBoardBno(Long bno, Pageable pageable);
    Long countByReplydateBeforeAndBoardBno(LocalDateTime replydate, Long bno);
    Long countByBoardBno(Long bno);

}
