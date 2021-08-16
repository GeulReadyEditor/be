package com.encore.backend.repository.user;

public interface ScrapCustomRepository {

    int findScrapsCountByEmail(String email);

    boolean addUserScraps(String email, String boardId);

    boolean removeUserScraps(String email, String boardId);
}
