package com.encore.backend.repository;

import java.util.List;

public interface UserCustomRepository {

    int findScrapsCountByEmail(String email);

    boolean deleteUserByEmail(String email);

    boolean addUserScraps(String email, String boardId);

    boolean removeUserScraps(String email, String boardId);

    boolean addUserFollowers(String email, String followerEmail);

    boolean removeUserFollowers(String email, String follower);


    boolean deleteLikes(String userEmail, String boardId);

    boolean addLikes(String userEmail, String boardId);
}
