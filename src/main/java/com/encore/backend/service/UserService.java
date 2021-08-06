package com.encore.backend.service;

import java.util.List;
import java.util.Map;

import com.encore.backend.dto.UserDto;
import com.encore.backend.vo.UserVO;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserVO> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);

    boolean deleteUserByEmail(String email);

    List<String> getUserScraps(String email, int scrapPageNumber);

    boolean addUserScraps(String email, String boardId);

    boolean removeUserScraps(String email, String boardId);

    List<String> getUserFollowers(String email);

    boolean addUserFollowers(String email, String followerEmail);

    boolean removeUserFollowers(String email, String followerEmail);

    int getUserScrapsCount(String email);
}
