package com.encore.backend.repository.user;

public interface FollowCustomRepository {

    boolean addUserFollowers(String email, String followerEmail);

    boolean removeUserFollowers(String email, String follower);

    boolean addUserFollowings(String email, String follower);

    boolean removeUserFollowings(String followEmail, String email);
}
