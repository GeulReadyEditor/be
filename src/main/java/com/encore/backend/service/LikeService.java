package com.encore.backend.service;

public interface LikeService {

    int getBoardLikes(String boardId);

    int updateBoardLikes(String boardId, String userId);

    void updateUserLikes(String userId, String boardId, boolean exist);
}
