package com.trace.jachuiplan.likes;

import com.trace.jachuiplan.board.Board;
import com.trace.jachuiplan.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesService {

    @Autowired
    private LikesRepository likesRepository;

    // 좋아요 추가
    public void addLike(LikesId likesId) {
        // 좋아요가 이미 존재하지 않으면 새로운 좋아요를 추가
        if (!isLiked(likesId.getBoard(), likesId.getUsers())) {
            Likes like = new Likes();
            like.setBoard(likesId.getBoard());
            like.setUsers(likesId.getUsers());
            likesRepository.save(like);  // 좋아요를 데이터베이스에 저장
        }
    }

    // 좋아요 제거
    public void removeLike(LikesId likesId) {
        // 좋아요가 존재하면 제거
        Likes like = likesRepository.findByBoardAndUsers(likesId.getBoard(), likesId.getUsers());
        if (like != null) {
            likesRepository.delete(like);  // 데이터베이스에서 좋아요 제거
        }
    }

    // 특정 게시글에 대해 사용자가 좋아요를 눌렀는지 확인
    public boolean isLiked(Board board, Users users) {
        Likes like = likesRepository.findByBoardAndUsers(board, users);
        return like != null;  // 좋아요가 존재하면 true 반환
    }
}
