package com.encore.backend.service;

import java.io.IOException;
import java.util.List;

import com.encore.backend.dto.UserDto;
import com.encore.backend.vo.UserVO;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDto createUser(UserDto userDto) throws IOException;

    UserDto getUserByUserId(String userId);

    Iterable<UserVO> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);

    boolean deleteUserByEmail(String email);

    List<String> getUserScraps(String email, int scrapPageNumber);

    boolean addUserScraps(String email, String boardId);

    boolean removeUserScraps(String email, String boardId);

    List<String> getUserFollowers(String email);

    boolean updateUserByEmail(String email, UserDto user, MultipartFile profileImage);

    boolean addUserFollowers(String email, String followerEmail);

    boolean removeUserFollowers(String email, String followerEmail);

    int getUserScrapsCount(String email);

    List<String> getUserFollowings(String email);

    boolean addUserFollowings(String email, String follower);

    boolean removeUserFollowings(String followEmail, String email);
}
