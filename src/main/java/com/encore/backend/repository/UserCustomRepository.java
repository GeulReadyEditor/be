package com.encore.backend.repository;

public interface UserCustomRepository {
    boolean deleteUserByEmail(String email);

    boolean deleteLikes(String userEmail, String boardId);

    boolean addLikes(String userEmail, String boardId);
}
