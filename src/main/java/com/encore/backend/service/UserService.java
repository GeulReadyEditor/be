package com.encore.backend.service;

import com.encore.backend.dto.UserDto;
import com.encore.backend.vo.UserVO;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserVO> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);
}
