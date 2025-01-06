/// 김성현, 김정은
package com.trace.jachuiplan.likes;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, LikesId> {
    // 특정 게시글과 사용자의 좋아요 찾기
    Likes findByBoardAndUsers(Board board, Users users);
    List<Likes> findByUsers(Users users);

    // 좋아요 수
    long countByBoard(Board board);
}
