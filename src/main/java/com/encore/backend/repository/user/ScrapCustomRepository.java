package com.encore.backend.repository.user;

public interface ScrapCustomRepository {

    boolean addUserScraps(String email, String boardId);

    boolean removeUserScraps(String email, String boardId);
}
