package com.trace.jachuiplan.board;


import com.trace.jachuiplan.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
