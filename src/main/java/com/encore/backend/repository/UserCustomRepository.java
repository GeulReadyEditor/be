package com.encore.backend.repository;

import java.util.List;

public interface UserCustomRepository {

    int findScrapsCountByEmail(String email);

    boolean deleteUserByEmail(String email);

    boolean addUserScraps(String email, String boardId);

    boolean removeUserScraps(String email, String boardId);

    boolean addUserFollwers(String email, String followerEmail);

    boolean removeUserFollwers(String email, String follower);


    boolean deleteLikes(String userEmail, String boardId);

    boolean addLikes(String userEmail, String boardId);
}
