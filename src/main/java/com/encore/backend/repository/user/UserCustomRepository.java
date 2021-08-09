package com.encore.backend.repository.user;

import com.encore.backend.vo.UserVO;

public interface UserCustomRepository {

    int findScrapsCountByEmail(String email);

    boolean deleteUserByEmail(String email);

    boolean updateUserByEmail(String email, UserVO user);
}
