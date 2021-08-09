package com.encore.backend.repository.user;

public interface LikeCustomRepository {

    boolean deleteLikes(String userEmail, String boardId);

    boolean addLikes(String userEmail, String boardId);
}
